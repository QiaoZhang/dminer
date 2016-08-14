package com.eptd.dminer.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.joda.time.DateTime;

import com.eptd.dminer.core.Authorization;
import com.eptd.dminer.core.Configuration;
import com.eptd.dminer.core.Parameter;
import com.eptd.dminer.processor.ProjectLogger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class GitHubAPIClient {
	//configuration
	private int REQUESTREPEAT;
	private int REQUESTSLEEP;
	
	private ProjectLogger logger;
	private Authorization auth;
	private final String encoding;
	
	private ArrayList<Parameter> paras;
	private int page = 1;
	private int maxPage = 0;
	private final int maxPerPage = 100;
	private final String SEPERATOR = "&";
	
	public GitHubAPIClient(ProjectLogger logger) {
		this.auth = null;
		this.encoding = "UTF-8";
		this.paras = new ArrayList<Parameter>();
		this.logger = new ProjectLogger(logger).append("GitHubAPIClient");
		this.Config(logger.getConfig());
	}
	
	public GitHubAPIClient(Authorization auth, ProjectLogger logger) {
		this.auth = auth;
		this.encoding = "UTF-8";
		this.paras = new ArrayList<Parameter>();
		this.logger = new ProjectLogger(logger).append("GitHubAPIClient");
		this.Config(logger.getConfig());
	}
	
	private void Config(Configuration config){
		REQUESTREPEAT = config.getRequestRepeat();
		REQUESTSLEEP = config.getRequestSleep();
	}
	
	public GitHubAPIClient addParameter(String paraName, String paraValue) {
		paras.add(new Parameter(paraName,paraValue));
		return this;
	}
	
	public GitHubAPIClient setMaxPage(int maxPage) {
		this.maxPage = maxPage;
		return this;
	}
	
	/**
	 * Using Get method to process a GitHub API URL while paging factor has been considered
	 * @param uri The URL to be conducted with Get method while all its parameters are not included. Please use addParameter() method.
	 * @return JsonObject(JsonElement) if the response data only contains 1 JsonObject; JsonArray which contains all returned JsonObject by search through paging system.
	 */
	public JsonElement get(String url){
		JsonElement jsonElement = new JsonObject();
		JsonArray jsonArray = new JsonArray();	
		try {
			do{
				HttpResponse response = request(url+setParameter()+setPaging(page,maxPerPage));
				if(!Optional.ofNullable(response).isPresent()) break;
				if(Optional.ofNullable(response.getFirstHeader("X-RateLimit-Remaining")).isPresent()
						&&Integer.valueOf(response.getFirstHeader("X-RateLimit-Remaining").getValue())==0){
					//make current thread sleep if the rate limit of GitHub API has reached
					long sleep = Long.valueOf(response.getFirstHeader("X-RateLimit-Reset").getValue())+1-(new DateTime().getMillis()/1000);
					Thread.sleep(sleep*1000);
					response = request(url+setParameter()+setPaging(page,maxPerPage));
				}
				if(maxPage==0&&response.getFirstHeader("Link") != null)
					maxPage = getLastPage(response.getFirstHeader("Link").getValue());
				jsonElement = new JsonParser().parse(IOUtils.toString(response.getEntity().getContent(),encoding));
				if(jsonElement.isJsonObject()){
					if(url.contains("/search/")&&Optional.ofNullable(jsonElement.getAsJsonObject().get("items")).isPresent())
						jsonArray.addAll(jsonElement.getAsJsonObject().get("items").getAsJsonArray());
				}else if(jsonElement.isJsonArray())
					jsonArray.addAll(jsonElement.getAsJsonArray());
				page++;
			}while(page <= maxPage);
		}catch (JsonSyntaxException | UnsupportedOperationException | IOException | NullPointerException | NumberFormatException | InterruptedException e) {
			logger.error("Get Method: " + url,e);
		}finally {
			clear();
		}
		if(jsonArray.size()>0||url.contains("/search/"))
			return jsonArray;
		return jsonElement;
	}
	
	public HttpResponse request(String url){
		int count = 0;
		do {
			try{
				if(count>0)
					Thread.sleep(REQUESTSLEEP * 1000);//REQUESTSLEEP: repeat HTTP request every X seconds 
				HttpClient httpClient = HttpClientBuilder.create().build();
				HttpGet request = new HttpGet(url);
				if(auth!=null)
					request.addHeader(auth.getOAuthTokenHeader());
				return httpClient.execute(request);
			}catch(IOException e){
				count++;
				logger.error("Request: "+url, e);
			} catch (InterruptedException e) {
				logger.error("Repeat Request: "+url, e);
				return null;
			}
		}while(count < REQUESTREPEAT);//REQUESTREPEAT: repeat up to Y times
		return null;
	}
	
	public HttpResponse put(String url,List<Header> headers,StringEntity entity){
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPut request = new HttpPut(url);
			if(auth!=null)
				request.addHeader(auth.getOAuthTokenHeader());			
			headers.stream().forEach(h->request.addHeader(h));
			request.setEntity(entity);
			return httpClient.execute(request);
		} catch (IOException e) {
			logger.error("Put Method: "+url, e);
		}
		return null;
	}
	
	public boolean delete(String url,List<Header> headers) {
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpDelete request = new HttpDelete(url);
			if(auth!=null)
				request.addHeader(auth.getOAuthTokenHeader());			
			headers.stream().forEach(h->request.addHeader(h));
			HttpResponse response = httpClient.execute(request);
			return response.getFirstHeader("Status").getValue().equals("204 No Content");
		} catch (IOException e) {
			logger.error("Delete Method: "+url, e);
		}
		return false;
	}
	
	private int getLastPage(String str){
		String[] refs = str.substring(1,str.length()-1).split("\", <");
		for(int i=0;i<refs.length;i++){
			String[] page = refs[i].split(">; rel=\"");
			if(page[1].equals("last")){
				String[] last = page[0].split("page=");
				String[] num = last[1].split("&");
				return Integer.valueOf(num[0]);
			}
		}
		return 1;
	}
	
	private String setParameter(){
		return paras.stream().map(Parameter::getURLParameter).reduce("?",(url,para)->url+para+SEPERATOR);
	}
	
	private String setPaging(Integer page, Integer perPage){
		return new Parameter("page",page.toString()).getURLParameter() + SEPERATOR +
				new Parameter("per_page",perPage.toString()).getURLParameter();
	}
	
	private void clear(){		
		this.paras.clear();
		this.setPage(1);
		this.setMaxPage(0);
	}	
	
	private void setPage(int page){
		this.page = page;
	}
	
	public int getMaxPerPage(){
		return this.maxPerPage;
	}
}

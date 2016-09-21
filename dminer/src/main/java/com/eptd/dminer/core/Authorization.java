package com.eptd.dminer.core;

import java.util.Arrays;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import com.eptd.dminer.crawler.GitHubAPIClient;
import com.eptd.dminer.processor.ProjectLogger;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Authorization{
	private final Header jsonContent = new BasicHeader("Content-type", "application/json");
	private ProjectLogger logger;
	private Header BasicAuthHeader = null;
	private Header OAuthTokenHeader = null;
	private Header DeleteHeader = null;
	private String clientID = null;
	private String fingerPrint = null;
	private String OAuthToken;
	private String data = "{\"client_secret\":\"----------------------------------------\",\"scope\":[\"repo\",\"gist\",\"user\"],\"note\":\"";
	
	public Authorization(ProjectLogger logger){
		this.logger = new ProjectLogger(logger).append(" Authorization");
		this.BasicAuthHeader = new BasicHeader("Authorization","Basic "+ Base64.getEncoder().encodeToString(
				new String(logger.getConfig().getClient().getUsername()+":"+logger.getConfig().getClient().getPassword()).getBytes()));
		this.clientID = logger.getConfig().getClient().getAppClientID();
		this.fingerPrint = logger.getConfig().getClient().getFingerPrint();
		this.data = data.replace("----------------------------------------", logger.getConfig().getClient().getAppClientSecret()) + fingerPrint +"\"}";
		this.DeleteHeader = new BasicHeader("Authorization","Basic "+ Base64.getEncoder().encodeToString(new String(clientID + ":" + logger.getConfig().getClient().getAppClientSecret()).getBytes()));
		
	}
	
	public String getOAuthToken() {		
		return this.OAuthToken;
	}
	
	public Header getOAuthTokenHeader(){
		if(this.OAuthToken == null)
			return this.BasicAuthHeader;
		return this.OAuthTokenHeader;
	}
	
	/**
	 * Create a GitHub Developer Application OAuth Token
	 * @return this if success; null if failed
	 */
	public Authorization createOAuthToken(){
		try {
			GitHubAPIClient client = new GitHubAPIClient(logger);
			if(BasicAuthHeader == null||DeleteHeader == null){
				throw new Exception("Need Authentication");
			}
			HttpResponse response = client.put("https://api.github.com/authorizations/clients/"+clientID+"/"+fingerPrint, 
					Arrays.asList(jsonContent,BasicAuthHeader),new StringEntity(data));
			JsonElement jsonElement = new JsonParser().parse(IOUtils.toString(response.getEntity().getContent(),"UTF-8"));
			if(jsonElement.isJsonObject()){
				OAuthToken = jsonElement.getAsJsonObject().get("token").getAsString();
				OAuthTokenHeader = new BasicHeader("Authorization","token " + OAuthToken);
			}
			return this;
		} catch (Exception e) {
			logger.error("Create OAuth Token", e);
			return null;
		}		
	}
	
	public boolean revokeOAuthToken(){
		if(this.OAuthTokenHeader == null)
			return false;
		try {
			GitHubAPIClient client = new GitHubAPIClient(logger);
			if(DeleteHeader == null){
				throw new UnsupportedOperationException("Need Authentication");
			}
			return client.delete("https://api.github.com/applications/"+clientID+"/tokens/"+this.getOAuthToken(),Arrays.asList(DeleteHeader));
		} catch (Exception e) {
			logger.error("Revoke OAuth Token",e);
			return false;
		}		
	}
}

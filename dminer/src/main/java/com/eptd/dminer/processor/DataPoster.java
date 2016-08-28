package com.eptd.dminer.processor;

import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import com.eptd.dminer.core.Client;
import com.eptd.dminer.core.MajorRepository;
import com.eptd.dminer.core.Task;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DataPoster {	
	private static final DataPoster instance = new DataPoster();
	
	private static final String DEFAULTURL = "http://qiaozhang.me:7070";
	private static final Header JSONTYPE = new BasicHeader("Content-type", "application/json");
	
	public synchronized JsonElement post(String url,MajorRepository repo,Integer failed){
		//initialization
		JsonObject jsonFailed = new JsonObject();
		jsonFailed.addProperty("success", false);
		jsonFailed.addProperty("error_msg", "Invalid response from EPTD-DSaver");
		try {
			//converting major repo to json string
			Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
			String repoJson = gson.toJson(repo, MajorRepository.class);
			jsonFailed.add("data", new JsonParser().parse(repoJson));
			//post major repo
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost request= new HttpPost(Optional.ofNullable(url).orElse(DEFAULTURL)+"/DataSaver");
			request.addHeader(JSONTYPE);
			request.addHeader(new BasicHeader("Failed-repo",failed.toString()));
			request.setEntity(new StringEntity(repoJson));	
			JsonElement jsonElement = new JsonParser().parse(IOUtils.toString(httpClient.execute(request).getEntity().getContent(),"UTF-8"));
			if(!jsonElement.getAsJsonObject().get("success").getAsBoolean()){				
				jsonFailed.add("error_msg", jsonElement.getAsJsonObject().get("error_msg").getAsJsonArray());
				jsonElement = null;
			}
			return Optional.ofNullable(jsonElement).orElse(jsonFailed);	
		} catch (Exception e) {
			return jsonFailed;
		}			
	}
	
	public static Task getTask(String url,Client client,Integer failed){
		//initialization
		try {
			//converting client info to json string
			Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
			String clientJson = gson.toJson(client, Client.class);
			//post client info to get current task
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost request= new HttpPost(Optional.ofNullable(url).orElse(DEFAULTURL)+"/TaskDistributor");
			request.addHeader(JSONTYPE);
			request.addHeader(new BasicHeader("Failed-repo",failed.toString()));
			request.setEntity(new StringEntity(clientJson));
			JsonObject response = new JsonParser().parse(IOUtils.toString(httpClient.execute(request).getEntity().getContent(),"UTF-8")).getAsJsonObject();
			if(response.get("success").getAsBoolean()){
				if(response.get("generated_id")!=null)
					System.out.println("Register client success with generated id as "+response.get("generated_id").getAsInt());
				if(response.get("data")!=null)
					//convert to task class
					return gson.fromJson(response.get("data").getAsJsonObject().toString(), Task.class);				
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}

	public static DataPoster getInstance() {
		return instance;
	}
}

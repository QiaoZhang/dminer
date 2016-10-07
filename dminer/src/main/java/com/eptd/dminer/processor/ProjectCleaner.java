package com.eptd.dminer.processor;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

public class ProjectCleaner {
	private static final ProjectCleaner instance = new ProjectCleaner();
	
	public synchronized boolean deleteSonar(String projectKey){
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost request= new HttpPost("http://localhost:9000/api/projects/delete?key="+projectKey);
			request.addHeader(new BasicHeader("Authorization","Basic "+ Base64.getEncoder().encodeToString("admin:admin".getBytes())));
			if(httpClient.execute(request).getStatusLine().getStatusCode() == 204)
				return true;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public synchronized boolean deleteFolder(String path){
		try {
			FileUtils.deleteDirectory(new File(path));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static ProjectCleaner getInstance() {
		return instance;
	}

}	

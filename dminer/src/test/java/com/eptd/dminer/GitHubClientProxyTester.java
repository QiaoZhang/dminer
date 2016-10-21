package com.eptd.dminer;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class GitHubClientProxyTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet("https://api.github.com/users/qiaozhang");
		
	}

}

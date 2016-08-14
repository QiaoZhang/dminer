package com.eptd.dminer.junit;

import org.junit.BeforeClass;
import org.junit.Test;

import com.eptd.dminer.core.Configuration;
import com.eptd.dminer.crawler.GitHubAPIClient;
import com.eptd.dminer.processor.ProjectLogger;
import com.google.gson.JsonElement;

import junit.framework.Assert;

public class GitHubAPITester {
	static ProjectLogger logger;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger = new ProjectLogger("https://api.github.com/repos/qiaozhang/junit-tester",Configuration.getDefaultConfig());
	}

	@Test
	public void testGet() {
		JsonElement response = new GitHubAPIClient(logger).get("https://api.github.com/users/code4craft/events");
		Assert.assertTrue(response.isJsonArray()&&response.getAsJsonArray().size()>0);
	}
	
	@Test
	public void testSearch() {
		JsonElement response = new GitHubAPIClient(logger)
				.addParameter("q", "type:pr+state:closed+author:code4craft")
				.addParameter("sort", "updated")
				.get("https://api.github.com/search/issues");
		Assert.assertTrue(response.isJsonArray()&&response.getAsJsonArray().size()>0);
	}

}

package com.eptd.dminer.junit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eptd.dminer.core.Authorization;
import com.eptd.dminer.core.Configuration;
import com.eptd.dminer.crawler.RepositoryProcessor;
import com.eptd.dminer.processor.ProjectLogger;

import junit.framework.Assert;

public class RepoProcessorTester {
	static ProjectLogger logger;
	static Authorization auth;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger = new ProjectLogger("https://api.github.com/repos/qiaozhang/junit-tester",Configuration.getDefaultConfig());
		auth = new Authorization(logger).createOAuthToken();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Revoking OAuth Token ... " + auth.revokeOAuthToken());
	}

	@Test(timeout=180000)
	public void testProcess() {
		RepositoryProcessor processor = new RepositoryProcessor("https://api.github.com/repos/code4craft/webmagic",auth,"C:\\EPTD\\code4craft",logger);
		Assert.assertTrue(processor.process());
		System.out.println("RepositoryURL: " + processor.getRepo().getRepositoryURL());
		System.out.println("RepositoryHTML: " + processor.getRepo().getRepositoryHTML());
		System.out.println("FilePath: " + processor.getRepo().getFilePath());
		System.out.println("ProjectID: " + processor.getRepo().getProjectID());
		System.out.println("ProjectName: " + processor.getRepo().getProjectName());
		System.out.println("OwnerLogin: " + processor.getRepo().getOwnerLogin());
		System.out.println("UserType: " + processor.getRepo().getUserType());
		System.out.println("Language: " + processor.getRepo().getLanguage());
		System.out.println("Version: " + processor.getRepo().getVersion());
		System.out.println("Size: " + processor.getRepo().getSize());
		System.out.println("StargazersCount: " + processor.getRepo().getStargazersCount());
		System.out.println("SubscribersCount: " + processor.getRepo().getSubscribersCount());
		System.out.println("ForksCount: " + processor.getRepo().getForksCount());
		System.out.println("CreatedAt: " + processor.getRepo().getCreatedAt().toString());
		System.out.println("IssuesCount: " + processor.getRepo().getIssuesCount());
		System.out.println("HandledIssuesCount: " + processor.getRepo().getHandledIssuesCount());
		System.out.println("AvgIssueHandledDays: " + processor.getRepo().getAvgIssueHandledDays());
		for(int i=0;i<processor.getRepo().getSonarMetrics().size();i++)
			System.out.println(processor.getRepo().getSonarMetrics().get(i).getKey() + ":"
					+ processor.getRepo().getSonarMetrics().get(i).getValue());
	}

}

package com.eptd.dminer.junit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eptd.dminer.core.Authorization;
import com.eptd.dminer.core.Configuration;
import com.eptd.dminer.crawler.UserProcessor;
import com.eptd.dminer.processor.ProjectLogger;

import junit.framework.Assert;

public class UserProcessorTester {
	static ProjectLogger logger;
	static Authorization auth;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger = new ProjectLogger("https://api.github.com/repos/qiaozhang/junit-tester",Configuration.getDefaultConfig());
		auth = new Authorization(logger);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Revoking OAuth Token ... " + auth.revokeOAuthToken());
	}

	@Test(timeout=600000)
	public void test() {
		UserProcessor processor = new UserProcessor("https://api.github.com/users/code4craft",auth,"C:\\EPTD",logger);
		Assert.assertTrue(processor.process());
		System.out.println("userURL: " + processor.getUser().getUserURL());
		System.out.println("userHTML: " + processor.getUser().getUserHTML());
		System.out.println("userId: " + processor.getUser().getUserId());
		System.out.println("login: " + processor.getUser().getLogin());
		System.out.println("name: " + processor.getUser().getName());
		System.out.println("numOfPublicRepos: " + processor.getUser().getNumOfPublicRepos());
		System.out.println("numOfAssignees: " + processor.getUser().getNumOfAssignees());
		System.out.println("followers: " + processor.getUser().getFollowers());
		System.out.println("folderPath: " + processor.getUser().getFolderPath());
		System.out.println("**********owned repositories**********");
		System.out.println("numOfAnalyzedRepos: " + processor.getUser().getNumOfAnalyzedRepos());
		System.out.println("avgSize: " + processor.getUser().getAvgSize());
		System.out.println("avgStargazersCount: " + processor.getUser().getAvgStargazersCount());
		System.out.println("avgSubscribersCount: " + processor.getUser().getAvgSubscribersCount());
		System.out.println("avgForksCount: " + processor.getUser().getAvgForksCount());
		System.out.println("avgIssuesCount: " + processor.getUser().getAvgIssuesCount());
		System.out.println("avgHandledIssuesRatio: " + processor.getUser().getAvgHandledIssuesRatio());
		System.out.println("avgIssueHandledDays: " + processor.getUser().getAvgIssueHandledDays());
		System.out.println("**********owned repositories with SonarQube**********");
		System.out.println("avgBugs: " + processor.getUser().getAvgBugs());
		System.out.println("avgVulnerabilities: " + processor.getUser().getAvgVulnerabilities());
		System.out.println("avgCodeSmells: " + processor.getUser().getAvgCodeSmells());
		System.out.println("avgSqaleIndex: " + processor.getUser().getAvgSqaleIndex());
		System.out.println("avgDebtRatio: " + processor.getUser().getAvgDebtRatio());
		System.out.println("avgDuplicatedLineDensity: " + processor.getUser().getAvgDuplicatedLineDensity());
		System.out.println("avgDuplicatedBlocks: " + processor.getUser().getAvgDuplicatedBlocks());
		System.out.println("avgDuplicatedLines: " + processor.getUser().getAvgDuplicatedLines());
		System.out.println("avgDuplicatedFiles: " + processor.getUser().getAvgDuplicatedFiles());
		System.out.println("avgMajorLanguageLOC: " + processor.getUser().getAvgMajorLanguageLOC());
		System.out.println("avgLines: " + processor.getUser().getAvgLines());
		System.out.println("avgStatements: " + processor.getUser().getAvgStatements());
		System.out.println("avgFunctions: " + processor.getUser().getAvgFunctions());
		System.out.println("avgClasses: " + processor.getUser().getAvgClasses());
		System.out.println("avgFiles: " + processor.getUser().getAvgFiles());
		System.out.println("avgDirectories: " + processor.getUser().getAvgDirectories());
		System.out.println("avgComplexity: " + processor.getUser().getAvgComplexity());
		System.out.println("avgFileComplexity: " + processor.getUser().getAvgFileComplexity());
		System.out.println("avgFunctionComplexity: " + processor.getUser().getAvgFunctionComplexity());
		System.out.println("avgClassComplexity: " + processor.getUser().getAvgClassComplexity());
		System.out.println("avgCommentLinesDensity: " + processor.getUser().getAvgCommemtLineDensity());
		System.out.println("avgCommentLines: " + processor.getUser().getAvgCommentLines());
		System.out.println("avgPublicAPI: " + processor.getUser().getAvgPublicAPI());
		System.out.println("avgDocumentedAPIDensity: " + processor.getUser().getAvgDocumentedAPIDensity());
		System.out.println("avgUndocumentedAPI: " + processor.getUser().getAvgUndocumentedAPI());
		System.out.println("**********contributed repositories**********");
		System.out.println("numOfPullRequest: " + processor.getUser().getNumOfPullRequest());
		System.out.println("numOfAcceptedPR: " + processor.getUser().getNumOfAcceptedPR());
		System.out.println("numOfContributedRepos: " + processor.getUser().getNumOfContributedRepos());
		System.out.println("avgCommits: " + processor.getUser().getAvgCommits());
		System.out.println("avgAdditions: " + processor.getUser().getAvgAdditions());
		System.out.println("avgDeletions: " + processor.getUser().getAvgDeletions());
		System.out.println("avgChangedFiles: " + processor.getUser().getAvgChangedFiles());
		System.out.println("avgDaysIntervalOfPR: " + processor.getUser().getAvgDaysIntervalOfPR());
	}

}

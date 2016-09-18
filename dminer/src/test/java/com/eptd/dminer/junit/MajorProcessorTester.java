package com.eptd.dminer.junit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eptd.dminer.core.Authorization;
import com.eptd.dminer.core.Configuration;
import com.eptd.dminer.crawler.MajorRepoProcessor;
import com.eptd.dminer.processor.DataPoster;
import com.eptd.dminer.processor.ProjectLogger;
import com.google.gson.JsonElement;

import junit.framework.Assert;

public class MajorProcessorTester {
	static ProjectLogger logger;
	static Authorization auth;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger = new ProjectLogger("https://api.github.com/repos/byoutline/kickmaterial",Configuration.getDefaultConfig());
		auth = new Authorization(logger).createOAuthToken();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Revoking OAuth Token ... " + auth.revokeOAuthToken());
	}
	
	@Test
	public void test() {
		MajorRepoProcessor processor = new MajorRepoProcessor(logger,0,auth,"C:\\EPTD");
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
		processor.getRepo().getContributors().stream().forEach(c->{
			System.out.println("----------------------------------------------------");
			System.out.println("userURL: " + c.getUserURL());
			System.out.println("userHTML: " + c.getUserHTML());
			System.out.println("userId: " + c.getUserId());
			System.out.println("login: " + c.getLogin());
			System.out.println("name: " + c.getName());
			System.out.println("numOfPublicRepos: " + c.getNumOfPublicRepos());
			System.out.println("numOfAssignees: " + c.getNumOfAssignees());
			System.out.println("followers: " + c.getFollowers());
			System.out.println("folderPath: " + c.getFolderPath());
			System.out.println("**********owned repositories**********");
			System.out.println("numOfAnalyzedRepos: " + c.getNumOfAnalyzedRepos());
			System.out.println("avgSize: " + c.getAvgSize());
			System.out.println("avgStargazersCount: " + c.getAvgStargazersCount());
			System.out.println("avgSubscribersCount: " + c.getAvgSubscribersCount());
			System.out.println("avgForksCount: " + c.getAvgForksCount());
			System.out.println("avgIssuesCount: " + c.getAvgIssuesCount());
			System.out.println("avgHandledIssuesRatio: " + c.getAvgHandledIssuesRatio());
			System.out.println("avgIssueHandledDays: " + c.getAvgIssueHandledDays());
			System.out.println("**********owned repositories with SonarQube**********");
			System.out.println("avgBugs: " + c.getAvgBugs());
			System.out.println("avgVulnerabilities: " + c.getAvgVulnerabilities());
			System.out.println("avgCodeSmells: " + c.getAvgCodeSmells());
			System.out.println("avgSqaleIndex: " + c.getAvgSqaleIndex());
			System.out.println("avgDebtRatio: " + c.getAvgDebtRatio());
			System.out.println("avgDuplicatedLineDensity: " + c.getAvgDuplicatedLineDensity());
			System.out.println("avgDuplicatedBlocks: " + c.getAvgDuplicatedBlocks());
			System.out.println("avgDuplicatedLines: " + c.getAvgDuplicatedLines());
			System.out.println("avgDuplicatedFiles: " + c.getAvgDuplicatedFiles());
			System.out.println("avgMajorLanguageLOC: " + c.getAvgMajorLanguageLOC());
			System.out.println("avgLines: " + c.getAvgLines());
			System.out.println("avgStatements: " + c.getAvgStatements());
			System.out.println("avgFunctions: " + c.getAvgFunctions());
			System.out.println("avgClasses: " + c.getAvgClasses());
			System.out.println("avgFiles: " + c.getAvgFiles());
			System.out.println("avgDirectories: " + c.getAvgDirectories());
			System.out.println("avgComplexity: " + c.getAvgComplexity());
			System.out.println("avgFileComplexity: " + c.getAvgFileComplexity());
			System.out.println("avgFunctionComplexity: " + c.getAvgFunctionComplexity());
			System.out.println("avgClassComplexity: " + c.getAvgClassComplexity());
			System.out.println("avgCommentLinesDensity: " + c.getAvgCommemtLineDensity());
			System.out.println("avgCommentLines: " + c.getAvgCommentLines());
			System.out.println("avgPublicAPI: " + c.getAvgPublicAPI());
			System.out.println("avgDocumentedAPIDensity: " + c.getAvgDocumentedAPIDensity());
			System.out.println("avgUndocumentedAPI: " + c.getAvgUndocumentedAPI());
			System.out.println("**********contributed repositories**********");
			System.out.println("numOfPullRequest: " + c.getNumOfPullRequest());
			System.out.println("numOfAcceptedPR: " + c.getNumOfAcceptedPR());
			System.out.println("numOfContributedRepos: " + c.getNumOfContributedRepos());
			System.out.println("avgCommits: " + c.getAvgCommits());
			System.out.println("avgAdditions: " + c.getAvgAdditions());
			System.out.println("avgDeletions: " + c.getAvgDeletions());
			System.out.println("avgChangedFiles: " + c.getAvgChangedFiles());
			System.out.println("avgDaysIntervalOfPR: " + c.getAvgDaysIntervalOfPR());
		});
		//send major repo data to data saver
		JsonElement jsonElement = DataPoster.getInstance().post(Configuration.getDefaultConfig().getDsaverURL(),processor.getRepo(),1);
		System.out.println(jsonElement.getAsJsonObject().toString());
	}

}

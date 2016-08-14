package com.eptd.dminer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.eptd.dminer.core.Authorization;
import com.eptd.dminer.core.Configuration;
import com.eptd.dminer.crawler.GitHubAPIClient;
import com.eptd.dminer.crawler.MajorRepoProcessor;
import com.eptd.dminer.crawler.SearchQueryGenerator;
import com.eptd.dminer.processor.DataPoster;
import com.eptd.dminer.processor.ProjectLogger;
import com.eptd.dminer.core.Client;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MajorRepoTester {

	public static void main(String[] args) {
		int taskID = 1;
		Configuration config = Configuration.getDefaultConfig();
		ProjectLogger mainLogger = new ProjectLogger("https://api.github.com/repos/qiaozhang/dminer",config);
		Authorization auth = new Authorization(mainLogger).createOAuthToken();
		//ArrayList<String> urls = new ArrayList<String>();
		//urls.add("https://api.github.com/repos/apache/tajo");
		//urls.add("https://api.github.com/repos/laobie/StatusBarUtil");
		//urls.add("https://api.github.com/repos/lyft/scissors");
		//urls.add("https://api.github.com/repos/square/keywhiz");
		//urls.add("https://api.github.com/repos/spotify/helios");
		//urls.add("https://api.github.com/repos/byoutline/kickmaterial");
		//urls.add("https://api.github.com/repos/yahoo/mysql_perf_analyzer");
		String path = "C:\\EPTD";
		
		SearchQueryGenerator generator = new SearchQueryGenerator("repo")
				.addSearchTerm("size", "4000..40000")
				.addSearchTerm("forks", "200..210")
				.addSearchTerm("language", config.getMajorLanguage());//MAJORLANGUAGE
		GitHubAPIClient client = new GitHubAPIClient(auth,mainLogger)
				.addParameter("q", generator.getSearchStr())
				.addParameter("sort", "forks");
		JsonElement response = client.get(generator.getQueryBase());
		List<JsonObject> repos = IntStream.rangeClosed(0, response.getAsJsonArray().size()-1)
				.mapToObj(i->response.getAsJsonArray().get(i).getAsJsonObject())
				.collect(Collectors.toList());
		//********************************
		repos.parallelStream().map(repo->{
			ProjectLogger logger = new ProjectLogger(repo.get("url").getAsString(),config);
			MajorRepoProcessor processor = new MajorRepoProcessor(logger,taskID,auth,path);
			if(processor.process() == true){
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
							+ processor.getRepo().getSonarMetrics().get(i).getValue() + "("
							+ processor.getRepo().getSonarMetrics().get(i).getFormattedValue() + ")");
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
					System.out.println("avgMajorLanguageLOC: " + c.getAvgMajorLanguageLOC());
					System.out.println("avgDebtRatio: " + c.getAvgDebtRatio());
					System.out.println("avgSqaleIndex: " + c.getAvgSqaleIndex());
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
				try {
					JsonElement jsonElement = DataPoster.getInstance().post(config.getDsaverURL(),processor.getRepo(),1);
					System.out.println(jsonElement.getAsJsonObject().toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return true;
		}).count();
						
		
		//********************************
		System.out.println("Revoking OAuth Token ... " + auth.revokeOAuthToken());
	}

}

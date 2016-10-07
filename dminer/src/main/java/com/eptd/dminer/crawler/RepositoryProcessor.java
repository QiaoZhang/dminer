package com.eptd.dminer.crawler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.eptd.dminer.core.Authorization;
import com.eptd.dminer.core.MajorRepository;
import com.eptd.dminer.core.Repository;
import com.eptd.dminer.core.SonarMetrics;
import com.eptd.dminer.processor.ProjectCleaner;
import com.eptd.dminer.processor.ProjectLogger;
import com.eptd.dminer.processor.SonarAnalysisProcessor;
import com.eptd.dminer.processor.SonarPropertiesWriter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RepositoryProcessor {
	private ForkJoinPool pool;
	protected ProjectLogger logger;
	protected String repositoryURL;
	protected Authorization auth;
	protected String filePath;	
	protected Repository repo;		
	protected final String[] issueEvents = {"closed","referenced","assigned","labeled","milestoned","locked"};
	
	/**
	 * Construct a GitHub repository processor with default encoding
	 * @param level The logging level to differentiate working tasks
	 * @param repositoryURL The GitHub API URL of a repository
	 * @param auth The authorization information to conduct GitHub client requests
	 * @param filePath The local file path to store cloned repository
	 */
	public RepositoryProcessor(String repositoryURL, Authorization auth, String filePath,ProjectLogger logger){
		if(logger != null)
			this.logger = new ProjectLogger(logger).append("RepositoryProcessor");
		this.auth = auth;
		this.repositoryURL = repositoryURL;
		this.filePath = filePath;		
		this.repo = new Repository();
		this.pool = new ForkJoinPool(3);
	}
	
	public RepositoryProcessor(String repositoryURL, Authorization auth, String filePath,ProjectLogger logger,ForkJoinPool pool){
		if(logger != null)
			this.logger = new ProjectLogger(logger).append("RepositoryProcessor");
		this.auth = auth;
		this.repositoryURL = repositoryURL;
		this.filePath = filePath;		
		this.repo = new Repository();
		this.pool = pool;
	}
	
	protected void updateMajorRepo(MajorRepository repo, ProjectLogger logger){
		this.repo = repo;
		this.logger = logger;
	}
	
	public boolean process(){
		try {
			//step 1: get json data of a repo
			final JsonElement jsonRepo = new GitHubAPIClient(auth,logger).get(repositoryURL);
			if(jsonRepo.isJsonObject()&&jsonRepo.getAsJsonObject().get("url")!=null){
				//step 2: extract basic info and initialized repo
				if(pool.submit(()->extractBasicInfo(jsonRepo.getAsJsonObject())).get()){
					//step 3: extract extra attributes
					pool.submit(()->extractAttributes(jsonRepo.getAsJsonObject()));	
					//step 4: conduct SonarQube analysis
					SonarAnalysisProcessor sonarProcessor = new SonarAnalysisProcessor(
						repo.getRepositoryHTML(),filePath,repo.getProjectID(),
			    		repo.getProjectName(),repo.getOwnerLogin(),repo.getUserType(),
			    		repo.getLanguage(),repo.getVersion(),logger);
					ArrayList<SonarMetrics> result = pool.submit(()->sonarProcessor.process()).get();
					//clean up first
					ProjectCleaner.getInstance().deleteSonar(sonarProcessor.getProjectKey());
					//set sonar results
					if(result!=null){
						repo.setSonarMetrics(result);
						return true;
					}else
						return false;
				}else
					return false;
			}else{
				logger.error("Captured data of url "+repositoryURL+" is not valid.");
				return false;
			}
		} catch (Exception e) {
			ProjectCleaner.getInstance().deleteSonar(SonarPropertiesWriter.getProjectKey(repo.getProjectID(), repo.getProjectName(), repo.getOwnerLogin(), repo.getUserType()));
			logger.error("Unknown Exception when processing "+repositoryURL,e);
			return false;
		}		
	}
	
	private boolean extractBasicInfo(JsonObject jsonRepo){
		try{
			repo.setRepositoryURL(this.repositoryURL);
			repo.setRepositoryHTML(jsonRepo.get("html_url").getAsString());		
			repo.setProjectID(jsonRepo.get("id").getAsLong());
			repo.setProjectName(jsonRepo.get("name").getAsString());
			repo.setOwnerLogin(jsonRepo.get("owner").getAsJsonObject().get("login").getAsString());
			repo.setUserType(jsonRepo.get("owner").getAsJsonObject().get("type").getAsString().toLowerCase());
			repo.setLanguage(jsonRepo.get("language").getAsString().toLowerCase());
			//set new file path
			this.filePath += "\\"+repo.getProjectName()+"-"+repo.getProjectID();
			repo.setFilePath(this.filePath);
			
			//get the latest tag name
			JsonElement tags = new GitHubAPIClient(auth,logger).get(jsonRepo.get("tags_url").getAsString());
			if(tags.isJsonArray()&&tags.getAsJsonArray().size()>0
					&&!tags.getAsJsonArray().get(0).getAsJsonObject().get("name").isJsonNull())
				repo.setVersion(tags.getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString());
			else
				repo.setVersion("1.0.0");
			return true;
		}catch(Exception e){
			logger.error("Unknown Exception when extracting basic info of "+repositoryURL,e);
			return false;
		}		
	}
	
	private void extractAttributes(JsonObject jsonRepo){
		repo.setSize(jsonRepo.get("size").getAsLong());
		repo.setStargazersCount(jsonRepo.get("stargazers_count").getAsLong());
		repo.setSubscribersCount(jsonRepo.get("subscribers_count").getAsLong());
		repo.setForksCount(jsonRepo.get("forks_count").getAsLong());
		repo.setCreatedAt(new DateTime(jsonRepo.get("created_at").getAsString()));
		
		//get the open issue counts and average issue close days
		JsonElement response = new GitHubAPIClient(auth,logger).get(repo.getRepositoryURL()+"/issues");
		if(response.isJsonArray()){
			if(response.getAsJsonArray().size()>0){
				List<JsonObject> issues = IntStream.rangeClosed(0, response.getAsJsonArray().size()-1)
												.mapToObj(i->response.getAsJsonArray().get(i).getAsJsonObject())
												.collect(Collectors.toList());
				List<Double> dateDistance = issues.stream().map(issue -> {
					DateTime start = new DateTime(issue.get("created_at").getAsString());
					JsonElement events = new GitHubAPIClient(auth,logger).get(issue.get("events_url").getAsString());
					if(events.isJsonArray()&&events.getAsJsonArray().size()>0)
					{
						for(int i=0;i<events.getAsJsonArray().size();i++){
							JsonObject obj = events.getAsJsonArray().get(i).getAsJsonObject();
							if(Arrays.asList(issueEvents).contains(obj.getAsJsonObject().get("event").getAsString())){
								/*
								 * calculate the average event handled days by adding up all days difference of
								 * 		the day of issue created
								 * 	  & the day of first qualified event created
								 * */
								return (double)Math.abs(Days.daysBetween(
									start.toLocalDate(), 
									new DateTime(obj.getAsJsonObject().get("created_at").getAsString()).toLocalDate()
									).getDays());
							}
						}
					}
					return 0.0;
				}).filter(d->d!=0.0).collect(Collectors.toList());
				repo.setIssuesCount(issues.size());
				repo.setHandledIssuesCount(dateDistance.size());
				if(dateDistance.size()!=0)
					repo.setAvgIssueHandledDays(dateDistance.stream().mapToDouble(Double::doubleValue).sum()/dateDistance.size());
				else
					repo.setAvgIssueHandledDays(0);
			}
		}else
			logger.error("Search results of repo id "+repo.getProjectID()+"'s issues is not valid.");
	}
	
	public Repository getRepo(){
		return this.repo;
	}
}

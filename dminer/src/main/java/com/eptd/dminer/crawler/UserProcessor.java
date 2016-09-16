package com.eptd.dminer.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.eptd.dminer.core.Authorization;
import com.eptd.dminer.core.Configuration;
import com.eptd.dminer.core.User;
import com.eptd.dminer.processor.ProjectCleaner;
import com.eptd.dminer.processor.ProjectLogger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class UserProcessor {
	private ProjectLogger logger;
	
	private String userURL;
	private Authorization auth;
	private String folderPath;
	
	private User user;
	private JsonArray preRepos;
	
	private int MAXREPOS;
	private String MAJORLANGUAGE;
	
	public UserProcessor(String userURL,Authorization auth,String filePath,ProjectLogger logger){
		this.auth = auth;
		this.userURL = userURL;		
		this.folderPath = filePath + "\\" + "eptd-contributors";
		this.user = new User(userURL);
		this.logger = new ProjectLogger(logger).append("UserProcessor");
		//no pre-processed user repo info
		this.preRepos = null;
		this.Config(logger.getConfig());
	}
	
	public UserProcessor(String userURL,JsonArray preRepos,Authorization auth,String filePath,ProjectLogger logger){
		this.auth = auth;
		this.userURL = userURL;		
		this.folderPath = filePath + "\\" + "eptd-contributors";
		this.user = new User(userURL);
		this.logger = new ProjectLogger(logger).append("UserProcessor");
		//pre-processed user repo info
		this.preRepos = preRepos;
		this.Config(logger.getConfig());
	}
	
	private void Config(Configuration config){
		MAXREPOS = config.getMaxRepos();
		MAJORLANGUAGE = config.getMajorLanguage();
	}
	
	public boolean process(){
		try {
			//step 1: get json data of a user
			final JsonElement jsonUser = new GitHubAPIClient(auth,logger).get(userURL);			
			if(jsonUser.isJsonObject()&&jsonUser.getAsJsonObject().get("html_url")!=null){
				ForkJoinPool pool = new ForkJoinPool(2);
				//step 2: extract basic info and initialized repo
				extractBasicInfo(jsonUser.getAsJsonObject());
				//step 3: process owned repositories with RepositoryProcessor
				pool.submit(()->extractOwnReposInfo());
				//step 4: process contributed repositories with search API
				pool.submit(()->extractContributedReposInfo());
				pool.awaitQuiescence(120, TimeUnit.MINUTES);		
				user.setAvgReposData();
				ProjectCleaner.getInstance().deleteFolder(folderPath);
				return true;
			}else{
				logger.error("Captured data of url "+userURL+" is not valid.");
				System.out.println(jsonUser.toString());
				ProjectCleaner.getInstance().deleteFolder(folderPath);
				return false;
			}			
		} catch (Exception e) {
			logger.error("Unknown Exception when processing "+userURL,e);
			ProjectCleaner.getInstance().deleteFolder(folderPath);
			return false;
		}	
	}
	
	private void extractBasicInfo(JsonObject jsonUser){
		user.setUserHTML(jsonUser.get("html_url").getAsString());
		user.setUserId(jsonUser.get("id").getAsLong());
		user.setLogin(jsonUser.get("login").getAsString());
		if(!jsonUser.get("name").isJsonNull())
			user.setName(jsonUser.get("name").getAsString());
		user.setNumOfPublicRepos(jsonUser.get("public_repos").getAsInt());
		user.setFollowers(jsonUser.get("followers").getAsLong());
		
		//check if the user is an assignee of repo[i]
		SearchQueryGenerator generator = new SearchQueryGenerator("issue")
				.addSearchTerm("assignee", user.getLogin());
		JsonElement response = new GitHubAPIClient(auth,logger)
				.addParameter("q", generator.getSearchStr())
				.get(generator.getQueryBase());
		if(response.isJsonArray()){
			if(response.getAsJsonArray().size()>0){
				List<JsonObject> issues = IntStream.rangeClosed(0, response.getAsJsonArray().size()-1)
						.mapToObj(i->response.getAsJsonArray().get(i).getAsJsonObject())
						.collect(Collectors.toList());
				List<String> assignees = issues.stream().map(i->i.get("repository_url").getAsString()).distinct().collect(Collectors.toList());
				user.setNumOfAssignees(assignees.size());
			}
		}else
			logger.error("Search results of "+user.getLogin()+"'s issues is not valid.");
		
		//set new folder path
		this.folderPath += "\\"+user.getLogin()+"-"+user.getUserId();
		user.setFolderPath(this.folderPath);
	}
	
	private void extractOwnReposInfo() {
		JsonElement response;
		//if user's repos has already searched
		if(preRepos!=null){
			response = preRepos;
		}else {
			SearchQueryGenerator generator = new SearchQueryGenerator("repo")
					.addSearchTerm("user", user.getLogin())
					.addSearchTerm("language", MAJORLANGUAGE);//MAJORLANGUAGE
			GitHubAPIClient client = new GitHubAPIClient(auth,logger)
					.addParameter("q", generator.getSearchStr())
					.addParameter("sort", "forks");
			client.setMaxPage((int)Math.ceil((double)MAXREPOS/(double)client.getMaxPerPage()));//MAXREPOS
			response = client.get(generator.getQueryBase());
		}
		//process repos in parallel
		if(response.isJsonArray()){
			if(response.getAsJsonArray().size()>0){
				List<JsonObject> repos = IntStream.rangeClosed(0, response.getAsJsonArray().size()-1)
						.mapToObj(i->response.getAsJsonArray().get(i).getAsJsonObject())
						.filter(i->!i.get("url").getAsString().equals(logger.getRepoURL()))
						.collect(Collectors.toList());
				AtomicReference<User> atomicUser = new AtomicReference<User>(this.getUser());
				repos.parallelStream().limit(MAXREPOS).map(repo -> {
					RepositoryProcessor processor = new RepositoryProcessor(repo.get("url").getAsString(),auth,this.folderPath,logger);
					if(processor.process()){
						if(processor.getRepo().getSonarMetrics().size()>0){
							atomicUser.get().addOwnRepo(processor.getRepo());
							return processor.getRepo();
						}else
							return null;
					}else
						return null;
				})
				.filter(repo->repo!=null)
				.count();
			}
		}else
			logger.error("Search results of "+user.getLogin()+"'s repos is not valid.");
	}
	
	private void extractContributedReposInfo() {		
		AtomicInteger progress = new AtomicInteger();
		SearchQueryGenerator generator = new SearchQueryGenerator("issue")
				.addSearchTerm("type", "pr")
				.addSearchTerm("author", user.getLogin());
		JsonElement response = new GitHubAPIClient(auth,logger)
				.addParameter("q", generator.getSearchStr())
				.addParameter("sort", "updated")
				.get(generator.getQueryBase());
		if(response.isJsonArray()){
			if(response.getAsJsonArray().size()>0){
				List<JsonObject> issues = IntStream.rangeClosed(0, response.getAsJsonArray().size()-1)
						.mapToObj(i->response.getAsJsonArray().get(i).getAsJsonObject()).filter(i->i!=null)
						.collect(Collectors.toList());//List<Double> totals = 
				user.setNumOfPullRequest(issues.size());
				/*
				 * [0] numOfAcceptedPR
				 * [1] totalCommits
				 * [2] totalAdditions
				 * [3] totalDeletions
				 * [4] totalChangedFiles
				 */
				int[] init = {0,0,0,0,0};
				AtomicIntegerArray totals = new AtomicIntegerArray(init);
				List<DateTime> closedDates = new ArrayList<DateTime>();
				Long numOfContributedRepos = issues.parallelStream().map(issue -> {
					JsonElement pull = new GitHubAPIClient(auth,logger).get(issue.get("pull_request").getAsJsonObject().get("url").getAsString());
					if(pull.isJsonObject()&&!pull.getAsJsonObject().get("closed_at").isJsonNull()){
						totals.incrementAndGet(0);// numOfAcceptedPR
						totals.addAndGet(1, pull.getAsJsonObject().get("commits").getAsInt());// totalCommits
						totals.addAndGet(2, pull.getAsJsonObject().get("additions").getAsInt());// totalAdditions
						totals.addAndGet(3, pull.getAsJsonObject().get("deletions").getAsInt());// totalDeletions
						totals.addAndGet(4, pull.getAsJsonObject().get("changed_files").getAsInt());// totalChangedFiles
						closedDates.add(new DateTime(pull.getAsJsonObject().get("closed_at").getAsString()));
						progress.incrementAndGet();
						return issue.get("repository_url").getAsString();
					}
					return null;
				}).filter(url->url!=null).distinct().count();
				if(totals.get(0)!=0){
					//Set attributes except average date interval
					user.setNumOfAcceptedPR(totals.get(0));
					user.setNumOfContributedRepos(numOfContributedRepos.intValue());
					user.setAvgCommits((double)totals.get(1)/(double)totals.get(0));
					user.setAvgAdditions((double)totals.get(2)/(double)totals.get(0));
					user.setAvgDeletions((double)totals.get(3)/(double)totals.get(0));
					user.setAvgChangedFiles((double)totals.get(4)/(double)totals.get(0));
					//Set average date interval
					AtomicLong totalPRInterval = new AtomicLong(0);
					AtomicReference<DateTime> lastDate = new AtomicReference<DateTime>();
					closedDates.parallelStream().sorted().collect(Collectors.toList()).forEach(date->{
						if(lastDate.get() != null){
							totalPRInterval.addAndGet(Math.abs(Days.daysBetween(lastDate.getAndSet(date).toLocalDate(),date.toLocalDate()).getDays()));
						}else
							lastDate.set(date);
					});
					if(totals.get(0)!=1)
						user.setAvgDaysIntervalOfPR(totalPRInterval.doubleValue()/(totals.get(0)-1));
				}	
			}
		}else
			logger.error("Search results of "+user.getLogin()+"'s pull requests is not valid.");
	}
	
	public User getUser(){
		return this.user;
	}
}

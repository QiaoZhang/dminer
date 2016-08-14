package com.eptd.dminer.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.eptd.dminer.core.Authorization;
import com.eptd.dminer.core.Configuration;
import com.eptd.dminer.core.MajorRepository;
import com.eptd.dminer.core.User;
import com.eptd.dminer.processor.ProjectLogger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MajorRepoProcessor extends RepositoryProcessor{
	// configuration with default value
	private int MAXREPOS;
	private String MAJORLANGUAGE;
	private double MAXRATIO;
	private double TOTALRATIO;
	private double NOOBRATIO;
	private int UPPERBOUND;
	private int LOWERBOUND;
	
	private MajorRepository majorRepo;
	
	/**
	 * Construct a GitHub repository processor with default encoding
	 * @param repositoryURL The GitHub API URL of a repository
	 * @param auth The authorization information to conduct GitHub client requests
	 * @param filePath The local file path to store cloned repository
	 */
	public MajorRepoProcessor(ProjectLogger logger, int taskID, Authorization auth, String filePath){
		super(logger.getRepoURL()
				,auth
				,filePath+"\\"+logger.getProjectName().replace(".", "\\")
				,null);//the logging level of major repository is 0
		this.majorRepo = new MajorRepository().setTaskID(taskID);
		super.updateMajorRepo(majorRepo,logger);
		this.Config(logger.getConfig());
	}

	private void Config(Configuration config){
		MAXREPOS = config.getMaxRepos();
		MAJORLANGUAGE = config.getMajorLanguage();
		MAXRATIO = config.getMaxRatio();
		TOTALRATIO = config.getTotalRatio();
		NOOBRATIO = config.getNoobRatio();
		UPPERBOUND = config.getUpperBound();
		LOWERBOUND = config.getLowerBound();
	}
	
	@Override
	public boolean process(){
		try {
			List<JsonObject> contributors;
			//pre-processed user repo info
			List<String> userLogins = new ArrayList<String>();
			List<JsonArray> userRepos = new ArrayList<JsonArray>();
			//step 1: filter original contributors down to the scope			
			JsonElement response = new GitHubAPIClient(auth,logger).get(repositoryURL+"/contributors");		
			if(response.isJsonArray()){
				List<JsonObject> originalContributors = IntStream.rangeClosed(0, response.getAsJsonArray().size()-1)
						.mapToObj(i->response.getAsJsonArray().get(i).getAsJsonObject())
						.collect(Collectors.toList());			
				final long maxConNum = originalContributors.get(0).get("contributions").getAsLong();//maximum contributions number
				AtomicLong totalConNum = new AtomicLong(0);//total contributions number
				contributors = originalContributors.stream()
				.map(u->{
					totalConNum.addAndGet(u.get("contributions").getAsLong());
					return u;
				})
				.map(u->{
					if(u.get("contributions").getAsLong() >= maxConNum*MAXRATIO//MAXRATIO: drop contributors with contribution less than 5% of the most commits
							&& u.get("contributions").getAsLong() >= totalConNum.get()*TOTALRATIO)//TOTALRATIO: drop contributors with contribution less than 1% of the total commits 
						return u;
					return null;
				})		
				.filter(u->u!=null)
				.limit(UPPERBOUND)//UPPERBOUND: limit the number of analyzed contributors					
				.collect(Collectors.toList());
				//find how many contributors has no major language programming experience
				List<JsonObject> noobContributors = contributors.parallelStream()
				.map(u->{
					//drop contributors with no public repository written by MAJORLANGUAGE
					SearchQueryGenerator generator = new SearchQueryGenerator("repo")
							.addSearchTerm("user", u.get("login").getAsString())
							.addSearchTerm("language", MAJORLANGUAGE);//MAJORLANGUAGE
					GitHubAPIClient client = new GitHubAPIClient(auth,logger)
							.addParameter("q", generator.getSearchStr())
							.addParameter("sort", "forks");
					client.setMaxPage((int)Math.ceil((double)MAXREPOS/(double)client.getMaxPerPage()));//MAXREPOS
					JsonElement repos = client.get(generator.getQueryBase());
					if(repos.isJsonArray()&&repos.getAsJsonArray().size()<1)
						return u;
					userLogins.add(u.get("login").getAsString());
					userRepos.add(repos.getAsJsonArray());
					return null;
				})
				.filter(u->u!=null)
				.collect(Collectors.toList());			
				if(contributors.size()<LOWERBOUND||noobContributors.size()>=(contributors.size()*NOOBRATIO)){
					//LOWERBOUND: minimum analyzed contributors
					//NOOBRATIO: ratio of no major language programming experience
					System.out.println("Major Repo with url "+repositoryURL+" is not conforming filtering criteria.");
					return false;
				}
			}else{
				logger.error("Captured data of url "+repositoryURL+"/contributors is not valid.");
				return false;
			}
			ForkJoinPool pool = new ForkJoinPool(1);
			//step 2: clone, analyze, and extract major repo data		
			pool.submit(()->super.process());		
			//step 3: process qualified contributors
			List<User> userData = contributors.stream().map(c->{
				UserProcessor processor;
				if(userLogins.contains(c.get("login").getAsString()))
					processor = new UserProcessor(c.get("url").getAsString(),userRepos.get(userLogins.indexOf(c.get("login").getAsString())),auth,filePath,logger);
				else
					processor = new UserProcessor(c.get("url").getAsString(),auth,filePath,logger);
				if(processor.process())
					return processor.getUser();
				else
					return null;				
			})
			.filter(u->u!=null)
			.collect(Collectors.toList());
			this.majorRepo.addAllContributors(userData);
			pool.awaitQuiescence(240, TimeUnit.MINUTES);
			RepositoryProcessor.deleteFolder(filePath);
			return true;
		} catch (Exception e) {
			logger.error("Unknown Exception when processing "+repositoryURL,e);
			return false;
		}		
	}
	
	@Override
	public MajorRepository getRepo(){
		return this.majorRepo;
	}

	
}

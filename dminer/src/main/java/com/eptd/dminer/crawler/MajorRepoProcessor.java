package com.eptd.dminer.crawler;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.eptd.dminer.core.Authorization;
import com.eptd.dminer.core.Configuration;
import com.eptd.dminer.core.FilteredUser;
import com.eptd.dminer.core.MajorRepository;
import com.eptd.dminer.core.User;
import com.eptd.dminer.processor.FilteredUserProcessor;
import com.eptd.dminer.processor.ProjectCleaner;
import com.eptd.dminer.processor.ProjectLogger;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MajorRepoProcessor extends RepositoryProcessor{
	// configuration with default value
	/*
	 * Deprecated boundaries
	 * private int MAXREPOS;
	 * private String MAJORLANGUAGE;
	 * private double MAXRATIO;
	 * private double TOTALRATIO;
	 * private double NOOBRATIO;
	 */
	
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
		this.majorRepo = new MajorRepository().setNewTaskID(taskID);
		super.updateMajorRepo(majorRepo,logger);
		this.Config(logger.getConfig());
	}

	private void Config(Configuration config){
		/*
		 * Deprecated boundaries configuration
		 * MAXREPOS = config.getMaxRepos();
		 * MAJORLANGUAGE = config.getMajorLanguage();
		 * MAXRATIO = config.getMaxRatio();
		 * TOTALRATIO = config.getTotalRatio();
		 * NOOBRATIO = config.getNoobRatio();
		 */
		UPPERBOUND = config.getUpperBound();
		LOWERBOUND = config.getLowerBound();
	}
	
	@Override
	public boolean process(){
		try {
			//limit the parallel pool size
			ForkJoinPool pool = new ForkJoinPool(16);
			System.out.println("**************************************************");
			System.out.println("Processing Major Repo "+repositoryURL);		
			JsonElement response = new GitHubAPIClient(auth,logger).get(repositoryURL+"/contributors");		
			if(response.isJsonArray()){
				//step 1: clone, analyze, and extract major repo data			
				pool.submit(()->super.process());
				//step 2: process contributors
				List<JsonObject> originalContributors = IntStream.rangeClosed(0, response.getAsJsonArray().size()-1)
						.mapToObj(i->response.getAsJsonArray().get(i).getAsJsonObject())
						.collect(Collectors.toList());
				//filtering major repo based on lowerbound
				if(originalContributors.size()<LOWERBOUND){
					//LOWERBOUND: minimum analyzed contributors
					//NOOBRATIO: ratio of no major language programming experience
					System.out.println("Major Repo with url "+repositoryURL+" is not conforming filtering criteria.");
					return false;
				}
				//initialization for parallel processing
				final long maxConNum = originalContributors.get(0).get("contributions").getAsLong();//maximum contributions number
				AtomicLong totalConNum = new AtomicLong(0);//total contributions number
				//step 2.1: process filtered contributors
				List<FilteredUser> filteredContributors = pool.submit(()->{
					List<FilteredUser> filteredCons = originalContributors.parallelStream()			
					.skip(UPPERBOUND)//UPPERBOUND: limit the number of analyzed contributors
					.peek(fc->totalConNum.addAndGet(fc.get("contributions").getAsLong()))//add up all contributors' contributions
					.map(fc->{//skip operation will consume all items
						FilteredUserProcessor processor = new FilteredUserProcessor(fc,logger);
						if(processor.process())
							return processor.getFilteredUser();
						else
							return null;
					})
					.onClose(()->{if(originalContributors.size()>UPPERBOUND)System.out.println("Filtered users processed:"+(originalContributors.size()-UPPERBOUND));})
					.filter(u->u!=null)
					.collect(Collectors.toList());
					return filteredCons;
				}).get();				
				//step 2.2: process qualified contributors
				List<User> contributors = pool.submit(()->{
					List<User> cons = originalContributors.stream()
					.limit(UPPERBOUND)
					.peek(c->totalConNum.addAndGet(c.get("contributions").getAsLong()))
					.map(c->{
						UserProcessor processor = new UserProcessor(c.get("url").getAsString(),auth,filePath,logger,pool);
						if(processor.process()){
							processor.getUser().setContribution(c.get("contributions").getAsLong());
							return processor.getUser();
						}else
							return null;				
					})
					.filter(u->u!=null)
					.collect(Collectors.toList());
					return cons;
				}).get();
				
				//wait for major repo analyzed
				pool.awaitQuiescence(24, TimeUnit.HOURS);
				//finalization
				this.majorRepo.setMaxContribution(maxConNum);
				this.majorRepo.setTotalContribution(totalConNum.get());
				this.majorRepo.addAllFilteredContributors(filteredContributors);
				this.majorRepo.addAllContributors(contributors);
				//clean up
				ProjectCleaner.getInstance().deleteFolder(filePath);
			}else{
				logger.error("Captured data of url "+repositoryURL+"/contributors is not valid.");
				logger.error(response.toString());
				return false;
			}
			//clean up
			ProjectCleaner.getInstance().deleteFolder(filePath);
			return true;
		} catch (Exception e) {
			logger.error("Unknown Exception when processing "+repositoryURL,e);
			//clean up
			ProjectCleaner.getInstance().deleteFolder(filePath);
			return false;
		}		
	}
	
	@Override
	public MajorRepository getRepo(){
		return this.majorRepo;
	}

	
}

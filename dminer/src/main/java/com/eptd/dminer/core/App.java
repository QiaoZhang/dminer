package com.eptd.dminer.core;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.eptd.dminer.crawler.GitHubAPIClient;
import com.eptd.dminer.crawler.MajorRepoProcessor;
import com.eptd.dminer.crawler.SearchQueryGenerator;
import com.eptd.dminer.processor.DataPoster;
import com.eptd.dminer.processor.ProjectCleaner;
import com.eptd.dminer.processor.ProjectLogger;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class App {
	private static final int SLEEP = 1; //mins

	public static void main(String[] args) {
		ProjectLogger mainLogger = new ProjectLogger("https://api.github.com/repos/qiaozhang/dminer",Configuration.getDefaultConfig());
		try {
			//eptd-dminer.properties file has to be located at the same level of executable jar file
			String filePath = ".\\dminer";		
			//reading configuration from properties file
			Configuration config = new Configuration();
			if(config.load(".\\eptd-dminer.properties")){			
				//Initialization
				AtomicInteger failed = new AtomicInteger(0);
				do{					
					try {
						Task task = DataPoster.getTask(config.getDsaverURL(), config.getClient(), failed.get());
						if(task != null){
							Authorization auth = new Authorization(mainLogger);
							//get repos according to assigned task
							SearchQueryGenerator generator = new SearchQueryGenerator("repo");
							if(!task.getParaLanguage().equals(""))
								generator.addSearchTerm("language", task.getParaLanguage());
							if(!task.getParaSize().equals(""))
								generator.addSearchTerm("size", task.getParaSize());
							if(!task.getParaForks().equals(""))
								generator.addSearchTerm("forks", task.getParaForks());
							if(!task.getParaStars().equals(""))
								generator.addSearchTerm("stars", task.getParaStars());
							if(!task.getParaUser().equals(""))
								generator.addSearchTerm("user", task.getParaUser());
							System.out.println("Processing task "+task.getTaskID()+" with search term "+generator.getSearchStr());
							GitHubAPIClient client = new GitHubAPIClient(auth,mainLogger)
									.addParameter("q", generator.getSearchStr())
									.addParameter("sort", "forks");
							JsonElement response = client.get(generator.getQueryBase());
							List<JsonObject> majorRepos = IntStream.rangeClosed(0, response.getAsJsonArray().size()-1)
									.mapToObj(i->response.getAsJsonArray().get(i).getAsJsonObject())
									.collect(Collectors.toList());						
							failed.set((int)majorRepos.stream()
							.filter(mr -> !task.getFinishedRepos().contains(mr.get("id").getAsLong()))//filtering all finished repos
							.map(mr->{
								//for each sub-task
								ProjectLogger logger = new ProjectLogger(mr.get("url").getAsString(),config);
								MajorRepoProcessor processor = new MajorRepoProcessor(logger,task.getTaskID(),auth,filePath);
								if(processor.process()){
									JsonElement dsaverResponse = DataPoster.getInstance().post(config.getDsaverURL(), processor.getRepo(), failed.get());
									if(!dsaverResponse.getAsJsonObject().get("success").getAsBoolean()){
										logger.error("\n\n"+dsaverResponse.toString());
										failed.incrementAndGet();
										return mr;
									}else
										return null;
								}else{
									failed.incrementAndGet();
									return mr;
								}
							})
							.filter(mr -> mr!=null)
							.count());
							if(failed.get() == 0)
								failed.set(-1);
							auth.revokeOAuthToken();
							ProjectCleaner.getInstance().deleteFolder(filePath);
						}else{
							//sleep if received task is null
							System.out.println("No task assigned to client "+config.getClient().getFingerPrint()+", thread gonna sleep for "+SLEEP+" mins.");
							Thread.sleep(1000*60*SLEEP);
						}
					} catch (Exception e) {
						mainLogger.error("Unknown error when processing task", e);
					}
				}while(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}

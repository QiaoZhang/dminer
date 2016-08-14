package com.eptd.dminer.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.eptd.dminer.crawler.GitHubAPIClient;
import com.eptd.dminer.crawler.MajorRepoProcessor;
import com.eptd.dminer.crawler.SearchQueryGenerator;
import com.eptd.dminer.processor.DataPoster;
import com.eptd.dminer.processor.ProjectLogger;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class App {

	public static void main(String[] args) {
		System.out.println("/eptd-dminer.properties");
		//eptd-dminer.properties file has to be located at the same level of executable jar file
		String filePath = ClassLoader.getSystemClassLoader().getResource(".").getPath();		
		//reading configuration from properties file
		Configuration config = new Configuration();
		System.out.println(filePath+ "/eptd-dminer.properties");
		if(config.load(filePath+ "/eptd-dminer.properties")){			
			System.out.println(config.getMaxRatio());
			/*
			//Initialization
			ProjectLogger mainLogger = new ProjectLogger("https://api.github.com/repos/qiaozhang/dminer",config);
			Authorization auth = new Authorization(mainLogger);
			AtomicInteger failed = new AtomicInteger(0);
			try {
				Task task = DataPoster.getTask(config.getDsaverURL(), config.getClient(), failed.get());
				if(task != null){
					//get repos according to assigned task
					SearchQueryGenerator generator = new SearchQueryGenerator("repo");
					if(task.getParaLanguage()!=null)
						generator.addSearchTerm("language", task.getParaLanguage());
					if(task.getParaSize()!=null)
						generator.addSearchTerm("size", task.getParaSize());
					if(task.getParaForks()!=null)
						generator.addSearchTerm("forks", task.getParaForks());
					if(task.getParaStars()!=null)
						generator.addSearchTerm("stars", task.getParaStars());
					if(task.getParaUser()!=null)
						generator.addSearchTerm("user", task.getParaUser());
					GitHubAPIClient client = new GitHubAPIClient(auth,mainLogger)
							.addParameter("q", generator.getSearchStr())
							.addParameter("sort", "forks");
					JsonElement response = client.get(generator.getQueryBase());
					List<JsonObject> majorRepos = IntStream.rangeClosed(0, response.getAsJsonArray().size()-1)
							.mapToObj(i->response.getAsJsonArray().get(i).getAsJsonObject())
							.collect(Collectors.toList());
					majorRepos.parallelStream()
					.map(mr->{
						//for each task
						ProjectLogger logger = new ProjectLogger(mr.get("url").getAsString(),config);
						MajorRepoProcessor processor = new MajorRepoProcessor(logger,task.getTaskID(),auth,filePath);
						return mr;
					});
				}
			} catch (Exception e) {
				
			}*/
		}
	}
}

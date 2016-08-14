package com.eptd.dminer.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.eptd.dminer.core.Configuration;

public class ProjectLogger {
	private StringBuffer buffer;
	private Configuration config;
	private final Logger logger;
	private final String projectName;
	private final String repoURL;
	private String level;
	
	/**
	 * This constructor is to build a root ProjectLogger instance with its Logger instance to be used throughout the entire MajorRepo analysis life cycle.
	 * @param repoURL The GitHub repository URL of MajorRepo
	 */
	public ProjectLogger(String repoURL,Configuration config){
		this.projectName = repoURLParser(repoURL);
		this.repoURL = repoURL;
		//consistent logger
		this.logger = LoggerFactory.getLogger(projectName);
		MDC.put("projectName", projectName);
		//consistent logging message buffer
		this.buffer = new StringBuffer();
		//consistent configuration
		this.config = config;
		//logging indentation level
		this.buffer.append("MajorRepoProcessor\n");
		this.level = "  ";//MajorRepoProcessor - level 0
	}
	
	/**
	 * This constructor is to build a new ProjectLogger instance with its caller's Logger instance but a further level to differentiate the logging data 
	 * @param logger The caller's ProjectLogger instance
	 */
	public ProjectLogger(ProjectLogger logger){
		this.projectName = logger.getProjectName();
		this.repoURL = logger.getRepoURL();
		//consistent logger
		this.logger = logger.getLogger();
		MDC.put("projectName", projectName);
		//consistent logging message buffer
		this.buffer = new StringBuffer().append(logger.getBuffer().toString());
		//consistent configuration
		this.config = logger.getConfig();
		//logging indentation level
		this.level = logger.getLevel()+">>";
	}
	
	public ProjectLogger append(String msg){
		this.buffer.append(level+" "+msg+"\n");
		return this;
	}
	
	public void error(String msg){
		this.logger.error(buffer.toString()+level+">> "+msg+"\n");
	}
	
	public void error(String msg, Throwable e){
		this.logger.error(buffer.toString()+level+">> "+msg+"\n",e);
	}
	
	public Logger getLogger(){
		return this.logger;
	}
	
	public StringBuffer getBuffer(){
		return this.buffer;
	}
	
	public Configuration getConfig(){
		return this.config;
	}
	
	public String getLevel(){
		return this.level;
	}

	public String getProjectName(){
		return this.projectName;
	}
	
	public String getRepoURL() {
		return repoURL;
	}
	
	@Deprecated
	public String printProgBar(int percent){
	    StringBuilder bar = new StringBuilder("[");
	    for(int i = 0; i < 100; i++){
	        if( i < (percent)){
	            bar.append("=");
	        }else if( i == (percent)){
	            bar.append(">");
	        }else{
	            bar.append(" ");
	        }
	    }
	    bar.append("]   " + percent + "%");
	    return bar.toString();
	}
	
	public int progressCalculator(int index,int maxSize){
		return (int)(((double)(index+1)/(double)maxSize)*100.0);
	}
	
	public static String repoURLParser(String repoURL){
		return repoURL.substring(29, repoURL.length()).replace('/', '.');
	}
}

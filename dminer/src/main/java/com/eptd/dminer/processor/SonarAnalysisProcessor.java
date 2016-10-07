package com.eptd.dminer.processor;

import java.util.ArrayList;

import com.eptd.dminer.core.SonarMetrics;

public class SonarAnalysisProcessor {
	private ProjectLogger logger;	
	private String repositoryHTML;
	private String filePath;
	private long projectID;
	private String projectName;
	private String login;
	private String userType;
	private String language;
	private String version;
	
	private String projectKey;
	
	private final String[] sonarMetrics = {
			"bugs","vulnerabilities","code_smells","sqale_index","sqale_debt_ratio",
			"duplicated_lines_density","duplicated_blocks","duplicated_lines","duplicated_files",
			"ncloc","lines","statements","functions","classes","files","directories","complexity",
			"file_complexity","function_complexity","class_complexity","comment_lines_density",
			"comment_lines","public_api","public_documented_api_density","public_undocumented_api"};
	
	/**
	 * Construct a repository processor for SonarQube analysis with specified release version
	 * @param repositoryHTML The GitHub HTML URL of repository to be processed 
	 * @param filePath The directory of root of cloned local repository
	 * @param projectID The ID of repository 
	 * @param projectName The name of repository
	 * @param login The name of repository owner
	 * @param userType The type of repository owner either user or organization
	 * @param language The primary programming language
	 * @param version The name of latest release tag
	 */
	public SonarAnalysisProcessor(
			String repositoryHTML,String filePath,long projectID, String projectName,String login,
			String userType,String language,String version,ProjectLogger logger){
		this.repositoryHTML = repositoryHTML;
		this.filePath = filePath;
		this.projectID = projectID;
		this.projectName = projectName;
		this.login = login;
		this.userType = userType;
		this.language = language;
		this.version = version;
		this.logger = new ProjectLogger(logger).append("SonarAnalysisProcessor");
		this.setProjectKey(SonarPropertiesWriter.getProjectKey(projectID,projectName,login,userType));
	}
	
	/**
	 * @return True if the GitHub repository has been processed successfully including three subtasks: 
	 * (1) clone remote repository to local drive, (2) create properties file for sonar project, and 
	 * (3) run SonarQube analysis; false if one of three subtasks failed.
	 */
	public ArrayList<SonarMetrics> process(){
		try {
			CMDProcessor cmd = new CMDProcessor();
			//step 1: clone repository
			cmd.addCommand("git clone "+ repositoryHTML + " " + filePath);
			if(!cmd.execute())
				throw new Exception("Project "+projectName+" fails to be cloned to local drive");
			//step 2: write properties file			
			if(!SonarPropertiesWriter.getInstance().write(logger,projectID,projectName,login,userType,language,version,filePath))
				throw new Exception("Project properties file of "+projectName+" fails to be created");
			//step 3: run SonarQube analysis
			if(!SonarRunnerProcessor.getInstance().execute(filePath,cmd))
				throw new Exception("Project "+projectName+" fails to be analyzed by SonarQube");
			return SonarResultExtractor.getInstance().extract(logger, projectKey, sonarMetrics);
		} catch (Exception e) {
			logger.error("Sonar-runner project "+projectName,e);
			return null;
		}
	}

	public String getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}
}

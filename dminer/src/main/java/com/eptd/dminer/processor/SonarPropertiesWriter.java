package com.eptd.dminer.processor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.text.WordUtils;

public class SonarPropertiesWriter {
	private static final SonarPropertiesWriter instance = new SonarPropertiesWriter();
	
	//comments
	private static final String COMMENT_METADATA = "# Required metadata";
	private static final String COMMENT_SOURCES = "# Comma-separated paths to directories with sources (required)";
	private static final String COMMENT_LANGUAGE = "# Language";
	private static final String COMMENT_ENCODING = "# Encoding of the source files";
	
	//sonar project properties
	private static final String SONAR_PROJECT_KEY = "sonar.projectKey=";
	private static final String SONAR_PROJECT_NAME = "sonar.projectName=";
	private static final String SONAR_PROJECT_VERSION = "sonar.projectVersion=";
	private static final String SONAR_SOURCES = "sonar.sources=";
	private static final String SONAR_LANGUAGE = "sonar.language=";
	private static final String SONAR_ENCODING = "sonar.sourceEncoding=";
	
	//Written file name
	private static final String FILENAME = "sonar-project.properties";
	
	/**
	 * Write the sonar-project.properties file into filePath for further reference of SonarQube analysis
	 * @throws IOException If errors occur during file writing process
	 * @throws FileNotFoundException If no file exists on the abstract path
	 * @throws UnsupportedEncodingException If file encoding is not supported
	 * @return True if sonar-project.properties file is created successfully; false if a duplicate creation is requested.
	 */
	public synchronized boolean write(ProjectLogger logger, long id, String name,String login, String userType, String language, String version, String folder){
		ProjectLogger newLogger = new ProjectLogger(logger).append("SonarPropertiesWriter");
		//values of sonar project  properties
		String projectKey = SonarPropertiesWriter.getProjectKey(id,name,login,userType);
		String projectName = WordUtils.capitalize(language + " :: " + name + " :: " + id);
		String projectVersion = version;
		String sources = ".";
		String filePath = folder + "\\" + FILENAME;
		String sourceEncoding = "utf-8";
		boolean error = false;
		int count = 0;
		do {			
			try {
				if(error == true)
					Thread.sleep(2000);
				File file = new File(filePath);
				if(file.exists()){
					return false;
				}				
				//create writer
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), sourceEncoding));
				//write properties
				writer.write(COMMENT_METADATA);
				writer.write("\n");
				writer.write(SONAR_PROJECT_KEY + projectKey);
				writer.write("\n");
				writer.write(SONAR_PROJECT_NAME + projectName);
				writer.write("\n");
				writer.write(SONAR_PROJECT_VERSION + projectVersion);
				writer.write("\n");
				writer.write("\n");
				writer.write(COMMENT_SOURCES);
				writer.write("\n");
				writer.write(SONAR_SOURCES + sources);
				writer.write("\n");
				writer.write("\n");
				writer.write(COMMENT_LANGUAGE);
				writer.write("\n");
				writer.write(SONAR_LANGUAGE + language);
				writer.write("\n");
				writer.write("\n");
				writer.write(COMMENT_ENCODING);
				writer.write("\n");
				writer.write(SONAR_ENCODING + sourceEncoding);			
				writer.flush();
				writer.close();
				return true;
			} catch (Exception e) {				
				newLogger.error("Writing to file path "+filePath, e);		
				error = true;
				count++;
			}
		}while (error&&count<logger.getConfig().getWriteRepeat());//WRITERREPEAT
		return false;
	}

	public static SonarPropertiesWriter getInstance() {
		return instance;
	}
	
	public static String getProjectKey(long id,String name,String login, String userType) {
		return login + ":" + name + ":" + id;
	}
}

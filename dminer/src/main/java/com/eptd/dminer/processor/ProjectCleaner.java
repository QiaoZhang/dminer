package com.eptd.dminer.processor;

import java.io.IOException;

public class ProjectCleaner {
	private static final ProjectCleaner instance = new ProjectCleaner();
	
	public synchronized boolean deleteSonar(String projectKey){
		try {
			if(new CMDProcessor().addCommand("curl -X POST -u admin:admin \"http://localhost:9000/api/projects/delete?key="+projectKey+"\"").execute()==0)
				return true;
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public synchronized boolean deleteFolder(String path){
		try {
			if(new CMDProcessor().addCommand("c:").addCommand("rd "+path+" /s /q").execute()==0)
				return true;
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static ProjectCleaner getInstance() {
		return instance;
	}

}	

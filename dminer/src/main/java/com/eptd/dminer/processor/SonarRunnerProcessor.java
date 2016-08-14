package com.eptd.dminer.processor;

import java.io.IOException;

public class SonarRunnerProcessor {
	private static final SonarRunnerProcessor instance = new SonarRunnerProcessor();
	
	public synchronized boolean execute(String filePath) throws InterruptedException, IOException{
		CMDProcessor cmd = new CMDProcessor();
		cmd.addCommand(filePath.substring(0, 2));
		cmd.addCommand("cd " + filePath);
	    cmd.addCommand("sonar-runner");
	    if(cmd.execute() != 0)
	    	return false;
		return true;
	}

	public static SonarRunnerProcessor getInstance() {
		return instance;
	}
}

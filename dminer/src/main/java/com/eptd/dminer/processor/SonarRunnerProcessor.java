package com.eptd.dminer.processor;

import java.io.IOException;

public class SonarRunnerProcessor {
	private static final SonarRunnerProcessor instance = new SonarRunnerProcessor();
	
	public synchronized boolean execute(String filePath,CMDProcessor cmd) throws InterruptedException, IOException{
		cmd.addCommand(filePath.substring(0, 2));
		cmd.addCommand("cd " + filePath);
	    cmd.addCommand("sonar-scanner");
	    if(!cmd.execute())
	    	return false;
		return true;
	}

	public static SonarRunnerProcessor getInstance() {
		return instance;
	}
}

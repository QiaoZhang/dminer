package com.eptd.dminer;

import java.util.concurrent.atomic.AtomicInteger;

import com.eptd.dminer.core.Authorization;
import com.eptd.dminer.core.Configuration;
import com.eptd.dminer.core.Task;
import com.eptd.dminer.processor.DataPoster;
import com.eptd.dminer.processor.ProjectLogger;

public class ClientTaskTester {

	public static void main(String[] args) {
		Configuration config = Configuration.getDefaultConfig();
		ProjectLogger mainLogger = new ProjectLogger("https://api.github.com/repos/qiaozhang/dminer",config);
		Authorization auth = new Authorization(mainLogger);
		AtomicInteger failed = new AtomicInteger(0);
		Task task = DataPoster.getTask(config.getDsaverURL(), config.getClient(), failed.get());
	}

}

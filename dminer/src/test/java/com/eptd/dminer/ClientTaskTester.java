package com.eptd.dminer;

import java.util.concurrent.atomic.AtomicInteger;

import com.eptd.dminer.core.Authorization;
import com.eptd.dminer.core.Configuration;
import com.eptd.dminer.core.Task;
import com.eptd.dminer.processor.DataPoster;
import com.eptd.dminer.processor.ProjectLogger;
import com.eptd.dminer.processor.SonarPropertiesWriter;
import com.eptd.dminer.processor.SonarResultExtractor;

public class ClientTaskTester {

	public static void main(String[] args) {
		/*
		Configuration config = Configuration.getDefaultConfig();
		ProjectLogger mainLogger = new ProjectLogger("https://api.github.com/repos/qiaozhang/dminer",config);
		Authorization auth = new Authorization(mainLogger);
		AtomicInteger failed = new AtomicInteger(0);
		Task task = DataPoster.getTask(config.getDsaverURL(), config.getClient(), failed.get());
		SonarPropertiesWriter.getInstance().write(
				new ProjectLogger("https://api.github.com/qiaozhang/dminer",Configuration.getDefaultConfig()), 
				9623064, 
				"webmagic",
				"code4craft",
				"user", 
				"java", 
				"webmaigc-0.4.3", 
				"E:\\DMiner\\dminer\\webmagic");*/
		String[] sonarMetrics = {"ncloc","sqale_index","sqale_debt_ratio"};
		SonarResultExtractor.getInstance().extract(new ProjectLogger("https://api.github.com/qiaozhang/dminer",Configuration.getDefaultConfig()), 
				"code4craft:webmagic:9623064",sonarMetrics );
	}

}

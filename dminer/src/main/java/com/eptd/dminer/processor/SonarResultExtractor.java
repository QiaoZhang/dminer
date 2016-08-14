package com.eptd.dminer.processor;

import java.util.ArrayList;

import org.sonar.wsclient.Host;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.connectors.HttpClient4Connector;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceQuery;

import com.eptd.dminer.core.SonarMetrics;

public class SonarResultExtractor {
	private static final SonarResultExtractor instance = new SonarResultExtractor();
	
	/**
	 * Process SonarQube analysis result extraction
	 * @return A list of SonarMetrics classes that contains all metrics value information
	 */
	public synchronized ArrayList<SonarMetrics> extract(ProjectLogger logger,String projectKey, String[] metricsKeys){
		ProjectLogger instanceLogger = new ProjectLogger(logger).append("SonarResultExtractor");
		try {
			Sonar sonar = new Sonar(new HttpClient4Connector(new Host(logger.getConfig().getSonarURL(), logger.getConfig().getSonarUsername(), logger.getConfig().getSonarPassword())));
			Resource struts = sonar.find(ResourceQuery.createForMetrics(projectKey, metricsKeys));
			ArrayList<SonarMetrics> metricsValues = new ArrayList<SonarMetrics>();
			if(struts!=null){
				for(int i=0;i<metricsKeys.length;i++)
					metricsValues.add(new SonarMetrics(
							metricsKeys[i],
							(Double)struts.getMeasure(metricsKeys[i]).getValue(),
							struts.getMeasure(metricsKeys[i]).getFormattedValue()));	
				return metricsValues;
			}else{
				instanceLogger.error("Captured SonarQube analysis result of project "+projectKey+" is not valid.");
				return null;
			}			
		}catch (Exception e){
			instanceLogger.error("Unknown Exception when extracting sonar metrics of "+projectKey, e);
			return null;
		}
	}

	public static SonarResultExtractor getInstance() {
		return instance;
	}
}

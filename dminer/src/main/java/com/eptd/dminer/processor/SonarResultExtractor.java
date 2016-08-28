package com.eptd.dminer.processor;

import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.eptd.dminer.core.SonarMetrics;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SonarResultExtractor {
	private static final SonarResultExtractor instance = new SonarResultExtractor();
	private String url = "SONAR/api/measures/component?componentKey=PROJECTKEY&metricKeys=METRICKEYS";
	
	/**
	 * Process SonarQube analysis result extraction
	 * @return A list of SonarMetrics classes that contains all metrics value information
	 */
	public synchronized ArrayList<SonarMetrics> extract(ProjectLogger logger,String projectKey, String[] metricsKeys){
		ProjectLogger instanceLogger = new ProjectLogger(logger).append("SonarResultExtractor");
		try {	
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url
					.replace("SONAR", logger.getConfig().getSonarURL())
					.replace("PROJECTKEY", projectKey)
					.replace("METRICKEYS", formatKeys(metricsKeys)));
			int repeat = 0;
			do{
				JsonObject response = new JsonParser().parse(IOUtils.toString(httpClient.execute(request).getEntity().getContent(),"UTF-8")).getAsJsonObject();
				if(response.get("component").getAsJsonObject().get("measures").getAsJsonArray().size()>0)
					return extractMetrics(response.get("component").getAsJsonObject().get("measures").getAsJsonArray());
				else
					Thread.sleep(1000);
				repeat++;
			}while(repeat<1000);
			return null;
		}catch (Exception e){
			instanceLogger.error("Unknown Exception when extracting sonar metrics of "+projectKey, e);
			return null;
		}
	}
	
	private String formatKeys(String[] metricsKeys){
		String formatKeys = metricsKeys[0];
		for(int i=1;i<metricsKeys.length;i++)
			formatKeys += "," + metricsKeys[i];
		return formatKeys;
	}
	
	private ArrayList<SonarMetrics> extractMetrics(JsonArray json){
		ArrayList<SonarMetrics> metricsValues = new ArrayList<SonarMetrics>();
		for(int i=0;i<json.size();i++){
			JsonObject obj = json.get(i).getAsJsonObject();
			metricsValues.add(new SonarMetrics(obj.get("metric").getAsString(),obj.get("value").getAsDouble()));
		}
		return metricsValues;
	}

	public static SonarResultExtractor getInstance() {
		return instance;
	}
}

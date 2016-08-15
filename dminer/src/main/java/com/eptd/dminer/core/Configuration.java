package com.eptd.dminer.core;

import java.io.FileInputStream;
import java.util.Optional;
import java.util.Properties;

public class Configuration {
	private static final Configuration config = new Configuration()
			.setClient(new Client()
					.setFingerPrint("junit-tester")
					.setUsername("qiaozhang")
					.setPassword("loveon1225")
					.setAppClientID("9f71637a480e74cefcb6")
					.setAppClientSecret("12d5cf32314dc8ca76eb13d3f315cfd760988f96"))
				.setDsaverURL("http://qiaozhang.me:7070/dsaver")
				.setSonarURL("http://localhost:9000")
				.setSonarUsername("admin")
				.setSonarPassword("admin")
				.setMajorLanguage("java")
				.setMaxRatio(0.05)
				.setTotalRatio(0.01)
				.setNoobRatio(0.8)
				.setUpperBound(50)
				.setLowerBound(2)
				.setMaxRepos(30)
				.setRequestRepeat(5)
				.setRequestSleep(5)
				.setWriteRepeat(5);
	//connection configuration
	private Client client;
	private String dsaverURL;
	private String sonarURL;
	private String sonarUsername;
	private String sonarPassword;
	//contributor filtering configuration
	private String majorLanguage;
	private double maxRatio;
	private double totalRatio;
	private double noobRatio;
	private int upperBound;
	private int lowerBound;
	private int maxRepos;
	//repeat configuration
	private int requestRepeat;
	private int requestSleep;
	private int writeRepeat;
	
	public Configuration() {
		
	}

	public boolean load(String filePath){
		FileInputStream file;
		Properties prpt;
		try {
			//load .properties file
			prpt = new Properties();
			file = new FileInputStream(filePath);
			prpt.load(file);
			file.close();
			//connection
			setClient(new Client()
					.setFingerPrint(Optional.ofNullable(prpt.getProperty("dminer.fingerPrint")).orElse("qiao-default"))
					.setUsername(Optional.ofNullable(prpt.getProperty("dminer.github.username")).orElse("qiaozhang"))
					.setPassword(Optional.ofNullable(prpt.getProperty("dminer.github.password")).orElse("loveon1225"))
					.setAppClientID(Optional.ofNullable(prpt.getProperty("dminer.github.clientID")).orElse("9f71637a480e74cefcb6"))
					.setAppClientSecret(Optional.ofNullable(prpt.getProperty("dminer.github.clientSecret")).orElse("12d5cf32314dc8ca76eb13d3f315cfd760988f96")));
			setDsaverURL(Optional.ofNullable(prpt.getProperty("dminer.dsaver.url")).orElse("http://qiaozhang.me:7070/dsaver"));
			setSonarURL(Optional.ofNullable(prpt.getProperty("dminer.sonar.url")).orElse("http://localhost:9000"));
			setSonarUsername(Optional.ofNullable(prpt.getProperty("dminer.sonar.username")).orElse("admin"));
			setSonarPassword(Optional.ofNullable(prpt.getProperty("dminer.sonar.password")).orElse("admin"));
			//contributor filtering
			setMajorLanguage(Optional.ofNullable(prpt.getProperty("dminer.majorLanguage")).orElse("java"));
			setMaxRatio(Optional.ofNullable(Double.parseDouble(prpt.getProperty("dminer.filter.maxRatio"))).orElse(0.05));
			setTotalRatio(Optional.ofNullable(Double.parseDouble(prpt.getProperty("dminer.filter.totalRatio"))).orElse(0.01));
			setNoobRatio(Optional.ofNullable(Double.parseDouble(prpt.getProperty("dminer.filter.noobRatio"))).orElse(0.5));
			setUpperBound(Optional.ofNullable(Integer.parseInt(prpt.getProperty("dminer.filter.upperBound"))).orElse(50));
			setLowerBound(Optional.ofNullable(Integer.parseInt(prpt.getProperty("dminer.filter.lowerBound"))).orElse(2));
			setMaxRepos(Optional.ofNullable(Integer.parseInt(prpt.getProperty("dminer.filter.maxRepos"))).orElse(30));
			//repeat
			setRequestRepeat(Optional.ofNullable(Integer.parseInt(prpt.getProperty("dminer.repeat.requestRepeat"))).orElse(5));
			setRequestSleep(Optional.ofNullable(Integer.parseInt(prpt.getProperty("dminer.repeat.requestSleep"))).orElse(5));
			setWriteRepeat(Optional.ofNullable(Integer.parseInt(prpt.getProperty("dminer.repeat.writeRepeat"))).orElse(5));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Client getClient() {
		return client;
	}

	public Configuration setClient(Client client) {
		this.client = client;
		return this;
	}

	public String getDsaverURL() {
		return dsaverURL;
	}

	public Configuration setDsaverURL(String dsaverURL) {
		this.dsaverURL = dsaverURL;
		return this;
	}

	public String getSonarURL() {
		return sonarURL;
	}

	public Configuration setSonarURL(String sonarURL) {
		this.sonarURL = sonarURL;
		return this;
	}

	public String getSonarUsername() {
		return sonarUsername;
	}

	public Configuration setSonarUsername(String sonarUsername) {
		this.sonarUsername = sonarUsername;
		return this;
	}

	public String getSonarPassword() {
		return sonarPassword;
	}

	public Configuration setSonarPassword(String sonarPasssword) {
		this.sonarPassword = sonarPasssword;
		return this;
	}

	public String getMajorLanguage() {
		return majorLanguage;
	}

	public Configuration setMajorLanguage(String majorLanguage) {
		this.majorLanguage = majorLanguage;
		return this;
	}

	public double getMaxRatio() {
		return maxRatio;
	}

	public Configuration setMaxRatio(double maxRatio) {
		this.maxRatio = maxRatio;
		return this;
	}

	public double getTotalRatio() {
		return totalRatio;
	}

	public Configuration setTotalRatio(double totalRatio) {
		this.totalRatio = totalRatio;
		return this;
	}

	public double getNoobRatio() {
		return noobRatio;
	}

	public Configuration setNoobRatio(double noobRatio) {
		this.noobRatio = noobRatio;
		return this;
	}

	public int getUpperBound() {
		return upperBound;
	}

	public Configuration setUpperBound(int upperBound) {
		this.upperBound = upperBound;
		return this;
	}

	public int getLowerBound() {
		return lowerBound;
	}

	public Configuration setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
		return this;
	}

	public int getMaxRepos() {
		return maxRepos;
	}

	public Configuration setMaxRepos(int maxRepos) {
		this.maxRepos = maxRepos;
		return this;
	}

	public int getRequestRepeat() {
		return requestRepeat;
	}

	public Configuration setRequestRepeat(int requestRepeat) {
		this.requestRepeat = requestRepeat;
		return this;
	}

	public int getRequestSleep() {
		return requestSleep;
	}

	public Configuration setRequestSleep(int requestSleep) {
		this.requestSleep = requestSleep;
		return this;
	}

	public int getWriteRepeat() {
		return writeRepeat;
	}

	public Configuration setWriteRepeat(int writeRepeat) {
		this.writeRepeat = writeRepeat;
		return this;
	}

	public static Configuration getDefaultConfig() {
		return config;
	}
}

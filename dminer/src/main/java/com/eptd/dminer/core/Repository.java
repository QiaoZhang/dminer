package com.eptd.dminer.core;

import java.util.ArrayList;
import java.util.Optional;

import org.joda.time.DateTime;

public class Repository {
	//basic repository information
	protected String repositoryURL;
	protected String repositoryHTML;
	protected String filePath;
	protected long projectID;
	protected String projectName;
	protected String ownerLogin;
	protected String userType;
	protected String language;
	protected String version;
	protected long size;
	protected long stargazersCount;
	protected long subscribersCount;
	protected long forksCount;
	protected DateTime createdAt;
	protected long issuesCount;
	protected long handledIssuesCount;
	protected double avgIssueHandledDays;
	protected ArrayList<SonarMetrics> sonarMetrics;
	
	public Repository(){
		this.setRepositoryURL(null);
		this.setRepositoryHTML(null);
		this.setFilePath(null);
		this.setProjectID(0);
		this.setProjectName(null);
		this.setOwnerLogin(null);
		this.setUserType(null);
		this.setLanguage(null);
		this.setVersion(null);
		this.setSize(0);
		this.setStargazersCount(0);
		this.setSubscribersCount(0);
		this.setForksCount(0);
		this.setCreatedAt(null);
		this.setIssuesCount(0);
		this.setHandledIssuesCount(0);
		this.setAvgIssueHandledDays(0.0);
		this.setSonarMetrics(null);
	}

	public String getRepositoryURL() {
		return Optional.ofNullable(repositoryURL).orElse("Unknown");
	}

	public void setRepositoryURL(String repositoryURL) {
		this.repositoryURL = Optional.ofNullable(repositoryURL).orElse("Unknown");
	}

	public String getRepositoryHTML() {
		return Optional.ofNullable(repositoryHTML).orElse("Unknown");
	}

	public void setRepositoryHTML(String repositoryHTML) {
		this.repositoryHTML = Optional.ofNullable(repositoryHTML).orElse("Unknown");
	}
	
	public String getFilePath() {
		return Optional.ofNullable(filePath).orElse("Unknown");
	}

	public void setFilePath(String filePath) {
		this.filePath = Optional.ofNullable(filePath).orElse("Unknown");
	}

	public long getProjectID() {
		return Optional.ofNullable(projectID).orElse((long) 0);
	}

	public void setProjectID(long projectID) {
		this.projectID = Optional.ofNullable(projectID).orElse((long) 0);
	}

	public String getProjectName() {
		return Optional.ofNullable(projectName).orElse("Unknown");
	}

	public void setProjectName(String projectName) {
		this.projectName = Optional.ofNullable(projectName).orElse("Unknown");
	}

	public String getOwnerLogin() {
		return Optional.ofNullable(ownerLogin).orElse("Unknown");
	}

	public void setOwnerLogin(String ownerLogin) {
		this.ownerLogin = Optional.ofNullable(ownerLogin).orElse("Unknown");
	}

	public String getUserType() {
		return Optional.ofNullable(userType).orElse("Unknown");
	}

	public void setUserType(String userType) {
		this.userType = Optional.ofNullable(userType).orElse("Unknown");
	}

	public String getLanguage() {
		return Optional.ofNullable(language).orElse("Unknown");
	}

	public void setLanguage(String language) {
		this.language = Optional.ofNullable(language).orElse("Unknown");
	}

	public String getVersion() {
		return Optional.ofNullable(version).orElse("1.0.0");
	}

	public void setVersion(String version) {
		this.version = Optional.ofNullable(version).orElse("1.0.0");
	}
	
	public SonarMetrics getSonarMetrics(String key){
		for(int i=0;i<sonarMetrics.size();i++){
			if(sonarMetrics.get(i).getKey().equals(key))
				return sonarMetrics.get(i);
		}
		return null;
	}

	public ArrayList<SonarMetrics> getSonarMetrics() {
		return Optional.ofNullable(sonarMetrics).orElse(new ArrayList<SonarMetrics>());
	}

	public void setSonarMetrics(ArrayList<SonarMetrics> sonarMetrics) {
		this.sonarMetrics = Optional.ofNullable(sonarMetrics).orElse(new ArrayList<SonarMetrics>());
	}

	public long getSize() {
		return Optional.ofNullable(size).orElse((long) 0);
	}

	public void setSize(long size) {
		this.size = Optional.ofNullable(size).orElse((long) 0);
	}

	public long getStargazersCount() {
		return Optional.ofNullable(stargazersCount).orElse((long) 0);
	}

	public void setStargazersCount(long stargazersCount) {
		this.stargazersCount = Optional.ofNullable(stargazersCount).orElse((long) 0);
	}

	public long getSubscribersCount() {
		return Optional.ofNullable(subscribersCount).orElse((long) 0);
	}

	public void setSubscribersCount(long subscribersCount) {
		this.subscribersCount = Optional.ofNullable(subscribersCount).orElse((long) 0);
	}

	public long getForksCount() {
		return Optional.ofNullable(forksCount).orElse((long) 0);
	}

	public void setForksCount(long forksCount) {
		this.forksCount = Optional.ofNullable(forksCount).orElse((long) 0);
	}

	public DateTime getCreatedAt() {
		return Optional.ofNullable(createdAt).orElse(new DateTime());
	}

	public void setCreatedAt(DateTime createdAt) {
		this.createdAt = Optional.ofNullable(createdAt).orElse(new DateTime());
	}

	public long getIssuesCount() {
		return Optional.ofNullable(issuesCount).orElse((long) 0);
	}

	public void setIssuesCount(long issuesCount) {
		this.issuesCount = Optional.ofNullable(issuesCount).orElse((long) 0);
	}

	public long getHandledIssuesCount() {
		return Optional.ofNullable(handledIssuesCount).orElse((long) 0);
	}

	public void setHandledIssuesCount(long handledIssuesCount) {
		this.handledIssuesCount = Optional.ofNullable(handledIssuesCount).orElse((long) 0);
	}

	public double getAvgIssueHandledDays() {
		return Optional.ofNullable(avgIssueHandledDays).orElse(0.0);
	}

	public void setAvgIssueHandledDays(double avgIssueHandledDays) {
		this.avgIssueHandledDays = Optional.ofNullable(avgIssueHandledDays).orElse(0.0);
	}
}

package com.eptd.dminer.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class User {
	private String userURL;
	private String userHTML;
	private long userId;						//also id for data crawler
	private String login;						//backup id
	private String name;						//better id for reviewing	
	private int numOfPublicRepos;				//count of public repos
	private int numOfAssignees;					//count of being assignee
	private long followers;
	private String folderPath;
	//attributes from owned repositories
	private ArrayList<Repository> ownRepos;						
	private int numOfAnalyzedRepos;
	private double avgSize;
	private double avgStargazersCount;
	private double avgSubscribersCount;
	private double avgForksCount;
	private double avgIssuesCount;
	private double avgHandledIssuesRatio;
	private double avgIssueHandledDays;
	private double avgMajorLanguageLOC;
	private double avgDebtRatio;
	private double avgSqaleIndex;
	//attributes from investigated pull request
	private int numOfPullRequest;
	private int numOfAcceptedPR;
	private int numOfContributedRepos;	
	private double avgCommits;
	private double avgAdditions;
	private double avgDeletions;
	private double avgChangedFiles;
	private double avgDaysIntervalOfPR;
	
	public User(String userURL){
		this.setUserURL(userURL);
		this.setUserHTML(null);
		this.setUserId(0);
		this.setLogin(null);
		this.setName(null);
		this.setNumOfPublicRepos(0);
		this.setNumOfAssignees(0);
		this.setFollowers(0);
		this.setFolderPath(null);
		//analyzed repos data
		this.setOwnRepos(null);
		this.setNumOfAnalyzedRepos(0);
		this.setAvgSize(0.0);
		this.setAvgStargazersCount(0.0);
		this.setAvgSubscribersCount(0.0);
		this.setAvgForksCount(0.0);
		this.setAvgIssuesCount(0.0);
		this.setAvgHandledIssuesRatio(0.0);
		this.setAvgIssueHandledDays(0.0);
		this.setAvgMajorLanguageLOC(0.0);
		this.setAvgDebtRatio(0.0);
		this.setAvgSqaleIndex(0.0);
		//pull requests
		this.setNumOfPullRequest(0);
		this.setNumOfAcceptedPR(0);
		this.setNumOfContributedRepos(0);
		this.setAvgCommits(0.0);
		this.setAvgAdditions(0.0);
		this.setAvgDeletions(0.0);
		this.setAvgChangedFiles(0.0);
		this.setAvgDaysIntervalOfPR(0.0);
	}

	public String getUserURL() {
		return Optional.ofNullable(userURL).orElse("Unknown");
	}

	public void setUserURL(String userURL) {
		this.userURL = Optional.ofNullable(userURL).orElse("Unknown");
	}

	public String getUserHTML() {
		return Optional.ofNullable(userHTML).orElse("Unknown");
	}

	public void setUserHTML(String userHTML) {
		this.userHTML = Optional.ofNullable(userHTML).orElse("Unknown");
	}

	public String getFolderPath() {
		return Optional.ofNullable(folderPath).orElse("Unknown");
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = Optional.ofNullable(folderPath).orElse("Unknown");
	}

	public long getUserId() {
		return Optional.ofNullable(userId).orElse((long) 0);
	}

	public void setUserId(long userId) {
		this.userId = Optional.ofNullable(userId).orElse((long) 0);
	}

	public String getLogin() {
		return Optional.ofNullable(login).orElse("Unknown");
	}

	public void setLogin(String login) {
		this.login = Optional.ofNullable(login).orElse("Unknown");
	}

	public String getName() {
		return Optional.ofNullable(name).orElse("Unknown");
	}

	public void setName(String name) {
		this.name = Optional.ofNullable(name).orElse("Unknown");
	}

	public int getNumOfAssignees() {
		return Optional.ofNullable(numOfAssignees).orElse(0);
	}

	public void setNumOfAssignees(int numOfAssignees) {
		this.numOfAssignees = Optional.ofNullable(numOfAssignees).orElse(0);
	}

	public int getNumOfPublicRepos() {
		return Optional.ofNullable(numOfPublicRepos).orElse(0);
	}

	public void setNumOfPublicRepos(int numOfPublicRepos) {
		this.numOfPublicRepos = Optional.ofNullable(numOfPublicRepos).orElse(0);
	}

	public long getFollowers() {
		return Optional.ofNullable(followers).orElse((long) 0);
	}

	public void setFollowers(long followers) {
		this.followers = Optional.ofNullable(followers).orElse((long) 0);
	}

	public ArrayList<Repository> getOwnRepos() {
		return Optional.ofNullable(ownRepos).orElse(new ArrayList<Repository>());
	}

	public void setOwnRepos(ArrayList<Repository> ownRepos) {
		this.ownRepos = Optional.ofNullable(ownRepos).orElse(new ArrayList<Repository>());
	}
	
	public int getNumOfAnalyzedRepos(){
		return Optional.ofNullable(numOfAnalyzedRepos).orElse(0);
	}
	
	public void setNumOfAnalyzedRepos(int numOfAnalyzedRepos){
		this.numOfAnalyzedRepos = Optional.ofNullable(numOfAnalyzedRepos).orElse(0);
	}

	public double getAvgSize() {
		return Optional.ofNullable(avgSize).orElse(0.0);
	}

	public void setAvgSize(double avgSize) {
		this.avgSize = Optional.ofNullable(avgSize).orElse(0.0);
	}

	public double getAvgStargazersCount() {
		return Optional.ofNullable(avgStargazersCount).orElse(0.0);
	}

	public void setAvgStargazersCount(double avgStargazersCount) {
		this.avgStargazersCount = Optional.ofNullable(avgStargazersCount).orElse(0.0);
	}

	public double getAvgSubscribersCount() {
		return Optional.ofNullable(avgSubscribersCount).orElse(0.0);
	}

	public void setAvgSubscribersCount(double avgSubscribersCount) {
		this.avgSubscribersCount = Optional.ofNullable(avgSubscribersCount).orElse(0.0);
	}

	public double getAvgForksCount() {
		return Optional.ofNullable(avgForksCount).orElse(0.0);
	}

	public void setAvgForksCount(double avgForksCount) {
		this.avgForksCount = Optional.ofNullable(avgForksCount).orElse(0.0);
	}

	public double getAvgIssuesCount() {
		return Optional.ofNullable(avgIssuesCount).orElse(0.0);
	}

	public void setAvgIssuesCount(double avgIssuesCount) {
		this.avgIssuesCount = Optional.ofNullable(avgIssuesCount).orElse(0.0);
	}

	public double getAvgHandledIssuesRatio() {
		return Optional.ofNullable(avgHandledIssuesRatio).orElse(0.0);
	}

	public void setAvgHandledIssuesRatio(double avgHandledIssuesRatio) {
		this.avgHandledIssuesRatio = Optional.ofNullable(avgHandledIssuesRatio).orElse(0.0);
	}

	public double getAvgIssueHandledDays() {
		return Optional.ofNullable(avgIssueHandledDays).orElse(0.0);
	}

	public void setAvgIssueHandledDays(double avgIssueHandledDays) {
		this.avgIssueHandledDays = Optional.ofNullable(avgIssueHandledDays).orElse(0.0);
	}

	public double getAvgMajorLanguageLOC() {
		return Optional.ofNullable(avgMajorLanguageLOC).orElse(0.0);
	}

	public void setAvgMajorLanguageLOC(double avgMajorLanguageLOC) {
		this.avgMajorLanguageLOC = Optional.ofNullable(avgMajorLanguageLOC).orElse(0.0);
	}

	public double getAvgDebtRatio() {
		return Optional.ofNullable(avgDebtRatio).orElse(0.0);
	}

	public void setAvgDebtRatio(double avgDebtRatio) {
		this.avgDebtRatio = Optional.ofNullable(avgDebtRatio).orElse(0.0);
	}

	public double getAvgSqaleIndex() {
		return Optional.ofNullable(avgSqaleIndex).orElse(0.0);
	}

	public void setAvgSqaleIndex(double avgSqaleIndex) {
		this.avgSqaleIndex = Optional.ofNullable(avgSqaleIndex).orElse(0.0);
	}

	public int getNumOfPullRequest() {
		return Optional.ofNullable(numOfPullRequest).orElse(0);
	}

	public void setNumOfPullRequest(int numOfPullRequest) {
		this.numOfPullRequest = Optional.ofNullable(numOfPullRequest).orElse(0);
	}

	public int getNumOfAcceptedPR() {
		return Optional.ofNullable(numOfAcceptedPR).orElse(0);
	}

	public void setNumOfAcceptedPR(int numOfAcceptedPR) {
		this.numOfAcceptedPR = Optional.ofNullable(numOfAcceptedPR).orElse(0);
	}

	public int getNumOfContributedRepos() {
		return Optional.ofNullable(numOfContributedRepos).orElse(0);
	}

	public void setNumOfContributedRepos(int numOfContributedRepos) {
		this.numOfContributedRepos = Optional.ofNullable(numOfContributedRepos).orElse(0);
	}

	public double getAvgCommits() {
		return Optional.ofNullable(avgCommits).orElse(0.0);
	}

	public void setAvgCommits(double avgCommits) {
		this.avgCommits = Optional.ofNullable(avgCommits).orElse(0.0);
	}

	public double getAvgAdditions() {
		return Optional.ofNullable(avgAdditions).orElse(0.0);
	}

	public void setAvgAdditions(double avgAdditions) {
		this.avgAdditions = Optional.ofNullable(avgAdditions).orElse(0.0);
	}

	public double getAvgDeletions() {
		return Optional.ofNullable(avgDeletions).orElse(0.0);
	}

	public void setAvgDeletions(double avgDeletions) {
		this.avgDeletions = Optional.ofNullable(avgDeletions).orElse(0.0);
	}

	public double getAvgChangedFiles() {
		return Optional.ofNullable(avgChangedFiles).orElse(0.0);
	}

	public void setAvgChangedFiles(double avgChangedFiles) {
		this.avgChangedFiles = Optional.ofNullable(avgChangedFiles).orElse(0.0);
	}

	public double getAvgDaysIntervalOfPR() {
		return Optional.ofNullable(avgDaysIntervalOfPR).orElse(0.0);
	}

	public void setAvgDaysIntervalOfPR(double avgDaysIntervalOfPR) {
		this.avgDaysIntervalOfPR = Optional.ofNullable(avgDaysIntervalOfPR).orElse(0.0);
	}
	
	//own repos arraylist operation
	public void addOwnRepo(Repository repo){
		this.ownRepos.add(repo);
	}
	
	public void addAllOwnRepo(List<Repository> ownRepos){
		this.ownRepos.addAll(ownRepos);
	}
	
	public void clearOwnRepos(){
		this.ownRepos.clear();
	}
	
	//set average data based on own repos
	public void setAvgReposData(){
		/*
		 * [0] totalSize
		 * [1] totalStargazersCount
		 * [2] totalSubscribersCount
		 * [3] totalForksCount
		 * [4] totalIssuesCount
		 * [5] numOfRepoWithIssue
		 * [6] numOfRepoWithAvgIssueDays
		 */
		long[] initLong = {0,0,0,0,0,0,0};
		AtomicLongArray totalsLong = new AtomicLongArray(initLong);
		/*
		 * [0] totalHandledIssuesRatio
		 * [1] totalIssueHandledDays
		 * [2] totalMajorLanguageLOC
		 * [3] totalSqaleIndex
		 * [4] totalDebtRatio
		 */
		Double[] initDouble = {0.0,0.0,0.0,0.0,0.0};
		AtomicReferenceArray<Double> totalsDouble = new AtomicReferenceArray<Double>(initDouble);
		System.out.println("OwnRepos:"+ownRepos.size());
		this.numOfAnalyzedRepos = Math.toIntExact(ownRepos.parallelStream()
			.filter(repo -> repo.getSonarMetrics()!=null&&repo.getSonarMetrics().size()>0)
			.map(repo->{
				//long variables
				totalsLong.addAndGet(0, repo.getSize());//totalSize
				totalsLong.addAndGet(1, repo.getStargazersCount());//totalStargazersCount
				totalsLong.addAndGet(2, repo.getSubscribersCount());//totalSubscribersCount
				totalsLong.addAndGet(3, repo.getForksCount());//totalForksCount
				totalsLong.addAndGet(4, repo.getIssuesCount());//totalIssuesCount
				//issue handling ratio and average issue handled days
				if(repo.getIssuesCount()!=0){
					totalsDouble.accumulateAndGet(0, (double)repo.getHandledIssuesCount()/(double)repo.getIssuesCount(), (m,n)->m+n);//totalHandledIssuesRatio
					totalsLong.incrementAndGet(5);
				}
				if(repo.getAvgIssueHandledDays()!=0){
					totalsDouble.accumulateAndGet(1, repo.getAvgIssueHandledDays(), (m,n)->m+n);//totalIssueHandledDays
					totalsLong.incrementAndGet(6);
				}				
				//double variables
				System.out.println(repo.getSonarMetrics("ncloc").getValue());
				totalsDouble.accumulateAndGet(2, repo.getSonarMetrics("ncloc").getValue(), (m,n)->m+n);//totalMajorLanguageLOC
				totalsDouble.accumulateAndGet(3, repo.getSonarMetrics("sqale_index").getValue(), (m,n)->m+n);//totalSqaleIndex
				totalsDouble.accumulateAndGet(4, repo.getSonarMetrics("sqale_debt_ratio").getValue(), (m,n)->m+n);//totalDebtRatio
				return true;
			}).count());
		System.out.println("OwnRepos:"+ownRepos.size());
		if(this.numOfAnalyzedRepos != 0){
			//set basic repo info
			this.setAvgSize((double)totalsLong.get(0)/(double)this.numOfAnalyzedRepos);
			this.setAvgStargazersCount((double)totalsLong.get(1)/(double)this.numOfAnalyzedRepos);
			this.setAvgSubscribersCount((double)totalsLong.get(2)/(double)this.numOfAnalyzedRepos);
			this.setAvgForksCount((double)totalsLong.get(3)/(double)this.numOfAnalyzedRepos);
			//set issue into
			this.setAvgIssuesCount((double)totalsLong.get(4)/(double)this.numOfAnalyzedRepos);
			if(totalsLong.get(5) != 0)
				this.setAvgHandledIssuesRatio(totalsDouble.get(0)/totalsLong.get(5));
			if(totalsLong.get(6) != 0)
				this.setAvgIssueHandledDays(totalsDouble.get(1)/totalsLong.get(6));						
			//set SonarQube analysis results
			this.setAvgMajorLanguageLOC(totalsDouble.get(2)/this.numOfAnalyzedRepos);
			this.setAvgSqaleIndex(totalsDouble.get(3)/this.numOfAnalyzedRepos);	
			this.setAvgDebtRatio(totalsDouble.get(4)/this.numOfAnalyzedRepos);			
		}			
	}
}

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
	//SonarQube metrics
	//Reliability
	private double avgBugs;
	//Vulnerabilities
	private double avgVulnerabilities;
	//Maintainability
	private double avgCodeSmells;
	private double avgSqaleIndex;
	private double avgDebtRatio;
	//Duplications
	private double avgDuplicatedLineDensity;
	private double avgDuplicatedBlocks;
	private double avgDuplicatedLines;
	private double avgDuplicatedFiles;
	//Size
	private double avgMajorLanguageLOC;
	private double avgLines;
	private double avgStatements;
	private double avgFunctions;
	private double avgClasses;
	private double avgFiles;
	private double avgDirectories;
	//Complexity
	private double avgComplexity;
	private double avgFileComplexity;
	private double avgFunctionComplexity;
	private double avgClassComplexity;
	//Documentation
	private double avgCommemtLineDensity;
	private double avgCommentLines;
	private double avgPublicAPI;
	private double avgDocumentedAPIDensity;
	private double avgUndocumentedAPI;	
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
		//sonarqube metrics
		this.setAvgBugs(0.0);
		this.setAvgVulnerabilities(0.0);
		this.setAvgCodeSmells(0.0);
		this.setAvgSqaleIndex(0.0);
		this.setAvgDebtRatio(0.0);
		this.setAvgDuplicatedLineDensity(0.0);
		this.setAvgDuplicatedBlocks(0.0);
		this.setAvgDuplicatedLines(0.0);
		this.setAvgDuplicatedFiles(0.0);
		this.setAvgMajorLanguageLOC(0.0);
		this.setAvgLines(0.0);		
		this.setAvgStatements(0.0);
		this.setAvgFunctions(0.0);
		this.setAvgClasses(0.0);
		this.setAvgFiles(0.0);
		this.setAvgDirectories(0.0);
		this.setAvgComplexity(0.0);
		this.setAvgFileComplexity(0.0);
		this.setAvgFunctionComplexity(0.0);
		this.setAvgClassComplexity(0.0);
		this.setAvgCommemtLineDensity(0.0);
		this.setAvgCommentLines(0.0);
		this.setAvgPublicAPI(0.0);
		this.setAvgDocumentedAPIDensity(0.0);
		this.setAvgUndocumentedAPI(0.0);
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
	
	public double getAvgBugs() {
		return avgBugs;
	}

	public void setAvgBugs(double avgBugs) {
		this.avgBugs = avgBugs;
	}
	
	public double getAvgVulnerabilities() {
		return avgVulnerabilities;
	}

	public void setAvgVulnerabilities(double avgVulnerabilities) {
		this.avgVulnerabilities = avgVulnerabilities;
	}

	public double getAvgCodeSmells() {
		return avgCodeSmells;
	}

	public void setAvgCodeSmells(double avgCodeSmells) {
		this.avgCodeSmells = avgCodeSmells;
	}

	public double getAvgDuplicatedLineDensity() {
		return avgDuplicatedLineDensity;
	}

	public void setAvgDuplicatedLineDensity(double avgDuplicatedLineDensity) {
		this.avgDuplicatedLineDensity = avgDuplicatedLineDensity;
	}

	public double getAvgDuplicatedBlocks() {
		return avgDuplicatedBlocks;
	}

	public void setAvgDuplicatedBlocks(double avgDuplicatedBlocks) {
		this.avgDuplicatedBlocks = avgDuplicatedBlocks;
	}

	public double getAvgDuplicatedLines() {
		return avgDuplicatedLines;
	}

	public void setAvgDuplicatedLines(double avgDuplicatedLines) {
		this.avgDuplicatedLines = avgDuplicatedLines;
	}

	public double getAvgDuplicatedFiles() {
		return avgDuplicatedFiles;
	}

	public void setAvgDuplicatedFiles(double avgDuplicatedFiles) {
		this.avgDuplicatedFiles = avgDuplicatedFiles;
	}

	public double getAvgLines() {
		return avgLines;
	}

	public void setAvgLines(double avgLines) {
		this.avgLines = avgLines;
	}

	public double getAvgStatements() {
		return avgStatements;
	}

	public void setAvgStatements(double avgStatements) {
		this.avgStatements = avgStatements;
	}

	public double getAvgFunctions() {
		return avgFunctions;
	}

	public void setAvgFunctions(double avgFunctions) {
		this.avgFunctions = avgFunctions;
	}

	public double getAvgClasses() {
		return avgClasses;
	}

	public void setAvgClasses(double avgClasses) {
		this.avgClasses = avgClasses;
	}

	public double getAvgFiles() {
		return avgFiles;
	}

	public void setAvgFiles(double avgFiles) {
		this.avgFiles = avgFiles;
	}

	public double getAvgDirectories() {
		return avgDirectories;
	}

	public void setAvgDirectories(double avgDirectories) {
		this.avgDirectories = avgDirectories;
	}

	public double getAvgComplexity() {
		return avgComplexity;
	}

	public void setAvgComplexity(double avgComplexity) {
		this.avgComplexity = avgComplexity;
	}

	public double getAvgFileComplexity() {
		return avgFileComplexity;
	}

	public void setAvgFileComplexity(double avgFileComplexity) {
		this.avgFileComplexity = avgFileComplexity;
	}

	public double getAvgFunctionComplexity() {
		return avgFunctionComplexity;
	}

	public void setAvgFunctionComplexity(double avgFunctionComplexity) {
		this.avgFunctionComplexity = avgFunctionComplexity;
	}

	public double getAvgClassComplexity() {
		return avgClassComplexity;
	}

	public void setAvgClassComplexity(double avgClassComplexity) {
		this.avgClassComplexity = avgClassComplexity;
	}

	public double getAvgCommemtLineDensity() {
		return avgCommemtLineDensity;
	}

	public void setAvgCommemtLineDensity(double avgCommemtLineDensity) {
		this.avgCommemtLineDensity = avgCommemtLineDensity;
	}

	public double getAvgCommentLines() {
		return avgCommentLines;
	}

	public void setAvgCommentLines(double avgCommentLines) {
		this.avgCommentLines = avgCommentLines;
	}

	public double getAvgPublicAPI() {
		return avgPublicAPI;
	}

	public void setAvgPublicAPI(double avgPublicAPI) {
		this.avgPublicAPI = avgPublicAPI;
	}

	public double getAvgDocumentedAPIDensity() {
		return avgDocumentedAPIDensity;
	}

	public void setAvgDocumentedAPIDensity(double avgDocumentedAPIDensity) {
		this.avgDocumentedAPIDensity = avgDocumentedAPIDensity;
	}

	public double getAvgUndocumentedAPI() {
		return avgUndocumentedAPI;
	}

	public void setAvgUndocumentedAPI(double avgUndocumentedAPI) {
		this.avgUndocumentedAPI = avgUndocumentedAPI;
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
		Double[] initDouble = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
		AtomicReferenceArray<Double> totalsDouble = new AtomicReferenceArray<Double>(initDouble);
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
				totalsDouble.accumulateAndGet(2, repo.getSonarMetrics("bugs").getValue(), (m,n)->m+n);//totalBugs
				totalsDouble.accumulateAndGet(3, repo.getSonarMetrics("vulnerabilities").getValue(), (m,n)->m+n);//totalVulnerabilities
				totalsDouble.accumulateAndGet(4, repo.getSonarMetrics("code_smells").getValue(), (m,n)->m+n);//totalCodeSmells
				totalsDouble.accumulateAndGet(5, repo.getSonarMetrics("sqale_index").getValue(), (m,n)->m+n);//totalSQALEIndex
				totalsDouble.accumulateAndGet(6, repo.getSonarMetrics("sqale_debt_ratio").getValue(), (m,n)->m+n);//totalDebtRatio
				totalsDouble.accumulateAndGet(7, repo.getSonarMetrics("duplicated_lines_density").getValue(), (m,n)->m+n);//totalDuplicatedLinesDensity
				totalsDouble.accumulateAndGet(8, repo.getSonarMetrics("duplicated_blocks").getValue(), (m,n)->m+n);//totalDuplicatedBlocks
				totalsDouble.accumulateAndGet(9, repo.getSonarMetrics("duplicated_lines").getValue(), (m,n)->m+n);//totalDuplicatedLines
				totalsDouble.accumulateAndGet(10, repo.getSonarMetrics("duplicated_files").getValue(), (m,n)->m+n);//totalDuplicatedFiles
				totalsDouble.accumulateAndGet(11, repo.getSonarMetrics("ncloc").getValue(), (m,n)->m+n);//totalLOC
				totalsDouble.accumulateAndGet(12, repo.getSonarMetrics("lines").getValue(), (m,n)->m+n);//totalLines
				totalsDouble.accumulateAndGet(13, repo.getSonarMetrics("statements").getValue(), (m,n)->m+n);//totalStatements
				totalsDouble.accumulateAndGet(14, repo.getSonarMetrics("functions").getValue(), (m,n)->m+n);//totalFunctions
				totalsDouble.accumulateAndGet(15, repo.getSonarMetrics("classes").getValue(), (m,n)->m+n);//totalClasses
				totalsDouble.accumulateAndGet(16, repo.getSonarMetrics("files").getValue(), (m,n)->m+n);//totalFiles
				totalsDouble.accumulateAndGet(17, repo.getSonarMetrics("directories").getValue(), (m,n)->m+n);//totalDirectories
				totalsDouble.accumulateAndGet(18, repo.getSonarMetrics("complexity").getValue(), (m,n)->m+n);//totalCompelxity
				totalsDouble.accumulateAndGet(19, repo.getSonarMetrics("file_complexity").getValue(), (m,n)->m+n);//totalFileComplexity
				totalsDouble.accumulateAndGet(20, repo.getSonarMetrics("function_complexity").getValue(), (m,n)->m+n);//totalFunctionComplexity
				totalsDouble.accumulateAndGet(21, repo.getSonarMetrics("class_complexity").getValue(), (m,n)->m+n);//totalClassComplexity
				totalsDouble.accumulateAndGet(22, repo.getSonarMetrics("comment_lines_density").getValue(), (m,n)->m+n);//totalCommentLinesDensity
				totalsDouble.accumulateAndGet(23, repo.getSonarMetrics("comment_lines").getValue(), (m,n)->m+n);//totalCommentLines
				totalsDouble.accumulateAndGet(24, repo.getSonarMetrics("public_api").getValue(), (m,n)->m+n);//totalPublicAPI
				totalsDouble.accumulateAndGet(25, repo.getSonarMetrics("public_documented_api_density").getValue(), (m,n)->m+n);//totalDocumentedAPIDensity
				totalsDouble.accumulateAndGet(26, repo.getSonarMetrics("public_undocumented_api").getValue(), (m,n)->m+n);//totalUndocumentedAPI
				return true;
			}).count());
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
			this.setAvgBugs(totalsDouble.get(2)/this.numOfAnalyzedRepos);
			this.setAvgVulnerabilities(totalsDouble.get(3)/this.numOfAnalyzedRepos);
			this.setAvgCodeSmells(totalsDouble.get(4)/this.numOfAnalyzedRepos);
			this.setAvgSqaleIndex(totalsDouble.get(5)/this.numOfAnalyzedRepos);
			this.setAvgDebtRatio(totalsDouble.get(6)/this.numOfAnalyzedRepos);
			this.setAvgDuplicatedLineDensity(totalsDouble.get(7)/this.numOfAnalyzedRepos);
			this.setAvgDuplicatedBlocks(totalsDouble.get(8)/this.numOfAnalyzedRepos);
			this.setAvgDuplicatedLines(totalsDouble.get(9)/this.numOfAnalyzedRepos);
			this.setAvgDuplicatedFiles(totalsDouble.get(10)/this.numOfAnalyzedRepos);
			this.setAvgMajorLanguageLOC(totalsDouble.get(11)/this.numOfAnalyzedRepos);
			this.setAvgLines(totalsDouble.get(12)/this.numOfAnalyzedRepos);
			this.setAvgStatements(totalsDouble.get(13)/this.numOfAnalyzedRepos);
			this.setAvgFunctions(totalsDouble.get(14)/this.numOfAnalyzedRepos);
			this.setAvgClasses(totalsDouble.get(15)/this.numOfAnalyzedRepos);
			this.setAvgFiles(totalsDouble.get(16)/this.numOfAnalyzedRepos);
			this.setAvgDirectories(totalsDouble.get(17)/this.numOfAnalyzedRepos);
			this.setAvgComplexity(totalsDouble.get(18)/this.numOfAnalyzedRepos);
			this.setAvgFileComplexity(totalsDouble.get(19)/this.numOfAnalyzedRepos);
			this.setAvgFunctionComplexity(totalsDouble.get(20)/this.numOfAnalyzedRepos);
			this.setAvgClassComplexity(totalsDouble.get(21)/this.numOfAnalyzedRepos);
			this.setAvgCommemtLineDensity(totalsDouble.get(22)/this.numOfAnalyzedRepos);
			this.setAvgCommentLines(totalsDouble.get(23)/this.numOfAnalyzedRepos);
			this.setAvgPublicAPI(totalsDouble.get(24)/this.numOfAnalyzedRepos);
			this.setAvgDocumentedAPIDensity(totalsDouble.get(25)/this.numOfAnalyzedRepos);
			this.setAvgUndocumentedAPI(totalsDouble.get(26)/this.numOfAnalyzedRepos);
			this.setAvgMajorLanguageLOC(totalsDouble.get(2)/this.numOfAnalyzedRepos);
			this.setAvgSqaleIndex(totalsDouble.get(3)/this.numOfAnalyzedRepos);	
			this.setAvgDebtRatio(totalsDouble.get(4)/this.numOfAnalyzedRepos);			
		}			
	}

	
}

package com.eptd.dminer.core;

import java.util.ArrayList;
import java.util.List;

public class MajorRepository extends Repository{
	private int taskID = 0;
	private long maxContribution = 0;
	private long totalContribution = 0;
	private ArrayList<User> contributors = new ArrayList<User>();
	private ArrayList<FilteredUser> filteredContributors = new ArrayList<FilteredUser>();
	
	public MajorRepository(){
		super();
	}

	public ArrayList<User> getContributors() {
		return contributors;
	}

	public void setContributors(ArrayList<User> contributors) {
		this.contributors = contributors;
	}
	
	public void addContributor(User contributor){
		this.contributors.add(contributor);
	}
	
	public void addAllContributors(List<User> contributors){
		this.contributors.addAll(contributors);
	}

	public int getTaskID() {
		return taskID;
	}
	
	public void setTaskID(int taskID){
		this.taskID = taskID;
	}

	public MajorRepository setNewTaskID(int taskID) {
		this.taskID = taskID;
		return this;
	}

	public ArrayList<FilteredUser> getFilteredContributors() {
		return filteredContributors;
	}

	public void setFilteredContributors(ArrayList<FilteredUser> filteredCons) {
		this.filteredContributors = filteredCons;
	}
	
	public void addFilteredContributors(FilteredUser contributor){
		this.filteredContributors.add(contributor);
	}
	
	public void addAllFilteredContributors(List<FilteredUser> filteredContributors){
		this.filteredContributors.addAll(filteredContributors);
	}

	public long getMaxContribution() {
		return maxContribution;
	}

	public void setMaxContribution(long maxContribution) {
		this.maxContribution = maxContribution;
	}

	public long getTotalContribution() {
		return totalContribution;
	}

	public void setTotalContribution(long totalContribution) {
		this.totalContribution = totalContribution;
	}
}

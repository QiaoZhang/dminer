package com.eptd.dminer.processor;

import com.eptd.dminer.core.FilteredUser;
import com.google.gson.JsonObject;

public class FilteredUserProcessor {
	private ProjectLogger logger;
	private JsonObject userData;
	private FilteredUser filteredUser;
	
	public FilteredUserProcessor(JsonObject userData,ProjectLogger logger){
		this.setUserData(userData);
		this.setFilteredUser(new FilteredUser());
		this.logger = new ProjectLogger(logger).append("FilteredUserProcessor");
	}
	
	public boolean process(){
		try{
			this.filteredUser.setLogin(userData.get("login").getAsString());
			this.filteredUser.setUserId(userData.get("id").getAsLong());
			this.filteredUser.setUserURL(userData.get("url").getAsString());
			this.filteredUser.setUserHTML(userData.get("html_url").getAsString());
			this.filteredUser.setContribution(userData.get("contributions").getAsLong());
			return true;
		}catch (Exception e){
			logger.error("Exception when extracting filtered contributor ",e);
			return false;
		}		
	}

	public FilteredUser getFilteredUser() {
		return filteredUser;
	}

	public void setFilteredUser(FilteredUser filteredUser) {
		this.filteredUser = filteredUser;
	}

	public JsonObject getUserData() {
		return userData;
	}

	public void setUserData(JsonObject userData) {
		this.userData = userData;
	}
}

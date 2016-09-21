package com.eptd.dminer.core;

public class FilteredUser {
	private String userURL;
	private String userHTML;
	private long userId;						//also id for data crawler
	private String login;						//backup id
	private long contribution;
	
	public String getUserURL() {
		return userURL;
	}
	
	public void setUserURL(String userURL) {
		this.userURL = userURL;
	}

	public String getUserHTML() {
		return userHTML;
	}

	public void setUserHTML(String userHTML) {
		this.userHTML = userHTML;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public long getContribution() {
		return contribution;
	}

	public void setContribution(long contribution) {
		this.contribution = contribution;
	}
}

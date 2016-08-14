package com.eptd.dminer.core;

import org.joda.time.DateTime;

public class Task {
	private int taskID = 0;
	private int clientID = 0;
	private String state = null;
	private int total = 0;
	private int success = 0;
	private int failed = 0;
	private String paraLanguage = null;
	private String paraUser = null;
	private String paraSize = null;
	private String paraStars = null;
	private String paraForks = null;
	private DateTime created = new DateTime();
	
	public int getTaskID() {
		return taskID;
	}
	
	public Task setTaskID(int taskID) {
		this.taskID = taskID;
		return this;
	}

	public int getClientID() {
		return clientID;
	}

	public Task setClientID(int clientID) {
		this.clientID = clientID;
		return this;
	}

	public String getState() {
		return state;
	}

	public Task setState(String state) {
		this.state = state;
		return this;
	}

	public int getTotal() {
		return total;
	}

	public Task setTotal(int total) {
		this.total = total;
		return this;
	}

	public int getSuccess() {
		return success;
	}

	public Task setSuccess(int success) {
		this.success = success;
		return this;
	}

	public int getFailed() {
		return failed;
	}

	public Task setFailed(int failed) {
		this.failed = failed;
		return this;
	}

	public String getParaLanguage() {
		return paraLanguage;
	}

	public Task setParaLanguage(String paraLanguage) {
		this.paraLanguage = paraLanguage;
		return this;
	}

	public String getParaUser() {
		return paraUser;
	}

	public Task setParaUser(String paraUser) {
		this.paraUser = paraUser;
		return this;
	}

	public String getParaSize() {
		return paraSize;
	}

	public Task setParaSize(String paraSize) {
		this.paraSize = paraSize;
		return this;
	}

	public String getParaStars() {
		return paraStars;
	}

	public Task setParaStars(String paraStars) {
		this.paraStars = paraStars;
		return this;
	}

	public String getParaForks() {
		return paraForks;
	}

	public Task setParaForks(String paraForks) {
		this.paraForks = paraForks;
		return this;
	}

	public DateTime getCreated() {
		return created;
	}

	public Task setCreated(DateTime created) {
		this.created = created;
		return this;
	}
}

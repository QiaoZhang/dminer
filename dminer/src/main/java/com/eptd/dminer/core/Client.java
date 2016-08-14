package com.eptd.dminer.core;

public class Client {
	private String fingerPrint;
	private String username;
	private String password;
	private String appClientID;
	private String appClientSecret;


	public String getFingerPrint() {
		return fingerPrint;
	}

	public Client setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public Client setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public Client setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getAppClientID() {
		return appClientID;
	}

	public Client setAppClientID(String appClientID) {
		this.appClientID = appClientID;
		return this;
	}

	public String getAppClientSecret() {
		return appClientSecret;
	}

	public Client setAppClientSecret(String appClientSecret) {
		this.appClientSecret = appClientSecret;
		return this;
	}
}

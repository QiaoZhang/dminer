package com.eptd.dminer.core;

public class SonarMetrics {
	private String key;
	private Double value;
	
	public SonarMetrics(String key,Double value){
		this.setKey(key);
		this.setValue(value);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
}

package com.eptd.dminer.core;

public class SonarMetrics {
	private String key;
	private Double value;
	private String formattedValue;
	
	public SonarMetrics(String key,Double value, String formattedValue){
		this.setKey(key);
		this.setValue(value);
		this.setFormattedValue(formattedValue);
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

	public String getFormattedValue() {
		return formattedValue;
	}

	public void setFormattedValue(String formattedValue) {
		this.formattedValue = formattedValue;
	}
	
}

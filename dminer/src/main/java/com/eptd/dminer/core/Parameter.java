package com.eptd.dminer.core;

public class Parameter {
	protected String paraName;
	protected String paraValue;
	
	public Parameter(String paraName, String paraValue){
		this.setParaName(paraName);
		this.setParaValue(paraValue);
	}

	public String getParaName() {
		return paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	public String getParaValue() {
		return paraValue;
	}

	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}
	
	public String getURLParameter(){
		return this.paraName + "=" + this.paraValue;
	}
}

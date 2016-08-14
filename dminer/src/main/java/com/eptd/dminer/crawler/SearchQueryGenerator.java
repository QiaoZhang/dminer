package com.eptd.dminer.crawler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchQueryGenerator {	
	private final String BASEURL = "https://api.github.com/search/";
	private final String REPO = "repositories";
	private final String USER = "users";
	private final String ISSUE = "issues";
	private final String CODE = "code";
	
	private String queryBase;
	private String searchStr = "";
	
	private List<String> validTermKey;
	
	
	public SearchQueryGenerator(String searchType){
		validTermKey = new ArrayList<String>();
		if(searchType.equals("repo")){
			queryBase = BASEURL + REPO;
			validTermKey.addAll(Arrays.asList("in","size","forks","fork","created",
					"pushed","user","repo","language","stars"));
		}else if(searchType.equals("user")){
			queryBase = BASEURL + USER;
			validTermKey.addAll(Arrays.asList("type","in","repo","location","language",
					"created","followers"));
		}else if(searchType.equals("issue")){
			queryBase = BASEURL + ISSUE;
			validTermKey.addAll(Arrays.asList("type","in","author","assignee","mentions",
					"commenter","involves","team","state","labels","no","language","is",
					"created","updated","merged","status","head","base","cloased","comments",
					"user","repo"));
		}else if(searchType.equals("code")){
			queryBase = BASEURL + CODE;
			validTermKey.addAll(Arrays.asList("in","language","fork","size","path",
					"filename","extension","user","repo"));
		}
	}
	
	public SearchQueryGenerator addSearchTerm(String key, String value){
		if(validTermKey.contains(key)){
			if(searchStr.length()>0)
				searchStr += "+";
			searchStr += key + ":" + value;
			return this;
		}else
			return null;			
	}
	
	public String getQueryBase(){
		return this.queryBase;
	}
	
	public String getSearchStr(){
		return this.searchStr;
	}
}

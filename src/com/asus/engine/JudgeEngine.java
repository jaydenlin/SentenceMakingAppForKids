package com.asus.engine;

import com.asus.asyctask.AsyncTaskResponse;
import com.asus.data.OntologyData;
import com.asus.data.WikiData;
import com.asus.dialogue.Question;

public abstract class JudgeEngine {
	
	
	protected abstract void setKeywords();
	public abstract String onRightResponse();
	public abstract String onWrongResponse();
	public abstract String onTeachResponse();
	public abstract String onConfusedResponse();
	public abstract String onWikiDataGet(String output);
	
	OntologyData ontologyData;
	WikiData wikiData;
	Question question;
	String answer;
	String[] keywords;
	private String rightAnswer;
	private String wrongAnswer;
	
	public JudgeEngine(Question question,String answer){
		this.question=question;
		this.ontologyData=OntologyData.getInstance();
		this.answer = answer;
		this.wikiData = WikiData.getInstance(new AsyncTaskResponse<String>() {
			@Override
			public void processFinish(String output) {
				onWikiDataGet(output);
			}
		});
		setKeywords();
		
	}
	
	public boolean IsRight() {
		if (keywords == null) {
			return false;
		}
		boolean isFail = true;
		for (int i = 0; i < keywords.length; i++) {
			if (answer.indexOf(keywords[i]) != -1) {
				isFail = false;
				rightAnswer = keywords[i];
				break;
			} else {
				//do nothing
				wrongAnswer = answer;
			}
		}
		return isFail;
	}

	public boolean IsConfused() {
		if (keywords == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getRightAnswer() {
		try{
			return rightAnswer;
		}catch(Exception e){
			return "";
		}
	}

	public String getWrongAnswer() {
		try{
			return wrongAnswer;
		}catch(Exception e){
			return "";
		}
	}
	
	protected void searchWikiData(String searchData) {
		wikiData.getWikiData(searchData);
	}
	
}

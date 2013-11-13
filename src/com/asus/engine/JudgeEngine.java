package com.asus.engine;

import android.util.Log;

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
	public abstract String onNextQuestionResponse();
	public abstract String getCurrentQuestion();
	OntologyData ontologyData;
	WikiData wikiData;
	Question question;
	String answer;
	String[] keywords;
	public boolean toTeachFlag=false;
	private static String rightAnswer="";
	private static String wrongAnswer="";
	
	public JudgeEngine(Question question,String answer){
		this.question=question;
		this.ontologyData=OntologyData.getInstance();
		this.answer = answer;
		this.wikiData = WikiData.getInstance();
		setKeywords();
	}
	
	public boolean IsRight() {
		if (keywords == null) {
			return false;
		}
		boolean isRight = false;
		for (int i = 0; i < keywords.length; i++) {
			if (answer.indexOf(keywords[i]) != -1) {
				isRight = true;
				rightAnswer = keywords[i];
				break;
			} else {
				//do nothing
				wrongAnswer = answer;
			}
		}
		return isRight;
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
			Log.w(getClass().getSimpleName(), "rightAnswer is empty");
			return "";
		}
	}

	public String getWrongAnswer() {
		try{
			return wrongAnswer;
		}catch(Exception e){
			Log.w(getClass().getSimpleName(), "wrongAnswer is empty");
			return "";
		}
	}
	
	
	public void searchWikiData(String searchData,AsyncTaskResponse<String> delegate) {
		wikiData.searchWikiData(searchData, delegate);
	}
	
}

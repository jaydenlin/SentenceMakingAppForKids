package com.asus.engine;

import java.util.List;

import android.util.Log;

import com.asus.asyctask.AsyncTaskResponse;
import com.asus.data.ConceptNetData;
import com.asus.data.DBHelper;
import com.asus.data.OntologyData;
import com.asus.data.WikiData;
import com.asus.dialogue.Question;

public abstract class JudgeEngine {
	
	
	protected abstract void setKeywords();
	public abstract String onRightResponse();
	public abstract String onWrongResponse();
	public abstract String onTeachResponse();
	public abstract String onConfusedResponse();
	public abstract String getNextQuestion();
	public abstract String getCurrentQuestion();
	public abstract int getTeachPhoto();
	
	public String teachString;
	
	protected OntologyData ontologyData;
	protected ConceptNetData conceptNetData;
	protected WikiData wikiData;
	protected Question question;
	protected String answer;
	protected String[] keywords;
	
	private static String rightAnswer="";
	private static String wrongAnswer="";
	
	public JudgeEngine(Question question,String answer){
		this.question=question;
		this.ontologyData=OntologyData.getInstance();
		this.answer = answer;
		this.wikiData = WikiData.getInstance();
		this.conceptNetData=ConceptNetData.getInstance();
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
		
//		if (keywords == null) {
//			return true;
//		} else {
//			return false;
//		}
		String[] confusedWords={"矗ボ","ぃ笵","钮ぃ来","ぃ穦","或","螟","年","毙и","毙厩","传肈","","ぐ或","裕或","或"};
		boolean IsConfused = false;
		for (int i = 0; i < confusedWords.length; i++) {
			if (answer.indexOf(confusedWords[i]) != -1) {
				IsConfused = true;
				answer="";//because the user is not answering they are confused,so set this answer as empty
				rightAnswer = "";
				wrongAnswer = "";
				break;
			} else {
				//do nothing
				//wrongAnswer = answer;
			}
		}
		return IsConfused;
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
	
	public void searchConceptNet(AsyncTaskResponse<List<String>> delegate) {
		
		conceptNetData.searchConceptNet(question.questionPhrase, answer, delegate);
	}
	
	public abstract int[] getHintPhotos();
}

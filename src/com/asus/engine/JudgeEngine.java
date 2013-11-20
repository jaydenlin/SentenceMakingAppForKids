package com.asus.engine;

import java.util.ArrayList;
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
	protected List<String> demoSentences=new ArrayList<String>();
	protected JudgeEngineCallback judgeEngineCallback;
	private boolean isRight;
	private boolean isConfused;
	private static String rightAnswer="";
	private static String wrongAnswer="";
	
	public JudgeEngine(Question question,String answer,JudgeEngineCallback judgeEngineCallback){
		this.question=question;
		this.ontologyData=OntologyData.getInstance();
		this.answer = answer;
		this.wikiData = WikiData.getInstance();
		this.conceptNetData=ConceptNetData.getInstance();
		this.judgeEngineCallback=judgeEngineCallback;
		setKeywords();
	}
	
	public void start(){//use callback here,so u can call this start() to automatically bind callback
		if(IsConfused()){
			judgeEngineCallback.onConfused();
		}else{
			checkRightOrWrong();
		}
	}
	
	private void checkRightOrWrong() {
		//checkout db
		isRight = false;
		if(keywords!=null){
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
		}
		//checkout concept net
		conceptNetData.searchConceptNet(question.questionPhrase, answer, new AsyncTaskResponse<List<String>>() {
			@Override
			public void processFinish(List<String> output) {
				if(output.size()>0){
					rightAnswer=answer;
					demoSentences=output;
					
					if(isRight!=true){
						isRight=true;
						answer="";
					}
					
				}else{
					wrongAnswer=answer;
				}
				//finally
				if(isRight){
					judgeEngineCallback.onRight();
				}else{
					judgeEngineCallback.onWrong();
				}
			}
		});
		
	}

	private boolean IsConfused() {
		
		String[] confusedWords={"����","�����D","ť����","���|","���","��","��","�Ч�","�о�","���@�D","�U�@","����","ԣ��","�ƻ�"};
		isConfused = false;
		for (int i = 0; i < confusedWords.length; i++) {
			if (answer.indexOf(confusedWords[i]) != -1) {
				isConfused = true;
				answer="";//because the user is not answering they are confused,so set this answer as empty
				rightAnswer = "";
				wrongAnswer = "";
				break;
			} else {
				//do nothing
				//wrongAnswer = answer;
			}
		}
		return isConfused;
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
	
	public List<String> getDemoSentences(){
		return demoSentences;
	}
	
	public void searchWikiData(String searchData,AsyncTaskResponse<String> delegate) {
		wikiData.searchWikiData(searchData, delegate);
	}
	
	public void searchConceptNet(AsyncTaskResponse<List<String>> delegate) {
		
		conceptNetData.searchConceptNet(question.questionPhrase, answer, delegate);
	}
	
	public abstract int[] getHintPhotos();
	
	
}

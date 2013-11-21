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
import com.asus.exception.AsyncTaskResponseNotFound;
import com.asus.exception.MatchedWordsNotFound;
import com.asus.exception.PhotoIdsNotFound;
import com.asus.exception.TeachStringResponseNotFound;

public abstract class JudgeEngine {
	
	
	protected abstract void setKeywords() throws MatchedWordsNotFound;
	public abstract String onRightResponse();
	public abstract String onWrongResponse();
	public abstract String onTeachResponse() throws TeachStringResponseNotFound;
	public abstract String onConfusedResponse();
	public abstract String getNextQuestion();
	public abstract String getCurrentQuestion();
	public abstract int getTeachPhoto() throws PhotoIdsNotFound;
	public abstract int[] getHintPhotos() throws PhotoIdsNotFound;
	
	public String teachString="";
	
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
		
	}
	
	public void start(){//use callback here,so u can call this start() to automatically bind callback
		if(IsConfusedThenSetAnswers()){
			judgeEngineCallback.onConfused();
		}else{
			checkRightOrWrongThenSetAnswersAndDemoString();
		}
	}
	
	private void checkRightOrWrongThenSetAnswersAndDemoString() {
		try {
			setKeywords();
		} catch (MatchedWordsNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	private boolean IsConfusedThenSetAnswers() {
		
		String[] confusedWords={"矗ボ","ぃ笵","钮ぃ来","ぃ穦","或","螟","年","毙и","毙厩","传肈","","ぐ或","裕或","或"};
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
	
	
	
}

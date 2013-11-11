package com.asus.dialogue;

import java.io.IOException;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.text.Html.TagHandler;

import com.asus.asyctask.AsyncTaskResponse;
import com.asus.bubbles.DiscussArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.data.OntologyData;
import com.asus.data.WikiData;

public class DialogueDispather implements JudgeManager{
	
	public static DialogueDispather instance;
	private Question question;
	private String answer;
	private String matchedAnswer;
	private String[] keywords;
	private OntologyData ontologyData;
	private WikiData wikiData;
	private DiscussArrayAdapter adapter;
	
	public static DialogueDispather getInstance(Question question, String answer,DiscussArrayAdapter adapter){
		if(instance==null){
			instance = new DialogueDispather(question,answer,adapter);
		}else{
			//update question and answer
			instance.question = question;
			instance.answer = answer;
		}
		return instance;
	}
	
	private DialogueDispather(Question question, String answer,DiscussArrayAdapter adapter){
		this.question = question;
		this.answer = answer;
		this.adapter = adapter;
		this.ontologyData=OntologyData.getInstance();
		this.wikiData = WikiData.getInstance(new AsyncTaskResponse<String>() {
			
			@Override
			public void processFinish(String output) {
				DialogueDispather.this.adapter.add(new OneComment(true, output));
			}
		}); 
	}
	
	public void start(){
		if(question.isAskingAdj){
			onAskingAdj();
		}else{
			onAskingNoun();
		}
	}
	
	private void onAskingAdj(){
		keywords=ontologyData.getMatchedAdjArrayForOneNoun(question.questionPhrase);
		if(keywords==null){
			onConfused();
		}
		else if(IsMatchAnswerFail()){
			onWrongAnwser();
		}else{
			onRightAnwser();
		}
		
	}
	
	private void onAskingNoun(){
		keywords=ontologyData.getMatchedNounArrayForOneAdj(question.questionPhrase);
		if(keywords==null){
			onConfused();
		}
		else if(IsMatchAnswerFail()){
			onWrongAnwser();
		}else{
			onRightAnwser();
		}
	}
	
	@Override
	public void onWrongAnwser() {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true, answer+"? �A�Q�Q�ݦ��S����n����"));
		onTeach();
	}

	@Override
	public void onRightAnwser() {
		if(question.isAskingAdj){
			adapter.add(new OneComment(true, answer+question.questionPhrase+"! �y���y�o�����@!�n��!"));
		}else{
			adapter.add(new OneComment(true, question.questionPhrase+answer+"! �y���y�o�����@!�n��!"));
		}
		addDerivedQuestionFormAnwser(matchedAnswer);
	}

	@Override
	public void onConfused() {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true, answer+"! ���~�o�D�ڤ]�٨S�Q�쵪�שO!�ڭ̴��@�D�a"));
	    addRandomQuestion();
	}

	@Override
	public void onAsked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTeach() {
		// TODO Auto-generated method stub
		String teachDemo;
		if(question.isAskingAdj){
			teachDemo= ontologyData.getOneRandomNounForOneAdj(answer);//find noun as teach demo
			if(teachDemo!=null){
				adapter.add(new OneComment(true, answer+"�O�Φb..�Ҧp: "+answer+teachDemo));
			}else{
				wikiData.getWikiData(answer);
			};
		}else{
			teachDemo= ontologyData.getOneRandomAdjForOneNoun(answer);//find adj as teach demo
			if(teachDemo!=null){
				adapter.add(new OneComment(true, answer+"�O�Φb..�Ҧp: "+teachDemo+answer));
			}else{
				wikiData.getWikiData(answer);
			};
		}
	}
	
	private void addDerivedQuestionFormAnwser(String matchedKeyword){
		if(question.isAskingAdj){
			question.isAskingAdj=false;
			question.questionPhrase = matchedKeyword;
			adapter.add(new OneComment(true, question.questionPhrase+"______"));
		}else{
			question.isAskingAdj=true;
			question.questionPhrase = matchedKeyword;
			adapter.add(new OneComment(true, "______"+question.questionPhrase));
		}
	}
	
	private void addRandomQuestion() {
		
		if(getRandomInteger(0, 1) == 0 ? true : false){
			question.questionPhrase = ontologyData.getOneRandomNoun();
			question.isAskingAdj =true;
			adapter.add(new OneComment(true, "______��"+question.questionPhrase));
		}else{
			question.questionPhrase = ontologyData.getOneRandomAdj();
			question.isAskingAdj =false;
			adapter.add(new OneComment(true, question.questionPhrase+"______"));
		}
	}
	
	private static int getRandomInteger(int aStart, int aEnd) {
		Random random = new Random();
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		long range = (long) aEnd - (long) aStart + 1;
		long fraction = (long) (range * random.nextDouble());
		int randomNumber = (int) (fraction + aStart);
		return randomNumber;
	}
	
	private boolean IsMatchAnswerFail(){
		boolean isFail=true;
		for(int i=0;i<keywords.length;i++){	
			if (answer.indexOf(keywords[i]) != -1){ 
				isFail = false;
				matchedAnswer = keywords[i];
				break;
			}else{
				//do nothing
			}
		}
		return isFail;
	}
	
}	

package com.asus.dialogue;

import java.util.Random;
import com.asus.asyctask.AsyncTaskResponse;
import com.asus.bubbles.DiscussArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.data.OntologyData;
import com.asus.data.WikiData;

public class DialogueDispather{
	
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
		this.wikiData = WikiData.getInstance(); 
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
	
	public void onWrongAnwser() {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true, answer+"? 再想想看有沒有更好的詞"));
		onTeach();
	}

	public void onRightAnwser() {
		if(question.isAskingAdj){
			adapter.add(new OneComment(true, answer+question.questionPhrase+"! 造詞造得不錯哦!好棒!"));
		}else{
			adapter.add(new OneComment(true, question.questionPhrase+answer+"! 造詞造得不錯哦!好棒!"));
		}
		addDerivedQuestionFormAnwser(matchedAnswer);
	}

	public void onConfused() {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true, answer+"! 嗚嗚~這題我也還沒想到答案呢!我們換一題吧"));
	    addRandomQuestion();
	}

	public void onAsked() {
		// TODO Auto-generated method stub
		
	}

	public void onTeach() {
		// TODO Auto-generated method stub
		String teachDemo;
		if(question.isAskingAdj){
			teachDemo= ontologyData.getOneRandomNounForOneAdj(answer);//find noun as teach demo
			if(teachDemo!=null){
				adapter.add(new OneComment(true, answer+"是用在..例如: "+answer+teachDemo));
			}else{
				wikiData.searchWikiData(answer,new AsyncTaskResponse<String>() {
					
					@Override
					public void processFinish(String output) {
						DialogueDispather.this.adapter.add(new OneComment(true, output));
					}
				});
			};
		}else{
			teachDemo= ontologyData.getOneRandomAdjForOneNoun(answer);//find adj as teach demo
			if(teachDemo!=null){
				adapter.add(new OneComment(true, answer+"是用在..例如: "+teachDemo+answer));
			}else{
				wikiData.searchWikiData(answer,new AsyncTaskResponse<String>() {
					
					@Override
					public void processFinish(String output) {
						DialogueDispather.this.adapter.add(new OneComment(true, output));
					}
				});
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
			adapter.add(new OneComment(true, "______的"+question.questionPhrase));
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

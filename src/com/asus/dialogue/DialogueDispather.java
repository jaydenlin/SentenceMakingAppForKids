package com.asus.dialogue;

import com.asus.bubbles.DiscussArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.ontologyData.OntologyData;

public class DialogueDispather implements JudgeManager{
	
	public static DialogueDispather instance;
	private Question question;
	private String answer;
	private String[] keywords;
	private OntologyData ontologyData;
	private DiscussArrayAdapter adapter;
	
	public static DialogueDispather getInstance(Question question, String answer,DiscussArrayAdapter adapter){
		if(instance==null){
			instance = new DialogueDispather(question,answer,adapter);
		}
		return instance;
	}
	
	private DialogueDispather(Question question, String answer,DiscussArrayAdapter adapter){
		this.question = question;
		this.answer = answer;
		this.adapter = adapter;
		this.ontologyData=OntologyData.getInstance();
		
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
		if( IsMatchAnswerFail()){
			onWrongAnwser();
		}else{
			onRightAnwser();
		}
		
	}
	
	private void onAskingNoun(){
		keywords=ontologyData.getMatchedNounArrayForOneAdj(question.questionPhrase);
		if( IsMatchAnswerFail()){
			onWrongAnwser();
		}else{
			onRightAnwser();
		}
	}
	
	
	private boolean IsMatchAnswerFail(){
		boolean isFail=true;
		for(int i=0;i<keywords.length;i++){	
			if (answer.indexOf(keywords[i]) != -1){ 
				isFail = false;
				
				break;
			}else{
				//do nothing
			}
		}
		return isFail;
	}
	
	
	@Override
	public void onWrongAnwser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRightAnwser() {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true, answer+"! 造詞造得不錯哦!好棒!"));
		//addDerivedQuestionFormAnwser(keywords[i]);
	}

	@Override
	public void onConfused() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAsked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTeach() {
		// TODO Auto-generated method stub
		
	}
	
	private void addDerivedQuestionFormAnwser(String matchedKeyword){
		if(question.isAskingAdj){
			question.isAskingAdj=false;
			question.questionPhrase = matchedKeyword;
			adapter.add(new OneComment(true, question.questionPhrase+"______"));
		}else{
			question.isAskingAdj=true;
			question.questionPhrase = matchedKeyword;
			adapter.add(new OneComment(true, "______的"+question.questionPhrase));
		}
	}
	
	
}	

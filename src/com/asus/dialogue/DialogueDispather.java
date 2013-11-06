package com.asus.dialogue;

import com.asus.bubbles.DiscussArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.ontologyData.OntologyData;

public class DialogueDispather implements JudgeManager{
	
	public static DialogueDispather instance;
	private Question question;
	private String answer;
	private String matchedAnswer;
	private String[] keywords;
	private OntologyData ontologyData;
	private DiscussArrayAdapter adapter;
	
	public static DialogueDispather getInstance(Question question, String answer,DiscussArrayAdapter adapter){
		if(instance==null){
			instance = new DialogueDispather(question,answer,adapter);
		}else{
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
	
	
	@Override
	public void onWrongAnwser() {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true, answer+"? 再想想看有沒有更好的詞"));
		onTeach();
	}

	@Override
	public void onRightAnwser() {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true, answer+question.questionPhrase+"! 造詞造得不錯哦!好棒!"));
		addDerivedQuestionFormAnwser(matchedAnswer);
	}

	@Override
	public void onConfused() {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true, answer+"! 嗚嗚~這題我也還沒想到答案呢!我們換一題吧"));
			//addRandomQuestion();
	}

	@Override
	public void onAsked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTeach() {
		// TODO Auto-generated method stub
		if(question.isAskingAdj){
			String demo= ontologyData.getOneRandomNounForOneAdj(answer);
			if(demo!=null){
				adapter.add(new OneComment(true, answer+"是用在..例如: "+answer+demo));
			};
		}else{
			String demo= ontologyData.getOneRandomAdjForOneNoun(answer);
			if(demo!=null){
				adapter.add(new OneComment(true, answer+"是用在..例如: "+demo+answer));
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
			adapter.add(new OneComment(true, "______的"+question.questionPhrase));
		}
	}
	
	
}	

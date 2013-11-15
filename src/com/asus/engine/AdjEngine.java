package com.asus.engine;

import android.R.integer;
import android.util.Log;

import com.asus.data.DBHelper;
import com.asus.dialogue.Question;

public class AdjEngine extends JudgeEngine{
	
	public AdjEngine(Question question,String answer) {
		super(question,answer);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setKeywords() {
		// TODO Auto-generated method stub
		keywords = ontologyData.getMatchedAdjArrayForOneNoun(question.questionPhrase);
		if(keywords==null){
			Log.w(getClass().getSimpleName(),"setKeywords keywrods is null");
		}else{
			Log.w(getClass().getSimpleName(),"setKeywords keywrods not null");
		}
	}
	
	@Override
	public String getCurrentQuestion() {
		// TODO Auto-generated method stub
		return "試試看這個句子。   _______的"+question.questionPhrase;
	}
	
	@Override
	public String onRightResponse() {
		// TODO Auto-generated method stub
		return getRightAnswer()+question.questionPhrase+"! 造詞造得不錯哦!好棒!";
	}

	@Override
	public String onWrongResponse() {
		// TODO Auto-generated method stub
		
		return getWrongAnswer()+"? 再想想看有沒有更好的詞";
	}

	@Override
	public String onTeachResponse() {
		// TODO Auto-generated method stub
		teachString = ontologyData.getOneRandomNounForOneAdj(answer);//find a proper noun for the adj answer
		if(teachString.equals("")){
			Log.w(getClass().getSimpleName(), "teachString is empty");
			return "";
		}else{
			return answer+"應該用在...例如:"+answer+teachString;
		}
	}

	@Override
	public String onConfusedResponse() {
		// TODO Auto-generated method stub
		return "這一題形容詞的用法我也還沒有想到答案耶..來換一題吧";
	}

	@Override
	public String getNextQuestion() {
		// TODO Auto-generated method stub
		setNextQuestion();
		return "試試看這個句子。  "+ question.questionPhrase +"_______";
	}
	
	
	private void setNextQuestion(){
		//use user's answer to ask "noun" question
		String proposedQuestion=getRightAnswer();//answer is adj
		question.isAskingAdj=false;
		question.questionPhrase=proposedQuestion.equals("")?ontologyData.getOneRandomAdj():proposedQuestion;
	}
	@Override
	public int getTeachPhoto(){
		return ontologyData.getOnePhotoIdOfOneNoun(teachString);
	}

	public int[] getHintPhotos() {
		// TODO Auto-generated method stub
		int[] photoArrayId = new int[1];
		if(question.isAskingAdj==true){
			//asking adj so that put noun photo
			photoArrayId[0]=ontologyData.getOnePhotoIdOfOneNoun(question.questionPhrase);
		}else{
			//asking noun so that put adj photo
			photoArrayId=ontologyData.getPhotoIdsOfOneAdj(question.questionPhrase);
		}
		return photoArrayId;
	}
	

}

package com.asus.engine;

import android.util.Log;

import com.asus.data.DBHelper;
import com.asus.dialogue.Question;

public class NounEngine extends JudgeEngine {

	public NounEngine(Question question,String answer) {
		super(question,answer);
	}
	
	@Override
	protected void setKeywords() {
		keywords = ontologyData.getMatchedNounArrayForOneAdj(question.questionPhrase);
		if(keywords==null){
			Log.w(getClass().getSimpleName(),"setKeywords keywrods is null");
		}else{
			Log.w(getClass().getSimpleName(),"setKeywords keywrods not null");
		}
	}
	
	@Override
	public String getCurrentQuestion() {
		// TODO Auto-generated method stub
		return "試試看這個句子。  "+ question.questionPhrase +"_______";
	}
	
	@Override
	public String onRightResponse() {
		// TODO Auto-generated method stub
		return question.questionPhrase+getRightAnswer()+"! 造詞造得不錯哦!好棒!";
	}

	@Override
	public String onWrongResponse() {
		// TODO Auto-generated method stub
		return getWrongAnswer()+"? 再想想看有沒有更好的詞";
	}

	@Override
	public String onTeachResponse() {
		// TODO Auto-generated method stub
		teachString = ontologyData.getOneRandomAdjForOneNoun(answer);//find a proper adj for noun answer
		if(teachString.equals("")){
			Log.w(getClass().getSimpleName(),"teachString is empty");
			return "";
		}else{
			return answer+"應該用在...例如:"+teachString+answer;
		}
	}

	@Override
	public String onConfusedResponse() {
		// TODO Auto-generated method stub
		return "這一題名詞的用法我也還沒有想到答案耶..來換一題吧";
	}

	@Override
	public String getNextQuestion() {
		// TODO Auto-generated method stub
		setNextQuestion();
		return "試試看這個句子。   _______的"+question.questionPhrase;
	}

	private void setNextQuestion(){
		//use user's answer to ask "adj" question
		String proposedQuestion=getRightAnswer();//answer is noun
		question.isAskingAdj=true;
		question.questionPhrase=proposedQuestion.equals("")?ontologyData.getOneRandomNoun():proposedQuestion;
	}

	@Override
	public int getTeachPhoto(){
		return ontologyData.getOnePhotoIdOfOneNoun(answer);
	}

	public int[] getHintPhotos() {
		// TODO Auto-generated method stub
		int[] photoArrayId = new int[1];
		if(question.isAskingAdj==true){
			//asking adj so that put noun photo
			photoArrayId[0]=ontologyData.getOnePhotoIdOfOneNoun(question.questionPhrase);
			Log.w(getClass().getSimpleName(),question.questionPhrase);
		}else{
			//asking noun so that put adj photo
			photoArrayId=ontologyData.getPhotoIdsOfOneAdj(question.questionPhrase);
			Log.w(getClass().getSimpleName(),question.questionPhrase);
		}
		return photoArrayId;
	}
	
	

}

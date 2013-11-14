package com.asus.engine;

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
		return "�ոլݳo�ӥy�l�C   _______��"+question.questionPhrase;
	}
	
	@Override
	public String onRightResponse() {
		// TODO Auto-generated method stub
		return getRightAnswer()+question.questionPhrase+"! �y���y�o�����@!�n��!";
	}

	@Override
	public String onWrongResponse() {
		// TODO Auto-generated method stub
		
		return getWrongAnswer()+"? �A�Q�Q�ݦ��S����n����";
	}

	@Override
	public String onTeachResponse() {
		// TODO Auto-generated method stub
		String teachString = ontologyData.getOneRandomNounForOneAdj(answer);//find a proper noun for the adj answer
		if(teachString.equals("")){
			Log.w(getClass().getSimpleName(), "teachString is empty");
			return "";
		}else{
			return answer+"���ӥΦb...�Ҧp:"+answer+teachString;
		}
	}

	@Override
	public String onConfusedResponse() {
		// TODO Auto-generated method stub
		return "�o�@�D�ήe�����Ϊk�ڤ]�٨S���Q�쵪�׭C..�Ӵ��@�D�a";
	}

	@Override
	public String onNextQuestionResponse() {
		// TODO Auto-generated method stub
		setQuestion();
		return "�ոլݳo�ӥy�l�C  "+ question.questionPhrase +"_______";
	}
	
	
	
	private void setQuestion(){
		//use user's answer to ask "noun" question
		String proposedQuestion=getRightAnswer();//answer is adj
		question.isAskingAdj=false;
		question.questionPhrase=proposedQuestion.equals("")?ontologyData.getOneRandomAdj():proposedQuestion;
	}

	

}

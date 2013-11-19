package com.asus.engine;

import android.R.integer;
import android.util.Log;

import com.asus.data.DBHelper;
import com.asus.dialogue.Question;
import com.asus.util.RandomUtil;

public class AdjEngine extends JudgeEngine{
	
	public AdjEngine(Question question,String answer,JudgeEngineCallback judgeEngineCallback) {
		super(question,answer,judgeEngineCallback);
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
		teachString = ontologyData.getOneRandomNounForOneAdj(answer);//find a proper noun for the adj answer
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
		String[] confusedResponse={
				"���r..�u��p�X�D�X�����F..����"+question.questionPhrase+"����..",
				"���o�D�S����������...�J�M����"+question.questionPhrase+"����..",
				"���|"+question.questionPhrase+"��? �o�ˤl��...��..",
				"���F��"+question.questionPhrase+"��?�n�a,�J�M�o�˪���..",
				"�O��,�ݨӧA�٤��M��"+question.questionPhrase+"? �J�M�o�˪���,��...",
		};

		return confusedResponse[RandomUtil.getRandomInteger(0, confusedResponse.length-1)];
	}

	@Override
	public String getNextQuestion() {
		// TODO Auto-generated method stub
		setNextQuestion();
		return "�ոլݳo�ӥy�l�C  "+ question.questionPhrase +"_______";
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
			Log.w(getClass().getSimpleName(),question.questionPhrase);
		}else{
			//asking noun so that put adj photo
			photoArrayId=ontologyData.getPhotoIdsOfOneAdj(question.questionPhrase);
			Log.w(getClass().getSimpleName(),question.questionPhrase);
		}
		return photoArrayId;
	}
	

}

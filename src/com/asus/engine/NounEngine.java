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
		return "�ոլݳo�ӥy�l�C  "+ question.questionPhrase +"_______";
	}
	
	@Override
	public String onRightResponse() {
		// TODO Auto-generated method stub
		return question.questionPhrase+getRightAnswer()+"! �y���y�o�����@!�n��!";
	}

	@Override
	public String onWrongResponse() {
		// TODO Auto-generated method stub
		return getWrongAnswer()+"? �A�Q�Q�ݦ��S����n����";
	}

	@Override
	public String onTeachResponse() {
		// TODO Auto-generated method stub
		teachString = ontologyData.getOneRandomAdjForOneNoun(answer);//find a proper adj for noun answer
		if(teachString.equals("")){
			Log.w(getClass().getSimpleName(),"teachString is empty");
			return "";
		}else{
			return answer+"���ӥΦb...�Ҧp:"+teachString+answer;
		}
	}

	@Override
	public String onConfusedResponse() {
		// TODO Auto-generated method stub
		return "�o�@�D�W�����Ϊk�ڤ]�٨S���Q�쵪�׭C..�Ӵ��@�D�a";
	}

	@Override
	public String getNextQuestion() {
		// TODO Auto-generated method stub
		setNextQuestion();
		return "�ոլݳo�ӥy�l�C   _______��"+question.questionPhrase;
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
		}else{
			//asking noun so that put adj photo
			photoArrayId=ontologyData.getPhotoIdsOfOneAdj(question.questionPhrase);
		}
		return photoArrayId;
	}
	
	

}

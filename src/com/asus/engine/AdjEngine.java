package com.asus.engine;

import android.R.integer;
import android.util.Log;

import com.asus.data.DBHelper;
import com.asus.dialogue.Question;
import com.asus.exception.MatchedWordsNotFound;
import com.asus.exception.PhotoIdsNotFound;
import com.asus.exception.TeachStringResponseNotFound;
import com.asus.util.RandomUtil;

public class AdjEngine extends JudgeEngine{
	
	public AdjEngine(Question question,String answer,JudgeEngineCallback judgeEngineCallback) {
		super(question,answer,judgeEngineCallback);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setKeywords() throws MatchedWordsNotFound {
		// TODO Auto-generated method stub
		keywords = ontologyData.getMatchedAdjArrayForOneNoun(question.questionPhrase);
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
	public String onTeachResponse() throws TeachStringResponseNotFound {
		try {
			teachString = ontologyData.getOneRandomNounForOneAdj(getWrongAnswer());//find a proper noun for the adj answer
			return getWrongAnswer()+"���ӥΦb...�Ҧp:"+getWrongAnswer()+teachString;
		} catch (MatchedWordsNotFound e) {
			throw new TeachStringResponseNotFound("No teachString found for"+answer);
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
		
		String responseConfusedString;
		try {
			responseConfusedString = "�ڱЧA�a!"+question.questionPhrase+"�|�Φb..�Ҧp:"+ontologyData.getOneRandomAdjForOneNoun(question.questionPhrase)+question.questionPhrase;
		} catch (MatchedWordsNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			responseConfusedString="";
		}
		return confusedResponse[RandomUtil.getRandomInteger(0, confusedResponse.length-1)]+responseConfusedString;
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
	public int getTeachPhoto() throws PhotoIdsNotFound{
		return ontologyData.getOnePhotoIdOfOneNoun(teachString);
	}

	public int[] getHintPhotos() throws PhotoIdsNotFound {
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

	@Override
	public String onLeaveResponse() {
		// TODO Auto-generated method stub
		return "�o�˧r..���T�T�o~�ڭ̤U���A��!";
	}

	@Override
	public String onDirtyResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String onAskResponse() {
		String response="�@~�A�ݪ��O"+question.questionPhrase+"���y�y�r!";
		if(question.isAskingAdj){
			try {
				response=response+"�ڨӱЧA�a!�A�i�H�o�˥�..�Ҧp:"+
				ontologyData.getOneRandomAdjForOneNoun(question.questionPhrase)+question.questionPhrase+"�C���A�o~�A�յۥ�["+question.questionPhrase+"]�y�ӥy�l�a!";
				
			} catch (MatchedWordsNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				question.questionPhrase=ontologyData.getOneRandomNoun();//set question.questionPhrase
				response=response+"���~�A�ݱo�����F�A�ݧڧO����!���ڦҦҧA�a~�ոլݳo�ӥy�l�A____��"+question.questionPhrase;
			}
		}else{
			try {
				response=response+"�ڨӱЧA�a!�A�i�H�o�˥�..�Ҧp:"+
				question.questionPhrase+ontologyData.getOneRandomNounForOneAdj(question.questionPhrase)+"�C���A�o~�A�յۥ�["+question.questionPhrase+"]�y�ӥy�l�a!";
				
			} catch (MatchedWordsNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				question.questionPhrase=ontologyData.getOneRandomAdj();//set question.questionPhrase
				response=response+"���~�A�ݱo�����F�A�ݧڧO����!���ڦҦҧA�a~�ոլݳo�ӥy�l�A"+question.questionPhrase+"____";
			}
			
		}
		
		return response;
	}

	@Override
	public String onShitResponse() {
		// TODO Auto-generated method stub
		return "�n�̷��D�ػ��X���㪺�y�l..�A�n���㻡�X..�Ҧp:[�ƻ�]��"+question.questionPhrase;
	}

	

}

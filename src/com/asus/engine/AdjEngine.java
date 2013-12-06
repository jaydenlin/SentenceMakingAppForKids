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
	public String onTeachResponse() throws TeachStringResponseNotFound {
		try {
			teachString = ontologyData.getOneRandomNounForOneAdj(getWrongAnswer());//find a proper noun for the adj answer
			return getWrongAnswer()+"應該用在...例如:"+getWrongAnswer()+teachString;
		} catch (MatchedWordsNotFound e) {
			throw new TeachStringResponseNotFound("No teachString found for"+answer);
		}
		
	}

	@Override
	public String onConfusedResponse() {
		// TODO Auto-generated method stub
		String[] confusedResponse={
				"唉呀..真抱歉出題出太難了..不懂"+question.questionPhrase+"的話..",
				"其實這題沒有那麼難啦...既然不懂"+question.questionPhrase+"的話..",
				"不會"+question.questionPhrase+"嗎? 這樣子噢...那..",
				"不了解"+question.questionPhrase+"嗎?好吧,既然這樣的話..",
				"是噢,看來你還不清楚"+question.questionPhrase+"? 既然這樣的話,那...",
		};
		
		String responseConfusedString;
		try {
			responseConfusedString = "我教你吧!"+question.questionPhrase+"會用在..例如:"+ontologyData.getOneRandomAdjForOneNoun(question.questionPhrase)+question.questionPhrase;
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
		return "試試看這個句子。  "+ question.questionPhrase +"_______";
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
		return "這樣呀..那掰掰囉~我們下次再玩!";
	}

	@Override
	public String onDirtyResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String onAskResponse() {
		String response="哦~你問的是"+question.questionPhrase+"怎麼造句呀!";
		if(question.isAskingAdj){
			try {
				response=response+"我來教你吧!你可以這樣用..例如:"+
				ontologyData.getOneRandomAdjForOneNoun(question.questionPhrase)+question.questionPhrase+"。換你囉~你試著用["+question.questionPhrase+"]造個句子吧!";
				
			} catch (MatchedWordsNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				question.questionPhrase=ontologyData.getOneRandomNoun();//set question.questionPhrase
				response=response+"嗚嗚~你問得太難了，問我別的啦!換我考考你吧~試試看這個句子，____的"+question.questionPhrase;
			}
		}else{
			try {
				response=response+"我來教你吧!你可以這樣用..例如:"+
				question.questionPhrase+ontologyData.getOneRandomNounForOneAdj(question.questionPhrase)+"。換你囉~你試著用["+question.questionPhrase+"]造個句子吧!";
				
			} catch (MatchedWordsNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				question.questionPhrase=ontologyData.getOneRandomAdj();//set question.questionPhrase
				response=response+"嗚嗚~你問得太難了，問我別的啦!換我考考你吧~試試看這個句子，"+question.questionPhrase+"____";
			}
			
		}
		
		return response;
	}

	@Override
	public String onShitResponse() {
		// TODO Auto-generated method stub
		return "要依照題目說出完整的句子..你要完整說出..例如:[甚麼]的"+question.questionPhrase;
	}

	

}

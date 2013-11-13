package com.asus.engine;

import android.util.Log;

import com.asus.dialogue.Question;

public class AdjEngine extends JudgeEngine{
	
	public AdjEngine(Question question,String answer) {
		super(question,answer);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setKeywords() {
		// TODO Auto-generated method stub
		keywords = ontologyData.getMatchedNounArrayForOneAdj(question.questionPhrase);
	}
	
	@Override
	public String onWikiDataGet(String output) {
		// TODO Auto-generated method stub
		return null;
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
		try{
			String teachString = ontologyData.getOneRandomNounForOneAdj(answer);//find a proper noun for the adj answer
			return answer+"���ӥΦb...�Ҧp:"+answer+teachString;//if null??
		}catch(Exception exception){
			searchWikiData(answer);
			Log.w(getClass().getSimpleName(), "teachString is empty");
			return "";
		}
	}

	@Override
	public String onConfusedResponse() {
		// TODO Auto-generated method stub
		return "�o�@�D�ήe�����Ϊk�ڤ]�٨S���Q�쵪�׭C..�Ӵ��@�D�a";
	}

}

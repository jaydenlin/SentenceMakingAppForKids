package com.asus.engine;

import android.util.Log;

import com.asus.dialogue.Question;

public class NounEngine extends JudgeEngine {

	public NounEngine(Question question,String answer) {
		super(question,answer);
	}
	
	@Override
	protected void setKeywords() {
		keywords = ontologyData
				.getMatchedAdjArrayForOneNoun(question.questionPhrase);
	}
	
	@Override
	public String onWikiDataGet(String output) {
		// TODO Auto-generated method stub
		return null;
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
		try{
			String teachString = ontologyData.getOneRandomAdjForOneNoun(answer);//find a proper adj for noun answer
			return answer+"���ӥΦb...�Ҧp:"+teachString+answer;//if null??
		}catch(Exception exception){
			Log.w(getClass().getSimpleName(),"teachString is empty");
			searchWikiData(answer);
			return "";
		}
	}

	@Override
	public String onConfusedResponse() {
		// TODO Auto-generated method stub
		return "�o�@�D�W�����Ϊk�ڤ]�٨S���Q�쵪�׭C..�Ӵ��@�D�a";
	}
	

	
	
	

}

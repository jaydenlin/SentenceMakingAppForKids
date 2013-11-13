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
		try{
			String teachString = ontologyData.getOneRandomAdjForOneNoun(answer);//find a proper adj for noun answer
			return answer+"應該用在...例如:"+teachString+answer;//if null??
		}catch(Exception exception){
			Log.w(getClass().getSimpleName(),"teachString is empty");
			searchWikiData(answer);
			return "";
		}
	}

	@Override
	public String onConfusedResponse() {
		// TODO Auto-generated method stub
		return "這一題名詞的用法我也還沒有想到答案耶..來換一題吧";
	}
	

	
	
	

}

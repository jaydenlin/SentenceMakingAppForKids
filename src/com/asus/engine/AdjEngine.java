package com.asus.engine;

import com.asus.dialogue.Question;

public class AdjEngine extends JudgeEngine{
	
	public AdjEngine(Question question,String answer) {
		super(question,answer);
		// TODO Auto-generated constructor stub
		
	}
	
	@Override
	public void setKeywords() {
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
		String teachString = ontologyData.getOneRandomNounForOneAdj(answer);
		return answer+"應該用在...例如:"+teachString+answer;//if null??
	}

	@Override
	public String onConfusedResponse() {
		// TODO Auto-generated method stub
		return "這一題形容詞的用法我也還沒有想到答案耶..來換一題吧";
	}

	

	

}

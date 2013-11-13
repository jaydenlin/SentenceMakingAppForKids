package com.asus.engine;

import android.util.Log;
import com.asus.dialogue.Question;

public class NounEngine extends JudgeEngine {

	public NounEngine(Question question,String answer) {
		super(question,answer);
	}
	
	@Override
	protected void setKeywords() {
		keywords = ontologyData.getMatchedNounArrayForOneAdj(question.questionPhrase);
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
		String teachString = ontologyData.getOneRandomAdjForOneNoun(answer);//find a proper adj for noun answer
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
	public String onNextQuestionResponse() {
		// TODO Auto-generated method stub
		setQuestion();
		return "試試看這個句子。   _______的"+question.questionPhrase;
	}

	private void setQuestion(){
		//use user's answer to ask "adj" question
		String proposedQuestion=getRightAnswer();//answer is noun
		question.isAskingAdj=true;
		question.questionPhrase=proposedQuestion.equals("")?ontologyData.getOneRandomNoun():proposedQuestion;
	}

	
	
	

}

package com.asus.dialogue;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.text.Html.TagHandler;

import com.asus.bubbles.DiscussArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.data.OntologyData;

public class DialogueDispather implements JudgeManager{
	
	public static DialogueDispather instance;
	private Question question;
	private String answer;
	private String matchedAnswer;
	private String[] keywords;
	private OntologyData ontologyData;
	private DiscussArrayAdapter adapter;
	
	public static DialogueDispather getInstance(Question question, String answer,DiscussArrayAdapter adapter){
		if(instance==null){
			instance = new DialogueDispather(question,answer,adapter);
		}else{
			instance.question = question;
			instance.answer = answer;
		}
		return instance;
	}
	
	private DialogueDispather(Question question, String answer,DiscussArrayAdapter adapter){
		this.question = question;
		this.answer = answer;
		this.adapter = adapter;
		this.ontologyData=OntologyData.getInstance();
	}
	
	public void start(){
		if(question.isAskingAdj){
			onAskingAdj();
		}else{
			onAskingNoun();
		}
	}
	
	private void onAskingAdj(){
		keywords=ontologyData.getMatchedAdjArrayForOneNoun(question.questionPhrase);
		if(keywords==null){
			onConfused();
		}
		else if(IsMatchAnswerFail()){
			onWrongAnwser();
		}else{
			onRightAnwser();
		}
		
	}
	
	private void onAskingNoun(){
		keywords=ontologyData.getMatchedNounArrayForOneAdj(question.questionPhrase);
		if(keywords==null){
			onConfused();
		}
		else if(IsMatchAnswerFail()){
			onWrongAnwser();
		}else{
			onRightAnwser();
		}
	}
	
	@Override
	public void onWrongAnwser() {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true, answer+"? 再想想看有沒有更好的詞"));
		onTeach();
	}

	@Override
	public void onRightAnwser() {
		// TODO Auto-generated method stub
		if(question.isAskingAdj){
			adapter.add(new OneComment(true, answer+question.questionPhrase+"! 造詞造得不錯哦!好棒!"));
		}else{
			adapter.add(new OneComment(true, question.questionPhrase+answer+"! 造詞造得不錯哦!好棒!"));
		}
		addDerivedQuestionFormAnwser(matchedAnswer);
	}

	@Override
	public void onConfused() {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true, answer+"! 嗚嗚~這題我也還沒想到答案呢!我們換一題吧"));
			//addRandomQuestion();
	}

	@Override
	public void onAsked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTeach() {
		// TODO Auto-generated method stub
		String teachDemo;
		if(question.isAskingAdj){
			teachDemo= ontologyData.getOneRandomNounForOneAdj(answer);//find noun as teach demo
			if(teachDemo!=null){
				adapter.add(new OneComment(true, answer+"是用在..例如: "+answer+teachDemo));
			};
		}else{
			teachDemo= ontologyData.getOneRandomAdjForOneNoun(answer);//find adj as teach demo
			if(teachDemo!=null){
				adapter.add(new OneComment(true, answer+"是用在..例如: "+teachDemo+answer));
			};
		}
		
//		HttpClient client = new DefaultHttpClient();
//		HttpGet get = new HttpGet("http://zh.wikipedia.org/wiki/%E9%A6%AC%E8%8B%B1%E4%B9%9D");
//		try {
//			HttpResponse response = client.execute(get);
//			HttpEntity resEntity = response.getEntity();
//			String result = EntityUtils.toString(resEntity);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	}
	
	private void addDerivedQuestionFormAnwser(String matchedKeyword){
		if(question.isAskingAdj){
			question.isAskingAdj=false;
			question.questionPhrase = matchedKeyword;
			adapter.add(new OneComment(true, question.questionPhrase+"的______"));
		}else{
			question.isAskingAdj=true;
			question.questionPhrase = matchedKeyword;
			adapter.add(new OneComment(true, "______的"+question.questionPhrase));
		}
	}
	
	private boolean IsMatchAnswerFail(){
		boolean isFail=true;
		for(int i=0;i<keywords.length;i++){	
			if (answer.indexOf(keywords[i]) != -1){ 
				isFail = false;
				matchedAnswer = keywords[i];
				break;
			}else{
				//do nothing
			}
		}
		return isFail;
	}
	
}	

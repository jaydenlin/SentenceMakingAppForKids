package com.asus.dialogue;

import android.util.Log;

import com.asus.bubbles.BubblesArrayAdapter;
import com.asus.engine.AdjEngine;
import com.asus.engine.JudgeEngine;
import com.asus.engine.NounEngine;

public class InputDispatcher {
	
	Question question;
	String answer;
	JudgeEngine judgeEngine;
	DialogueHandler dialogueHandler;
	BubblesArrayAdapter adapter;
	
	public static InputDispatcher instance;
	public static InputDispatcher getInstance(Question question,String answer,BubblesArrayAdapter adapter){
		
		if(instance==null){
			instance=new InputDispatcher(question,answer,adapter);
		}else{
			//update question and answer when receive user input
			instance.question=question;
			instance.answer=answer;
		}
		
		return instance;
	}
	
	private InputDispatcher(Question question,String answer,BubblesArrayAdapter adapter) {
		// TODO Auto-generated constructor stub
		this.question=question;
		this.answer=answer;
		this.adapter=adapter;
	}
	
	public void start(){
		setJudgeEngine();
		setDialogueHandler(judgeEngine);
		
		dialogueHandler.putResponseFrom(judgeEngine);
		
		if(judgeEngine.toTeachFlag){
			dialogueHandler=new TeachDialogueHandler(adapter);
			dialogueHandler.putResponseFrom(judgeEngine);
		}
		
		dialogueHandler.putQuestionFrom(judgeEngine);
		
	}
	
	private void setJudgeEngine(){
		if(question.isAskingAdj){
			judgeEngine= new AdjEngine(question, answer);
		}else{
			judgeEngine=new NounEngine(question, answer);
		}
	}
	
	private void setDialogueHandler(JudgeEngine judgeEngine){
		try{
			if(judgeEngine.IsConfused()){
				dialogueHandler=new ConfusedDialogueHandler(adapter);
			}else if(judgeEngine.IsRight()){
				dialogueHandler=new RightDialogueHandler(adapter);
			}else{
				judgeEngine.toTeachFlag=true;
				dialogueHandler=new WrongDialogueHandler(adapter);
			}
		}catch(NullPointerException exception){
			Log.w(getClass().getSimpleName(), "judgeEngine must be set up in advance. please call setJudgeEngine() to set it up.");
		}
	}
	
}

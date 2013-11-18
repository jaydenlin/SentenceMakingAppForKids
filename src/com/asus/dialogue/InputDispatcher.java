package com.asus.dialogue;

import android.util.Log;

import com.asus.bubbles.BubblesArrayAdapter;
import com.asus.data.DBHelper;
import com.asus.engine.AdjEngine;
import com.asus.engine.JudgeEngine;
import com.asus.engine.NounEngine;
import com.asus.photos.PhotosArrayAdapter;
import com.asus.scene.ConfusedSceneHandler;
import com.asus.scene.RightSceneHandler;
import com.asus.scene.SceneHandler;
import com.asus.scene.TeachSceneHandler;
import com.asus.scene.WrongSceneHandler;

public class InputDispatcher {
	
	Question question;
	String answer;
	JudgeEngine judgeEngine;
	DialogueHandler dialogueHandler;
	SceneHandler sceneHandler;
	BubblesArrayAdapter adapter;
	PhotosArrayAdapter photosArrayAdapter;
	public static InputDispatcher instance;
	public static InputDispatcher getInstance(Question question,String answer,BubblesArrayAdapter adapter,PhotosArrayAdapter photosArrayAdapter){
		
		if(instance==null){
			instance=new InputDispatcher(question,answer,adapter,photosArrayAdapter);
		}else{
			//update question and answer when receive user input
			instance.question=question;
			instance.answer=answer;
		}
		
		return instance;
	}
	
	private InputDispatcher(Question question,String answer,BubblesArrayAdapter adapter,PhotosArrayAdapter photosArrayAdapter) {
		// TODO Auto-generated constructor stub
		this.question=question;
		this.answer=answer;
		this.adapter=adapter;
		this.photosArrayAdapter=photosArrayAdapter;
	}
	
	public void start(){
		setJudgeEngine();
		setHandler(judgeEngine);
		
		dialogueHandler.putResponseFrom(judgeEngine);
		dialogueHandler.putQuestionFrom(judgeEngine);
		sceneHandler.putHintPhotoFrom(judgeEngine);
		
	}
	
	private void setJudgeEngine(){
		if(question.isAskingAdj){
			judgeEngine= new AdjEngine(question, answer);
		}else{
			judgeEngine=new NounEngine(question, answer);
		}
	}
	
	private void setHandler(JudgeEngine judgeEngine){
		try{
			
			if(judgeEngine.IsConfused()){
				dialogueHandler=new ConfusedDialogueHandler(adapter);
				sceneHandler = new ConfusedSceneHandler(photosArrayAdapter);
			}else if(judgeEngine.IsRight()){
				dialogueHandler=new RightDialogueHandler(adapter);
				sceneHandler = new RightSceneHandler(photosArrayAdapter);
			}else{
				dialogueHandler=new WrongDialogueHandler(adapter);
				sceneHandler=new WrongSceneHandler(photosArrayAdapter);
			}
		}catch(NullPointerException exception){
			Log.w(getClass().getSimpleName(), "judgeEngine must be set up in advance. please call setJudgeEngine() to set it up.");
		}
	}
	
}

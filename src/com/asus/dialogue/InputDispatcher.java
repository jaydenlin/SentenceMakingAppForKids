package com.asus.dialogue;

import android.util.Log;

import com.asus.agent.Agent;
import com.asus.bubbles.BubblesArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.data.DBHelper;
import com.asus.engine.AdjEngine;
import com.asus.engine.JudgeEngine;
import com.asus.engine.JudgeEngineCallback;
import com.asus.engine.NounEngine;
import com.asus.photos.OnePhoto;
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
	JudgeEngineCallback judgeEngineCallback;
	Agent agent;
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
		judgeEngine.start();

//		dialogueHandler.putResponseFrom(judgeEngine);
//		dialogueHandler.putQuestionFrom(judgeEngine);
//		sceneHandler.putHintPhotoFrom(judgeEngine);
		
	}
	
	
	private void setJudgeEngine(){
		agent=new Agent();
		judgeEngineCallback=new JudgeEngineCallback() {
			
			@Override
			public void onConfused() {
				// TODO Auto-generated method stub
				agent.addHandler(new ConfusedDialogueHandler(adapter));
				agent.addHandler(new ConfusedSceneHandler(photosArrayAdapter));
				agent.execHandler(judgeEngine);
//				dialogueHandler=new ConfusedDialogueHandler(adapter);
//				sceneHandler = new ConfusedSceneHandler(photosArrayAdapter);
				
//				dialogueHandler.putResponseFrom(judgeEngine);
//				dialogueHandler.putQuestionFrom(judgeEngine);
//				sceneHandler.putHintPhotoFrom(judgeEngine);
			}

			@Override
			public void onRight() {
				// TODO Auto-generated method stub
				agent.addHandler(new RightDialogueHandler(adapter));
				agent.addHandler(new RightSceneHandler(photosArrayAdapter));
				agent.execHandler(judgeEngine);
				
//				dialogueHandler=new RightDialogueHandler(adapter);
//				sceneHandler = new RightSceneHandler(photosArrayAdapter);
				
//				dialogueHandler.putResponseFrom(judgeEngine);
//				dialogueHandler.putQuestionFrom(judgeEngine);
//				sceneHandler.putHintPhotoFrom(judgeEngine);
			}
			
			@Override
			public void onWrong() {
				// TODO Auto-generated method stub
				agent.addHandler(new WrongDialogueHandler(adapter));
				agent.addHandler(new WrongSceneHandler(photosArrayAdapter));
				agent.execHandler(judgeEngine);
//				dialogueHandler=new WrongDialogueHandler(adapter);
//				sceneHandler=new WrongSceneHandler(photosArrayAdapter);
				
//				dialogueHandler.putResponseFrom(judgeEngine);
//				dialogueHandler.putQuestionFrom(judgeEngine);
//				sceneHandler.putHintPhotoFrom(judgeEngine);
			}			
		};
		
		if(question.isAskingAdj){
			judgeEngine= new AdjEngine(question, answer,judgeEngineCallback);
		}else{
			judgeEngine=new NounEngine(question, answer,judgeEngineCallback);
		}
	}
	
//	private void setHandler(JudgeEngine judgeEngine){
//		try{
//			
//			if(judgeEngine.IsConfused()){
//				dialogueHandler=new ConfusedDialogueHandler(adapter);
//				sceneHandler = new ConfusedSceneHandler(photosArrayAdapter);
//			}else if(judgeEngine.IsRight()){
//				dialogueHandler=new RightDialogueHandler(adapter);
//				sceneHandler = new RightSceneHandler(photosArrayAdapter);
//			}else{
//				dialogueHandler=new WrongDialogueHandler(adapter);
//				sceneHandler=new WrongSceneHandler(photosArrayAdapter);
//			}
//		}catch(NullPointerException exception){
//			Log.w(getClass().getSimpleName(), "judgeEngine must be set up in advance. please call setJudgeEngine() to set it up.");
//		}
//	}
	
}

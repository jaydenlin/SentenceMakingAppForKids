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
import com.asus.scene.AskSceneHandler;
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
		
	}
	
	
	private void setJudgeEngine(){
		agent=new Agent();
		judgeEngineCallback=new JudgeEngineCallback() {
			
			@Override
			public void onAsk() {
				// TODO Auto-generated method stub
				agent.addHandler(new AskDialogueHandler(adapter));
				agent.addHandler(new AskSceneHandler(photosArrayAdapter));
				agent.execHandler(judgeEngine);
			}			
			@Override
			public void onConfused() {
				// TODO Auto-generated method stub
				agent.addHandler(new ConfusedDialogueHandler(adapter));
				agent.addHandler(new ConfusedSceneHandler(photosArrayAdapter));
				agent.execHandler(judgeEngine);
			}
			@Override
			public void onShit() {
				// TODO Auto-generated method stub
				agent.addHandler(new ShitDialogueHandler(adapter));
				agent.execHandler(judgeEngine);
			}
			@Override
			public void onRight() {
				// TODO Auto-generated method stub
				agent.addHandler(new RightDialogueHandler(adapter));
				agent.addHandler(new RightSceneHandler(photosArrayAdapter));
				agent.execHandler(judgeEngine);
				
			}
			
			@Override
			public void onWrong() {
				// TODO Auto-generated method stub
				agent.addHandler(new WrongDialogueHandler(adapter));
				agent.addHandler(new WrongSceneHandler(photosArrayAdapter));
				agent.execHandler(judgeEngine);
			}
			

			
		};
		
		if(question.isAskingAdj){
			judgeEngine= new AdjEngine(question, answer,judgeEngineCallback);
		}else{
			judgeEngine=new NounEngine(question, answer,judgeEngineCallback);
		}
	}
	
	
}

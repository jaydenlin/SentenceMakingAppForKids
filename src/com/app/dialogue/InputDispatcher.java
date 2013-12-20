package com.app.dialogue;

import android.util.Log;

import com.app.agent.Agent;
import com.app.bubbles.BubblesArrayAdapter;
import com.app.bubbles.OneComment;
import com.app.data.DBHelper;
import com.app.engine.AdjEngine;
import com.app.engine.JudgeEngine;
import com.app.engine.JudgeEngineCallback;
import com.app.engine.NounEngine;
import com.app.photos.OnePhoto;
import com.app.photos.PhotosArrayAdapter;
import com.app.scene.AskSceneHandler;
import com.app.scene.ConfusedSceneHandler;
import com.app.scene.RightSceneHandler;
import com.app.scene.SceneHandler;
import com.app.scene.TeachSceneHandler;
import com.app.scene.WrongSceneHandler;

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
			public void onLeave() {
				// TODO Auto-generated method stub
				agent.addHandler(new LeaveDialogueHandler(adapter));
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
			@Override
			public void onConfirm() {
				// TODO Auto-generated method stub
				agent.addHandler(new ConfirmDialogueHandler(adapter));
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

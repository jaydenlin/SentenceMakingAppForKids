package com.app.dialogue;

import com.app.bubbles.BubblesArrayAdapter;
import com.app.bubbles.OneComment;
import com.app.engine.JudgeEngine;

public class RightDialogueHandler extends DialogueHandler{
	
	
	public RightDialogueHandler(BubblesArrayAdapter adapter) {
		super(adapter);
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public void putResponseFrom(JudgeEngine engine) {
//		// TODO Auto-generated method stub
//		adapter.add(new OneComment(true, engine.onRightResponse()));
//		adapter.add(new OneComment(true, "哦～!我想你是指.."+engine.getDemoSentences().get(0)));
//	}
//
//	@Override
//	public void putQuestionFrom(JudgeEngine engine) {
//		// TODO Auto-generated method stub
//		adapter.add(new OneComment(true, engine.getNextQuestion()));
//	}

	@Override
	public void update(JudgeEngine engine) {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true, engine.onRightResponse()));
		if(engine.getDemoSentences().size()>0){
			adapter.add(new OneComment(true, "哦～不錯哦!我想你是指.."+engine.getDemoSentences().get(0)));
		}
		adapter.add(new OneComment(true, engine.getNextQuestion()));
		
		notifyDoneCallback.doNextHandler(engine);
	}

	
	

}

package com.app.dialogue;

import com.app.bubbles.BubblesArrayAdapter;
import com.app.bubbles.OneComment;
import com.app.engine.JudgeEngine;

public class WrongDialogueHandler extends DialogueHandler{

	public WrongDialogueHandler(BubblesArrayAdapter discussArrayAdapter) {
		super(discussArrayAdapter);
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public void putResponseFrom(JudgeEngine engine) {
//		// TODO Auto-generated method stub
//		adapter.add(new OneComment(true, engine.onWrongResponse()));
//		
//		//do teach things
//		TeachDialogueHandler tachDialogueHandler=new TeachDialogueHandler(adapter);
//		tachDialogueHandler.putResponseFrom(engine);
//	}
//
//	@Override
//	public void putQuestionFrom(JudgeEngine engine) {
//		// TODO Auto-generated method stub
//		adapter.add(new OneComment(true,engine.getCurrentQuestion()));
//	}
	
	@Override
	public void update(JudgeEngine engine) {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true, engine.onWrongResponse()));
		
		//do teach things
		TeachDialogueHandler tachDialogueHandler=new TeachDialogueHandler(adapter);
		tachDialogueHandler.update(engine);
		notifyDoneCallback.doNextHandler(engine);
	}

}

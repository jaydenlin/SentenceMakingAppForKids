package com.asus.dialogue;

import com.asus.bubbles.BubblesArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.engine.JudgeEngine;

public class ConfusedDialogueHandler extends DialogueHandler{

	public ConfusedDialogueHandler(BubblesArrayAdapter adapter) {
		super(adapter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void putResponseFrom(JudgeEngine engine) {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true, engine.onConfusedResponse()));
	}

	@Override
	public void putQuestionFrom(JudgeEngine engine) {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true,engine.getNextQuestion()));
		
	}

}

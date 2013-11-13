package com.asus.dialogue;

import com.asus.bubbles.DiscussArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.engine.JudgeEngine;

public class WrongDialogueHandler extends DialogueHandler{

	public WrongDialogueHandler(DiscussArrayAdapter discussArrayAdapter) {
		super(discussArrayAdapter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void putResponseFrom(JudgeEngine engine) {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true, engine.onWrongResponse()));
	}

	@Override
	public void putQuestionFrom(JudgeEngine engine) {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true,engine.getCurrentQuestion()));
	}


}

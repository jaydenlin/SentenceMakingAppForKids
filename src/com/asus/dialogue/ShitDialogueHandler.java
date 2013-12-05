package com.asus.dialogue;

import com.asus.bubbles.BubblesArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.engine.JudgeEngine;

public class ShitDialogueHandler extends DialogueHandler{

	public ShitDialogueHandler(BubblesArrayAdapter adapter) {
		super(adapter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(JudgeEngine judgeEngine) {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true, judgeEngine.onShitResponse()));
	}


}

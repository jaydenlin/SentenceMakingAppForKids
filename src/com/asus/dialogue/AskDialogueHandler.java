package com.asus.dialogue;

import com.asus.bubbles.BubblesArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.engine.JudgeEngine;

public class AskDialogueHandler extends DialogueHandler{
	
	public AskDialogueHandler(BubblesArrayAdapter adapter) {
		super(adapter);
	}

	@Override
	public void update(JudgeEngine judgeEngine) {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true, judgeEngine.onAskResponse()));
		notifyDoneCallback.doNextHandler(judgeEngine);
	}

	

}

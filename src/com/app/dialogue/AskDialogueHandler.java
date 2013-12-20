package com.app.dialogue;

import com.app.bubbles.BubblesArrayAdapter;
import com.app.bubbles.OneComment;
import com.app.engine.JudgeEngine;

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

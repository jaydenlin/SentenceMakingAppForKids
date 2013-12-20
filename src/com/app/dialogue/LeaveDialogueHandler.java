package com.app.dialogue;

import com.app.bubbles.BubblesArrayAdapter;
import com.app.bubbles.OneComment;
import com.app.engine.JudgeEngine;

public class LeaveDialogueHandler extends DialogueHandler{

	public LeaveDialogueHandler(BubblesArrayAdapter adapter) {
		super(adapter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(JudgeEngine judgeEngine) {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true, judgeEngine.onLeaveResponse()));
	}


}

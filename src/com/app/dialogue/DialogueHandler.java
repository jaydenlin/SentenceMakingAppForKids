package com.app.dialogue;

import com.app.agent.AgentObserver;
import com.app.bubbles.BubblesArrayAdapter;
import com.app.engine.JudgeEngine;

public abstract class DialogueHandler extends AgentObserver{
	
	BubblesArrayAdapter adapter;
	public DialogueHandler(BubblesArrayAdapter adapter) {
		// TODO Auto-generated constructor stub
		this.adapter=adapter;
	}
	
//	public abstract void putResponseFrom(JudgeEngine engine);
//	public abstract void putQuestionFrom(JudgeEngine engine);
}

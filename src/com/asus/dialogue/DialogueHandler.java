package com.asus.dialogue;

import com.asus.agent.AgentObserver;
import com.asus.bubbles.BubblesArrayAdapter;
import com.asus.engine.JudgeEngine;

public abstract class DialogueHandler implements AgentObserver{
	
	BubblesArrayAdapter adapter;
	public DialogueHandler(BubblesArrayAdapter adapter) {
		// TODO Auto-generated constructor stub
		this.adapter=adapter;
	}
	
	public abstract void putResponseFrom(JudgeEngine engine);
	public abstract void putQuestionFrom(JudgeEngine engine);
}

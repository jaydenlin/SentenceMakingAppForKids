package com.asus.dialogue;

import com.asus.bubbles.DiscussArrayAdapter;
import com.asus.engine.JudgeEngine;

public abstract class DialogueHandler {
	
	DiscussArrayAdapter adapter;
	public DialogueHandler(DiscussArrayAdapter adapter) {
		// TODO Auto-generated constructor stub
		this.adapter=adapter;
	}
	
	public abstract void putResponseFrom(JudgeEngine engine);
	public abstract void putQuestionFrom(JudgeEngine engine);
}

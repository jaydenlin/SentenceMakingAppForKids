package com.asus.dialogue;

import android.util.Log;

import com.asus.asyctask.AsyncTaskResponse;
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
		
		
		engine.searchConceptNet(new AsyncTaskResponse<String>() {
			
			@Override
			public void processFinish(String output) {
				// TODO Auto-generated method stub
				Log.w(getClass().getSimpleName(), output);
				adapter.add(new OneComment(true, output));
			}
		});
		
		adapter.add(new OneComment(true, engine.onConfusedResponse()));
		
	}

	@Override
	public void putQuestionFrom(JudgeEngine engine) {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true,engine.getNextQuestion()));
		
	}

}

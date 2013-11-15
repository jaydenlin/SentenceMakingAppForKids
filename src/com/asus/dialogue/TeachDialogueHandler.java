package com.asus.dialogue;

import com.asus.asyctask.AsyncTaskResponse;
import com.asus.bubbles.BubblesArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.engine.JudgeEngine;

public class TeachDialogueHandler extends DialogueHandler{
	
	private String wrongAnswer;
	
	public TeachDialogueHandler(BubblesArrayAdapter adapter) {
		super(adapter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void putResponseFrom(JudgeEngine engine) {
		// TODO Auto-generated method stub
		wrongAnswer=engine.getWrongAnswer();
		if(engine.onTeachResponse().equals("")){
			
			engine.searchWikiData(engine.getWrongAnswer(), new AsyncTaskResponse<String>() {
				@Override
				public void processFinish(String output) {
					// TODO Auto-generated method stub
					if(!output.trim().equals("")){
						adapter.add(new OneComment(true, wrongAnswer+"的意思應該是..."+output));
					}
				}
			});
			
		}else{
			adapter.add(new OneComment(true, engine.onTeachResponse()));
		}
		
	}

	@Override
	public void putQuestionFrom(JudgeEngine engine) {
		// TODO Auto-generated method stub
		adapter.add(new OneComment(true, engine.getCurrentQuestion()));
	}

}

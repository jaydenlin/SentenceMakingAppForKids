package com.asus.dialogue;

import java.util.List;

import android.util.Log;

import com.asus.asyctask.AsyncTaskResponse;
import com.asus.bubbles.BubblesArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.engine.JudgeEngine;
import com.asus.util.RandomUtil;

public class ConfusedDialogueHandler extends DialogueHandler{
	
	
	String[] openingResponse={
			"至於剛才那題,讓我說明一下那個詞的意思吧..例如:",
			"別洩氣哦!我補充一下剛才那題的詞,它會用在..例如:",
			"但是我還是要教你一下,剛才那題的詞的意思..例如:",
			"關於剛才那題,我還是要多嘴一下,仔細聽囉..那個詞有時候你會這樣用..例如:",
	};
	
	JudgeEngine engineForInnerClassEngineToUse;
	
	public ConfusedDialogueHandler(BubblesArrayAdapter adapter) {
		super(adapter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void putResponseFrom(JudgeEngine engine) {
		// TODO Auto-generated method stub
		engineForInnerClassEngineToUse=engine;
		
		adapter.add(new OneComment(true, engine.onConfusedResponse()));
		engine.searchConceptNet(new AsyncTaskResponse<List<String>>() {
			
			@Override
			public void processFinish(List<String> output) {
				// TODO Auto-generated method stub
				if(output.size()>0){
					adapter.add(new OneComment(true, 
						openingResponse[RandomUtil.getRandomInteger(0, openingResponse.length-1)]
						+output.get(RandomUtil.getRandomInteger(0, output.size()-1))
						));
				}
			}
			
		});
		
		
	}

	@Override
	public void putQuestionFrom(JudgeEngine engine) {
		//Move put question to putResponseFrom function
		adapter.add(new OneComment(true, "我們換一題吧!"+engine.getNextQuestion()));
	}

	@Override
	public void update(JudgeEngine judgeEngine) {
		// TODO Auto-generated method stub
		
	}

}

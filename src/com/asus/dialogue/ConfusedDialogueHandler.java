package com.asus.dialogue;

import java.util.List;

import android.util.Log;

import com.asus.agent.NotifyCallback;
import com.asus.asyctask.AsyncTaskResponse;
import com.asus.bubbles.BubblesArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.engine.JudgeEngine;
import com.asus.util.RandomUtil;

public class ConfusedDialogueHandler extends DialogueHandler{
	
	
	String[] openingResponse={
			"�άO�o�˥�..�Ҧp:",
			"�]�i�H�o�˥�..�Ҧp:",
			"�t�~,�o�ӵ����]�|�Φb..�Ҧp:",
			"�٦��r..�o�ӵ����ɭԧA�|�o�˥�..�Ҧp:",
	};
	
	JudgeEngine engineForInnerClassEngineToUse;
	
	public ConfusedDialogueHandler(BubblesArrayAdapter adapter) {
		super(adapter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(JudgeEngine engine) {
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
					adapter.add(new OneComment(true, "�ڭ̴��@�D�a!"+engineForInnerClassEngineToUse.getNextQuestion()));
					notifyDoneCallback.doNextHandler(engineForInnerClassEngineToUse);
				}else{
					adapter.add(new OneComment(true, "�ڭ̴��@�D�a!"+engineForInnerClassEngineToUse.getNextQuestion()));
					notifyDoneCallback.doNextHandler(engineForInnerClassEngineToUse);
				}
			}
			
		});
		
//		@Override
//		public void putResponseFrom(JudgeEngine engine) {
//			// TODO Auto-generated method stub
//			engineForInnerClassEngineToUse=engine;
//			
//			adapter.add(new OneComment(true, engine.onConfusedResponse()));
//			engine.searchConceptNet(new AsyncTaskResponse<List<String>>() {
//				
//				@Override
//				public void processFinish(List<String> output) {
//					// TODO Auto-generated method stub
//					if(output.size()>0){
//						adapter.add(new OneComment(true, 
//							openingResponse[RandomUtil.getRandomInteger(0, openingResponse.length-1)]
//							+output.get(RandomUtil.getRandomInteger(0, output.size()-1))
//							));
//					}
//				}
//				
//			});
//			
//			
//		}
	//
//		@Override
//		public void putQuestionFrom(JudgeEngine engine) {
//			//Move put question to putResponseFrom function
//			adapter.add(new OneComment(true, "�ڭ̴��@�D�a!"+engine.getNextQuestion()));
//		}
		
	}

	

	

}

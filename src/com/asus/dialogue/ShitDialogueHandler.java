package com.asus.dialogue;

import java.util.List;

import com.asus.asyctask.AsyncTaskResponse;
import com.asus.bubbles.BubblesArrayAdapter;
import com.asus.bubbles.OneComment;
import com.asus.data.ConceptNetData;
import com.asus.engine.JudgeEngine;
import com.asus.util.RandomUtil;

public class ShitDialogueHandler extends DialogueHandler{
	
	JudgeEngine engineForInnerClassEngineToUse;
	String[] toGoBackTopicString={"�n�F,���n����F..�ӱM�ߦ^���D���o!","�n��,�M�߳��ڪ��y�y��..","�@~�M�ߦ^���D�ذէA!","�A�ܤ��M�߭C,��ڰ���@��..","��~~�b�������,���S���M��ť�ڪ��D�اr?","�M�ߤ@�I��~�A�o�˧ڦn�ˤ�.."};
	String[] linkUserTopicStrings={"��?���ƻ�r","����!�A�b���ƻ��!","�A�ܷd�Ǯ@! ���ӧr..","�F�����o�ӧr? ���L..","��..","�@? ��ı�o�r..","����~�ƻ�?"};
	public ShitDialogueHandler(BubblesArrayAdapter adapter) {
		super(adapter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(JudgeEngine judgeEngine) {
		// TODO Auto-generated method stub
		engineForInnerClassEngineToUse=judgeEngine;
		
		ConceptNetData.getInstance().searchConceptNet(judgeEngine.getRawAnswer().trim(), "", new AsyncTaskResponse<List<String>>() {
			@Override
			public void processFinish(List<String> output) {
				// TODO Auto-generated method stub
				String linkString=linkUserTopicStrings[RandomUtil.getRandomInteger(0, linkUserTopicStrings.length-1)];
				String gobackString=toGoBackTopicString[RandomUtil.getRandomInteger(0, toGoBackTopicString.length-1)];
				
				if(output.size()>0){
					adapter.add(new OneComment(true, linkString+output.get(0)));
					adapter.add(new OneComment(true, gobackString+engineForInnerClassEngineToUse.onShitResponse()));
				}else{
					adapter.add(new OneComment(true, linkString));
					adapter.add(new OneComment(true, gobackString+engineForInnerClassEngineToUse.onShitResponse()));
				}
			}
		});
		
		//adapter.add(new OneComment(true, judgeEngine.onShitResponse()));
	}


}

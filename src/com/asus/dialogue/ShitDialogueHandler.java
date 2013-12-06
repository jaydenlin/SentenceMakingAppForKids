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
	String[] toGoBackTopicString={"好了,不要鬼扯了..來專心回答題目囉!","好啦,專心陪我玩造句嘛..","哦~專心回答題目啦你!","你很不專心耶,跟我鬼扯一堆..","齁~~在那邊亂講,有沒有專心聽我的題目呀?","專心一點啦~你這樣我好傷心.."};
	String[] linkUserTopicStrings={"啊?講甚麼呀","哈哈!你在講甚麼啦!","你很搞怪哦! 那個呀..","幹嘛講這個呀? 不過..","噢..","哦? 我覺得呀..","哈哈~甚麼鬼?"};
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

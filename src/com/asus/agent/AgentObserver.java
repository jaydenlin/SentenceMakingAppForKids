package com.asus.agent;

import com.asus.engine.JudgeEngine;

public abstract class AgentObserver {
	
	public abstract void update(JudgeEngine judgeEngine);
	protected NotifyDoneCallback notifyDoneCallback;
	
	public void setNotifyDoneCallback(NotifyDoneCallback notifyDoneCallback){
		this.notifyDoneCallback=notifyDoneCallback;
	};
}

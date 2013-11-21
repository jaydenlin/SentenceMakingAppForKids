package com.asus.agent;

import com.asus.engine.JudgeEngine;

public abstract class AgentObserver {
	
	public abstract void update(JudgeEngine judgeEngine);
	protected NotifyCallback notifyDoneCallback;
	
	public void setNotifyDoneCallback(NotifyCallback notifyDoneCallback){
		this.notifyDoneCallback=notifyDoneCallback;
	};
}

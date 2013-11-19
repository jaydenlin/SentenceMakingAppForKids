package com.asus.agent;

import com.asus.engine.JudgeEngine;

public interface AgentSubject {
	public abstract void addObserver(AgentObserver agentObserver);
	public abstract void removeObserver(AgentObserver agentObserver);
	public abstract void notifyObservers(JudgeEngine judgeEngine);
}

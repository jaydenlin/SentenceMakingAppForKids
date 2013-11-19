package com.asus.agent;

import com.asus.engine.JudgeEngine;

public interface AgentObserver {
	public abstract void update(JudgeEngine judgeEngine);
}

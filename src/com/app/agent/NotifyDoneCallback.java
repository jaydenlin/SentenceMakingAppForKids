package com.app.agent;

import android.R.integer;

import com.app.engine.JudgeEngine;

public abstract class NotifyDoneCallback {
	int handlerIndex;
	public NotifyDoneCallback(int handlerIndex) {
		// TODO Auto-generated constructor stub
		this.handlerIndex=handlerIndex;
	}
	public abstract void doNextHandler(JudgeEngine judgeEngine);
}

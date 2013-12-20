package com.app.remote;

public interface RemoteCallback {
	public void onError(String error);
	public void onConnect();
	public void onDisconnect();
	public void onAnswerSelected(String selectedAnswer);
}

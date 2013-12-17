package com.asus.remote;

public interface RemoteCallback {
	public void onError(String error);
	public void onConnect();
	public void onAnswerSelected(String selectedAnswer);
}

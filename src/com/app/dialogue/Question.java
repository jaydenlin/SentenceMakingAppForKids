package com.app.dialogue;

public class Question {
	public boolean isAskingAdj;
	public String questionPhrase;
	public static Question instance;
	
	public static Question getInstance(){
		if(instance==null){
			instance = new Question();
		}
		return instance;
	}
	
	private Question(){
		
	}
}

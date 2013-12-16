package com.asus.remote;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;


public class PreparedAnswersList {

	List<String> preparedAnswers = new ArrayList<String>();

	private static PreparedAnswersList instance;
	public static PreparedAnswersList getInstance() {
		if (instance == null) {
			instance = new PreparedAnswersList();
		}
		return instance;
	}

	private PreparedAnswersList() {

	}

	public void add(String answer) {
		preparedAnswers.add(answer);
	}

	
	public String outputAndClear() {
		String jsonString="";
		Gson gson = new Gson();
		jsonString = gson.toJson(this);
		preparedAnswers.clear();
		return jsonString;
	}

}

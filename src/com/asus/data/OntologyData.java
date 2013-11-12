package com.asus.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.asus.dialogue.DBHelper;

public class OntologyData {
	
	private HashMap<String,String[]> NounToAdjective = new HashMap<String, String[]>();
	private HashMap<String,String[]> AdjectiveToNoun = new HashMap<String, String[]>();
	String [] MatchedAdjArrayForNoun;
	String [] MatchedNounArrayForAdj;
	Random random = new Random();
	
	private static OntologyData instance;
	
	public static OntologyData getInstance(){
		if(instance==null){
			instance = new OntologyData();
		}
		return instance;
	}
	
	private OntologyData(){
		
		MatchedAdjArrayForNoun="好吃的,黃色的,黃的,長的,長長的,長的,彎彎的".split(",");NounToAdjective.put("香蕉" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="紅的,紅色的,圓形,美麗,圓的,美麗的,美的,大的,好吃的,碩大,綠的,難吃,小的,香的".split(",");NounToAdjective.put("蘋果" , MatchedAdjArrayForNoun);

		
		MatchedNounArrayForAdj="蘋果,香蕉".split(",");AdjectiveToNoun.put("好吃的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="香蕉".split(",");AdjectiveToNoun.put("黃色的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="香蕉".split(",");AdjectiveToNoun.put("黃的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="香蕉".split(",");AdjectiveToNoun.put("長長的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="香蕉".split(",");AdjectiveToNoun.put("長的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="蘋果".split(",");AdjectiveToNoun.put("紅的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="蘋果".split(",");AdjectiveToNoun.put("紅色", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="蘋果".split(",");AdjectiveToNoun.put("紅色的", MatchedNounArrayForAdj);
		
	}
	
	public HashMap<String, String[]> getNounToAdjective(){
		return NounToAdjective;
	}
	
	public HashMap<String, String[]> geAdjectiveToNoun(){
		return AdjectiveToNoun;
	}
	
	//////////////////////////////////////////
	////Find Answer
	/////////////////////////////////////////
	public String[] getMatchedAdjArrayForOneNoun(String noun){
		if(NounToAdjective.containsKey(noun)){
			return NounToAdjective.get(noun);
		}else{
			return null;
		}
	}
	
	public String[] getMatchedNounArrayForOneAdj(String adj){
		if(AdjectiveToNoun.containsKey(adj)){
			return AdjectiveToNoun.get(adj);
		}else{
			return null;
		}
	}
	//////////////////////////////////////////
	////Find teach
	/////////////////////////////////////////
	public String getOneRandomAdjForOneNoun(String noun){
		if(NounToAdjective.containsKey(noun)){
			String[] adjArray=NounToAdjective.get(noun);
			return adjArray[getRandomInteger(0, adjArray.length-1)];
		}
		return null;
	}
	
	public String getOneRandomNounForOneAdj(String adj){
		if(AdjectiveToNoun.containsKey(adj)){
			String[] nounArray=AdjectiveToNoun.get(adj);
			return nounArray[getRandomInteger(0, nounArray.length-1)];
		}
		return null;
	}
	
	public String getOneRandomNoun(){
		Object[] values = AdjectiveToNoun.values().toArray();
		String[] nouns = (String[])values[random.nextInt(values.length)];
		return nouns[getRandomInteger(0,nouns.length-1)];
		//return "名詞超範圍";
	}
	
	public String getOneRandomAdj(){
		Object[] values = NounToAdjective.values().toArray();
		String[] adjs = (String[])values[random.nextInt(values.length)]; 
		return adjs[getRandomInteger(0,adjs.length-1)];
		//return "形容詞詞超範圍";
	}
	
	
	private int getRandomInteger(int aStart, int aEnd) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		long range = (long) aEnd - (long) aStart + 1;
		long fraction = (long) (range * random.nextDouble());
		int randomNumber = (int) (fraction + aStart);
		return randomNumber;
	}
	
	
}

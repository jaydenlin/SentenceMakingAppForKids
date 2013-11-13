package com.asus.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.util.Log;


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
		MatchedAdjArrayForNoun="紅的,紅色的,圓形,美麗,圓的,美麗的,美的,大的,好吃的,綠的,難吃,小的".split(",");NounToAdjective.put("蘋果" , MatchedAdjArrayForNoun);

		MatchedAdjArrayForNoun="毛茸茸的,溫馴的,迅速的,靈敏的".split(",");NounToAdjective.put("兔子" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="毛茸茸的,活潑的,靈敏的".split(",");NounToAdjective.put("老鼠" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="長長的,迅速的,靈敏的".split(",");NounToAdjective.put("鯊魚" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="毛茸茸的,溫馴的".split(",");NounToAdjective.put("羊" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="滑溜溜的,長長的,有花紋的".split(",");NounToAdjective.put("蛇" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="有花紋的,兇猛的,毛茸茸的".split(",");NounToAdjective.put("老虎" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="溼答答的,溼淋淋的,慢吞吞的,緩慢的,慢的".split(",");NounToAdjective.put("烏龜" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="巨大的,緩慢的,慢吞吞的,溫馴的".split(",");NounToAdjective.put("鯨魚" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="毛茸茸的,兇猛的".split(",");NounToAdjective.put("獅子" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="溼答答的,溼淋淋的,慢吞吞的,緩慢的,慢的".split(",");NounToAdjective.put("河馬" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="毛茸茸,溫馴的,靈敏的,有花紋的".split(",");NounToAdjective.put("貓" , MatchedAdjArrayForNoun);
		
		MatchedNounArrayForAdj="蛇".split(",");AdjectiveToNoun.put("滑溜溜的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="蛇,鯨魚,河馬,烏龜,金魚".split(",");AdjectiveToNoun.put("溼答答的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="蛇,鯨魚,河馬,烏龜".split(",");AdjectiveToNoun.put("溼淋淋的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="兔子,貓,羊".split(",");AdjectiveToNoun.put("溫馴的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="兔子,老鼠".split(",");AdjectiveToNoun.put("迅速的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="老虎,蛇".split(",");AdjectiveToNoun.put("有花紋的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="貓,羊,兔子".split(",");AdjectiveToNoun.put("溫馴的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="烏龜,鯨魚,河馬".split(",");AdjectiveToNoun.put("慢吞吞的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="烏龜,鯨魚,河馬".split(",");AdjectiveToNoun.put("緩慢的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="烏龜,鯨魚,河馬".split(",");AdjectiveToNoun.put("慢的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="獅子,兔子,老鼠,貓".split(",");AdjectiveToNoun.put("靈敏的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="獅子,兔子,羊,貓".split(",");AdjectiveToNoun.put("毛茸茸的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="獅子,老虎,蛇".split(",");AdjectiveToNoun.put("兇猛的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="蘋果,香蕉".split(",");AdjectiveToNoun.put("好吃的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="香蕉".split(",");AdjectiveToNoun.put("黃色的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="香蕉".split(",");AdjectiveToNoun.put("黃的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="香蕉".split(",");AdjectiveToNoun.put("長長的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="香蕉".split(",");AdjectiveToNoun.put("長的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="蘋果,蕃茄".split(",");AdjectiveToNoun.put("紅的", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="蘋果,蕃茄".split(",");AdjectiveToNoun.put("紅色", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="蘋果,蕃茄".split(",");AdjectiveToNoun.put("紅色的", MatchedNounArrayForAdj);
		
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
		}else{
			Log.w(getClass().getSimpleName(),"Can't find random Adj for the noun");
		}
		return "";
	}
	
	public String getOneRandomNounForOneAdj(String adj){
		if(AdjectiveToNoun.containsKey(adj)){
			String[] nounArray=AdjectiveToNoun.get(adj);
			return nounArray[getRandomInteger(0, nounArray.length-1)];
		}else{
			Log.w(getClass().getSimpleName(),"Can't find random Noun for the adj");
		}
		return "";
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

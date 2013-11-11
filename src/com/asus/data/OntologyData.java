package com.asus.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
		MatchedAdjArrayForNoun="紅的,紅色,紅色的,圓形,美麗,圓的,美麗的,美的,大的,好吃的,碩大,綠的,難吃,小的,香的".split(",");NounToAdjective.put("蘋果" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="香的,香香的,臭的,臭臭的,淡的,淡淡的,濃的,濃濃的,刺鼻的,作嘔的,腥腥的,騷騷的,無味的,香噴噴的,酸的,酸酸的,甜的,甜甜的,鹹的,鹹鹹的,辣的,辣辣的,苦的,苦苦的,美味的,澀的,澀澀的,清淡的,滑滑的,冰冷的,涼的,暖的,溫暖的,熱的,溫熱的,燙的,青的,青色的,紅的,紅色的,紅紅的,火紅色,橙的,橙色的,橘的,橘色的,黃的,黃色的,黃黃的,綠的,綠色的,綠綠的,黃綠的,黃綠色的,綠油油的,土黃的,土黃色的,咖啡色,咖啡色的,藍的,藍色的,藍藍的,水藍色,紫的,紫色的,紫紫的,白的,白色的,白白的,白花花的,乳白色,乳白色的,黑的,黑色的,黑黑的,暗黑的,黑漆漆的,粉紅,粉紅色的,粉粉的,,無色的,無彩色的,黑白的,黑白色的,巧克力色,巧克力色的,壞的,好的,新的,全新的,新鮮的,珍貴的,健康的,特別的,乾淨的,優良的".split(",");NounToAdjective.put("沙拉醬" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="臭的,臭臭的,橢圓的,扁的,扁扁的,黏黏的,硬硬的,青的,青色的,綠的,綠色的,綠綠的,黃綠的,黃綠色的,土黃的,土黃色的,咖啡色,咖啡色的,黑的,黑色的,黑黑的,暗黑的,黑漆漆的,慢的,慢慢的,小的,小小的,圓圓的,髒髒的,髒兮兮的,健康的,生病的,疲憊的,特別的,乾淨的,溫馴的,前面的,後面的,左邊的,右邊的,中間的,裡面的,上面的,下面的,左前方的,右前方的,左後方的,右後方的".split(",");NounToAdjective.put("烏龜" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="香的,香香的,淡的,淡淡的,濃的,濃濃的,刺鼻的,作嘔的,腥腥的,騷騷的,無味的,香噴噴的,酸的,酸酸的,甜的,甜甜的,鹹的,鹹鹹的,辣的,辣辣的,苦的,苦苦的,美味的,澀的,澀澀的,清淡的,長長的,滑滑的,柔柔的,冰冷的,涼的,紅色的,橙色的,,橘色的,黃色的,綠色的,黃綠色的,土黃色的,咖啡色,咖啡色的,藍色的,藍藍的,水藍色,紫色的,白色的,白白的,乳白色,乳白色的,黑的,黑色的,粉紅色的,金色的,黃金色的,銀色的,壞的,好的,,新的,全新的,特別的,優良的,前面的,後面的,左邊的,右邊的,中間的,裡面的,上面的,下面的,左前方的,右前方的,左後方的,右後方的".split(",");NounToAdjective.put("洗髮精" , MatchedAdjArrayForNoun);

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
	////Find Anwser
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
		return (String)values[random.nextInt(values.length)];
		//return "名詞超範圍";
	}
	
	public String getOneRandomAdj(){
		Object[] values = NounToAdjective.values().toArray();
		return (String)values[random.nextInt(values.length)];
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

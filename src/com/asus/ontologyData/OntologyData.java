package com.asus.ontologyData;

import java.util.ArrayList;
import java.util.HashMap;

public class OntologyData {
	
	private HashMap<String,String> NounToAdjective = new HashMap<String, String>();
	private HashMap<String,String> AdjectiveToNoun = new HashMap<String, String>();
	private static OntologyData instance;
	
	public static OntologyData getInstance(){
		if(instance==null){
			instance = new OntologyData();
		}
		return instance;
	}
	
	private OntologyData(){
		
		NounToAdjective.put("香蕉" , "好吃的,黃色的,彎彎的");
		NounToAdjective.put("蘋果" , "好吃的,紅色的");
		
		AdjectiveToNoun.put("好吃的", "香蕉,蘋果");

	}
}

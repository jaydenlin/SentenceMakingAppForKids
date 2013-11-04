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
		
		NounToAdjective.put("����" , "�n�Y��,���⪺,�s�s��");
		NounToAdjective.put("ī�G" , "�n�Y��,���⪺");
		
		AdjectiveToNoun.put("�n�Y��", "����,ī�G");

	}
}

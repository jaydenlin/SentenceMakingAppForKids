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
		
		MatchedAdjArrayForNoun="�n�Y��,���⪺,����,����,������,����,�s�s��".split(",");NounToAdjective.put("����" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="����,���⪺,���,���R,�ꪺ,���R��,����,�j��,�n�Y��,��,���Y,�p��".split(",");NounToAdjective.put("ī�G" , MatchedAdjArrayForNoun);

		MatchedAdjArrayForNoun="����,�Ź���,���t��,�F�Ӫ�".split(",");NounToAdjective.put("�ߤl" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="����,���⪺,�F�Ӫ�".split(",");NounToAdjective.put("�ѹ�" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="������,���t��,�F�Ӫ�".split(",");NounToAdjective.put("�T��" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="����,�Ź���".split(",");NounToAdjective.put("��" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="�ƷȷȪ�,������,���᯾��".split(",");NounToAdjective.put("�D" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="���᯾��,���r��,����".split(",");NounToAdjective.put("�Ѫ�" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="�õ�����,�òO�O��,�C�]�]��,�w�C��,�C��".split(",");NounToAdjective.put("�Q�t" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="���j��,�w�C��,�C�]�]��,�Ź���".split(",");NounToAdjective.put("�H��" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="����,���r��".split(",");NounToAdjective.put("��l" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="�õ�����,�òO�O��,�C�]�]��,�w�C��,�C��".split(",");NounToAdjective.put("�e��" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="����,�Ź���,�F�Ӫ�,���᯾��".split(",");NounToAdjective.put("��" , MatchedAdjArrayForNoun);
		
		MatchedNounArrayForAdj="�D".split(",");AdjectiveToNoun.put("�ƷȷȪ�", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="�D,�H��,�e��,�Q�t,����".split(",");AdjectiveToNoun.put("�õ�����", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="�D,�H��,�e��,�Q�t".split(",");AdjectiveToNoun.put("�òO�O��", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="�ߤl,��,��".split(",");AdjectiveToNoun.put("�Ź���", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="�ߤl,�ѹ�".split(",");AdjectiveToNoun.put("���t��", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="�Ѫ�,�D".split(",");AdjectiveToNoun.put("���᯾��", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="��,��,�ߤl".split(",");AdjectiveToNoun.put("�Ź���", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="�Q�t,�H��,�e��".split(",");AdjectiveToNoun.put("�C�]�]��", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="�Q�t,�H��,�e��".split(",");AdjectiveToNoun.put("�w�C��", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="�Q�t,�H��,�e��".split(",");AdjectiveToNoun.put("�C��", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="��l,�ߤl,�ѹ�,��".split(",");AdjectiveToNoun.put("�F�Ӫ�", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="��l,�ߤl,��,��".split(",");AdjectiveToNoun.put("����", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="��l,�Ѫ�,�D".split(",");AdjectiveToNoun.put("���r��", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="ī�G,����".split(",");AdjectiveToNoun.put("�n�Y��", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="����".split(",");AdjectiveToNoun.put("���⪺", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="����".split(",");AdjectiveToNoun.put("����", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="����".split(",");AdjectiveToNoun.put("������", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="����".split(",");AdjectiveToNoun.put("����", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="ī�G,���X".split(",");AdjectiveToNoun.put("����", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="ī�G,���X".split(",");AdjectiveToNoun.put("����", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="ī�G,���X".split(",");AdjectiveToNoun.put("���⪺", MatchedNounArrayForAdj);
		
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
		//return "�W���W�d��";
	}
	
	public String getOneRandomAdj(){
		Object[] values = NounToAdjective.values().toArray();
		String[] adjs = (String[])values[random.nextInt(values.length)]; 
		return adjs[getRandomInteger(0,adjs.length-1)];
		//return "�ήe�����W�d��";
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

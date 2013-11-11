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
		
		MatchedAdjArrayForNoun="�n�Y��,���⪺,����,����,������,����,�s�s��".split(",");NounToAdjective.put("����" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="����,����,���⪺,���,���R,�ꪺ,���R��,����,�j��,�n�Y��,�Ӥj,��,���Y,�p��,����".split(",");NounToAdjective.put("ī�G" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="����,������,�䪺,��䪺,�H��,�H�H��,�@��,�@�@��,���,�@�ê�,�{�{��,���̪�,�L����,���Q�Q��,�Ī�,�ĻĪ�,����,������,�Ъ�,���Ъ�,����,������,�W��,�W�W��,������,�ߪ�,���ߪ�,�M�H��,�Ʒƪ�,�B�N��,�D��,�x��,�ŷx��,����,�ż���,�S��,�C��,�C�⪺,����,���⪺,������,������,��,��⪺,�諸,��⪺,����,���⪺,������,��,��⪺,���,����,����⪺,��o�o��,�g����,�g���⪺,�@�ئ�,�@�ئ⪺,�Ū�,�Ŧ⪺,���Ū�,���Ŧ�,����,���⪺,������,�ժ�,�զ⪺,�եժ�,�ժ�᪺,�ťզ�,�ťզ⪺,�ª�,�¦⪺,�¶ª�,�t�ª�,�º�����,����,�����⪺,������,,�L�⪺,�L�m�⪺,�¥ժ�,�¥զ⪺,���J�O��,���J�O�⪺,�a��,�n��,�s��,���s��,�s�A��,�öQ��,���d��,�S�O��,���b��,�u�}��".split(",");NounToAdjective.put("�F����" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="�䪺,��䪺,��ꪺ,��,���,�H�H��,�w�w��,�C��,�C�⪺,��,��⪺,���,����,����⪺,�g����,�g���⪺,�@�ئ�,�@�ئ⪺,�ª�,�¦⪺,�¶ª�,�t�ª�,�º�����,�C��,�C�C��,�p��,�p�p��,��ꪺ,żż��,ż������,���d��,�ͯf��,�h�Ϊ�,�S�O��,���b��,�Ź���,�e����,�᭱��,���䪺,�k�䪺,������,�̭���,�W����,�U����,���e�誺,�k�e�誺,����誺,�k��誺".split(",");NounToAdjective.put("�Q�t" , MatchedAdjArrayForNoun);
		MatchedAdjArrayForNoun="����,������,�H��,�H�H��,�@��,�@�@��,���,�@�ê�,�{�{��,���̪�,�L����,���Q�Q��,�Ī�,�ĻĪ�,����,������,�Ъ�,���Ъ�,����,������,�W��,�W�W��,������,�ߪ�,���ߪ�,�M�H��,������,�Ʒƪ�,�X�X��,�B�N��,�D��,���⪺,��⪺,,��⪺,���⪺,��⪺,����⪺,�g���⪺,�@�ئ�,�@�ئ⪺,�Ŧ⪺,���Ū�,���Ŧ�,���⪺,�զ⪺,�եժ�,�ťզ�,�ťզ⪺,�ª�,�¦⪺,�����⪺,���⪺,�����⪺,�Ȧ⪺,�a��,�n��,,�s��,���s��,�S�O��,�u�}��,�e����,�᭱��,���䪺,�k�䪺,������,�̭���,�W����,�U����,���e�誺,�k�e�誺,����誺,�k��誺".split(",");NounToAdjective.put("�~�v��" , MatchedAdjArrayForNoun);

		MatchedNounArrayForAdj="ī�G,����".split(",");AdjectiveToNoun.put("�n�Y��", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="����".split(",");AdjectiveToNoun.put("���⪺", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="����".split(",");AdjectiveToNoun.put("����", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="����".split(",");AdjectiveToNoun.put("������", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="����".split(",");AdjectiveToNoun.put("����", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="ī�G".split(",");AdjectiveToNoun.put("����", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="ī�G".split(",");AdjectiveToNoun.put("����", MatchedNounArrayForAdj);
		MatchedNounArrayForAdj="ī�G".split(",");AdjectiveToNoun.put("���⪺", MatchedNounArrayForAdj);
		
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
		//return "�W���W�d��";
	}
	
	public String getOneRandomAdj(){
		Object[] values = NounToAdjective.values().toArray();
		return (String)values[random.nextInt(values.length)];
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

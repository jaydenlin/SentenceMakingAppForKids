package com.asus.dialogue;

import java.util.ArrayList;
import java.util.HashMap;

import com.asus.bubbles.OneComment;

public class DialogueDispather {
	
	private static final String[] ASKING = {"�O�ƻ�","�ƻ�O","����"};
	private static final String[] ANWSERING = {};
	private String speech;
	
	public DialogueDispather(String answer){
		String[] keywords= ASKING;
		for(int i=0;i<keywords.length;i++){	
			if (answer.indexOf(keywords[i]) != -1){ 
	            //System.out.println(str);
				break;
			}
		}
		
		
	}
	
}	

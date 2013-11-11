package com.asus.data;

import com.asus.activity.R;



public class PhotoData {
	
	private static PhotoData instance;
	public static PhotoData getInstance(){
		if(instance==null){
			instance = new PhotoData();
		}
		return instance;
	}
	
	
}

package com.asus.scene;

import android.R.integer;
import android.util.Log;

import com.asus.engine.JudgeEngine;
import com.asus.photos.OnePhoto;
import com.asus.photos.PhotosArrayAdapter;

public class TeachSceneHandler extends SceneHandler{

	public TeachSceneHandler(PhotosArrayAdapter photosArrayAdapter) {
		super(photosArrayAdapter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void putHintPhotoFrom(JudgeEngine engine) {
		// TODO Auto-generated method stub
		int photoid=engine.getTeachPhoto();
		if(photoid!=0){
			photosArrayAdapter.clear();
			photosArrayAdapter.add(new OnePhoto(engine.getTeachPhoto(), ""));
		}
	}
	
	@Override
	public void update(JudgeEngine judgeEngine) {
		// TODO Auto-generated method stub
		
	}


}

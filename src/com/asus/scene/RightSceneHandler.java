package com.asus.scene;

import android.R.integer;

import com.asus.engine.JudgeEngine;
import com.asus.photos.OnePhoto;
import com.asus.photos.PhotosArrayAdapter;

public class RightSceneHandler extends SceneHandler{

	public RightSceneHandler(PhotosArrayAdapter photosArrayAdapter) {
		super(photosArrayAdapter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void putHintPhotoFrom(JudgeEngine engine) {
		// TODO Auto-generated method stub
		int[] photoArrayId=engine.getHintPhotos();
		photosArrayAdapter.clear();
		for(int i=0;i<photoArrayId.length;i++){
			photosArrayAdapter.add(new OnePhoto(photoArrayId[i], ""));
		}
		
	}

}

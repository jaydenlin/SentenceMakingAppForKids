package com.asus.scene;

import android.util.Log;

import com.asus.engine.JudgeEngine;
import com.asus.photos.OnePhoto;
import com.asus.photos.PhotosArrayAdapter;

public class WrongSceneHandler extends SceneHandler{

	public WrongSceneHandler(PhotosArrayAdapter photosArrayAdapter) {
		super(photosArrayAdapter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void putHintPhotoFrom(JudgeEngine engine) {
		//do nothing
		int[] photoArrayId=engine.getHintPhotos();
		//photosArrayAdapter.clear();
		for(int i=0;i<photoArrayId.length;i++){
			photosArrayAdapter.add(new OnePhoto(photoArrayId[i], ""));
			Log.w(getClass().getSimpleName(), Integer.toString(photoArrayId[i]));
		}
	}

}

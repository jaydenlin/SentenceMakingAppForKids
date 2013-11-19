package com.asus.scene;

import com.asus.engine.JudgeEngine;
import com.asus.photos.OnePhoto;
import com.asus.photos.PhotosArrayAdapter;

public class ConfusedSceneHandler extends SceneHandler{

	public ConfusedSceneHandler(PhotosArrayAdapter photosArrayAdapter) {
		super(photosArrayAdapter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void putHintPhotoFrom(JudgeEngine engine) {
		// TODO Auto-generated method stub
		//do nothing
		int[] photoArrayId=engine.getHintPhotos();
		photosArrayAdapter.clear();
		for(int i=0;i<photoArrayId.length;i++){
			photosArrayAdapter.add(new OnePhoto(photoArrayId[i], ""));
		}
	}

}

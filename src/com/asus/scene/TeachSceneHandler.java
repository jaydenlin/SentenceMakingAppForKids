package com.asus.scene;

import android.util.Log;

import com.asus.engine.JudgeEngine;
import com.asus.exception.PhotoIdsNotFound;
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
		
		try {
			int photoid;
			photoid = engine.getTeachPhoto();
			photosArrayAdapter.clear();
			photosArrayAdapter.add(new OnePhoto(engine.getTeachPhoto(), ""));
		} catch (PhotoIdsNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void update(JudgeEngine engine) {
		// TODO Auto-generated method stub
		putHintPhotoFrom(engine);
		notifyDoneCallback.doNextHandler(engine);
	}

}

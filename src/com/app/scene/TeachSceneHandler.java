package com.app.scene;

import android.util.Log;

import com.app.engine.JudgeEngine;
import com.app.exception.PhotoIdsNotFound;
import com.app.photos.OnePhoto;
import com.app.photos.PhotosArrayAdapter;

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

package com.app.scene;

import android.R.integer;

import com.app.engine.JudgeEngine;
import com.app.exception.PhotoIdsNotFound;
import com.app.photos.OnePhoto;
import com.app.photos.PhotosArrayAdapter;

public class RightSceneHandler extends SceneHandler{

	public RightSceneHandler(PhotosArrayAdapter photosArrayAdapter) {
		super(photosArrayAdapter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void putHintPhotoFrom(JudgeEngine engine) {
		// TODO Auto-generated method stub
		int[] photoArrayId;
		try {
			photoArrayId = engine.getHintPhotos();
			photosArrayAdapter.clear();
			for(int i=0;i<photoArrayId.length;i++){
				photosArrayAdapter.add(new OnePhoto(photoArrayId[i], ""));
			}
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

package com.asus.scene;

import com.asus.engine.JudgeEngine;
import com.asus.exception.PhotoIdsNotFound;
import com.asus.photos.OnePhoto;
import com.asus.photos.PhotosArrayAdapter;

public class ConfusedSceneHandler extends SceneHandler {

	public ConfusedSceneHandler(PhotosArrayAdapter photosArrayAdapter) {
		super(photosArrayAdapter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void putHintPhotoFrom(JudgeEngine engine) {
		// TODO Auto-generated method stub
		// do nothing
		int[] photoArrayId;
		try {
			photoArrayId = engine.getHintPhotos();
			photosArrayAdapter.clear();
			for (int i = 0; i < photoArrayId.length; i++) {
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

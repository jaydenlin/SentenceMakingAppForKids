package com.asus.scene;

import android.widget.GridView;

import com.asus.engine.JudgeEngine;
import com.asus.photos.PhotosArrayAdapter;

public abstract class SceneHandler {
	
	private PhotosArrayAdapter photosArrayAdapter;
	private GridView photoGridView;
	
	public SceneHandler(PhotosArrayAdapter photosArrayAdapter) {
		// TODO Auto-generated constructor stub
		this.photosArrayAdapter=photosArrayAdapter;
	}
	
	public abstract void putHintPhotoFrom(JudgeEngine engine);
}

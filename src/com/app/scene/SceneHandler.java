package com.app.scene;

import android.widget.GridView;

import com.app.agent.AgentObserver;
import com.app.data.OntologyData;
import com.app.engine.JudgeEngine;
import com.app.photos.PhotosArrayAdapter;

public abstract class SceneHandler extends AgentObserver{
	
	protected PhotosArrayAdapter photosArrayAdapter;
	public SceneHandler(PhotosArrayAdapter photosArrayAdapter) {
		// TODO Auto-generated constructor stub
		this.photosArrayAdapter=photosArrayAdapter;
	}
	
	public abstract void putHintPhotoFrom(JudgeEngine engine);
}

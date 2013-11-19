package com.asus.scene;

import android.widget.GridView;

import com.asus.agent.AgentObserver;
import com.asus.data.OntologyData;
import com.asus.engine.JudgeEngine;
import com.asus.photos.PhotosArrayAdapter;

public abstract class SceneHandler implements AgentObserver{
	
	protected PhotosArrayAdapter photosArrayAdapter;
	public SceneHandler(PhotosArrayAdapter photosArrayAdapter) {
		// TODO Auto-generated constructor stub
		this.photosArrayAdapter=photosArrayAdapter;
	}
	
	public abstract void putHintPhotoFrom(JudgeEngine engine);
}

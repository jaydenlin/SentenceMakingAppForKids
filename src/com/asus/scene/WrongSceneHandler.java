package com.asus.scene;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import com.asus.engine.JudgeEngine;
import com.asus.exception.PhotoIdsNotFound;
import com.asus.photos.OnePhoto;
import com.asus.photos.PhotosArrayAdapter;

public class WrongSceneHandler extends SceneHandler{

	public WrongSceneHandler(PhotosArrayAdapter photosArrayAdapter) {
		super(photosArrayAdapter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void putHintPhotoFrom(JudgeEngine engine) {
		
		//do teach things
		Log.w(getClass().getSimpleName(),"do teach");
		new TeachSceneHandlerAsyncTask().execute(engine);
//		
		//put original photo
		Log.w(getClass().getSimpleName(),"do put photo");
		new WrongSceneHandlerAsyncTask().execute(engine);
		
	}
	
	@Override
	public void update(JudgeEngine engine) {
		// TODO Auto-generated method stub
		putHintPhotoFrom(engine);
		notifyDoneCallback.doNextHandler(engine);
	}

	
	class TeachSceneHandlerAsyncTask extends AsyncTask<JudgeEngine, Void, String>{
		
		JudgeEngine engineAtInnerClass;
		@Override
		protected String doInBackground(JudgeEngine... params) {
			// TODO Auto-generated method stub
			Log.w(getClass().getSimpleName(), "doInTheBackgroud TeachHandler");
			engineAtInnerClass=params[0];
			
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			TeachSceneHandler teachSceneHandler=new TeachSceneHandler(photosArrayAdapter);
			teachSceneHandler.putHintPhotoFrom(engineAtInnerClass);
		}
		
		
		
	}
	
	class WrongSceneHandlerAsyncTask extends AsyncTask<JudgeEngine, Void, String>{
		
		JudgeEngine engineAtInnerClass;
		@Override
		protected String doInBackground(JudgeEngine... params) {
			// TODO Auto-generated method stub
			Log.w(getClass().getSimpleName(), "doInTheBackgroud WrongHandler");
			engineAtInnerClass=params[0];
			
			//in order to show teach photo first
			try {
				Thread.sleep(9000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			int[] photoArrayId;
			try {
				photoArrayId = engineAtInnerClass.getHintPhotos();
				photosArrayAdapter.clear();
				for(int i=0;i<photoArrayId.length;i++){
					photosArrayAdapter.add(new OnePhoto(photoArrayId[i], ""));
					Log.w(getClass().getSimpleName(), Integer.toString(photoArrayId[i]));
					
				}
			} catch (PhotoIdsNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
	}
	

}

package com.asus.asyctask;

import com.asus.atc.dialogservice.DialogServiceConnector;

import android.os.AsyncTask;

public class CSRTimeoutCounter extends AsyncTask<DialogServiceConnector, Void, Void>{
	
	@Override
	protected Void doInBackground(DialogServiceConnector... params) {
		// TODO Auto-generated method stub
		DialogServiceConnector dialogServiceConnector=params[0];
		
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dialogServiceConnector.stopCSR();
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	

}

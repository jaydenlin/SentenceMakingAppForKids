package com.asus.remote;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.os.AsyncTask;
import android.util.Log;

import io.socket.*;

public class RemoteConnection {
	
	public void connect(RemoteSelectedCallback callback) {
		new SocketIOConnect(callback).execute();
	}
	
	class SocketIOConnect extends AsyncTask<Void, Void, Void>{
		RemoteSelectedCallback callback;
		SocketIO socket;
		public SocketIOConnect(RemoteSelectedCallback callback){
			this.callback=callback;
		}
		
		@Override
		protected Void doInBackground(Void... params){
			try {
				socket = new SocketIO("http://jaydenlin.tw:8080/");
				socket.connect(new IOCallback() {
					
		            @Override
		            public void onMessage(JSONObject json, IOAcknowledge ack) {
		            }

		            @Override
		            public void onMessage(String data, IOAcknowledge ack) {
		            	Log.w("SocketIO","Server said: " + data);
		            }

		            @Override
		            public void onError(SocketIOException socketIOException) {
		            	Log.w("SocketIO","an Error occured");
		                socketIOException.printStackTrace();
		            }

		            @Override
		            public void onDisconnect() {
		            	Log.w("SocketIO","Connection terminated.");
		            }

		            @Override
		            public void onConnect() {
		            	Log.w("SocketIO","connected");
//		            	PreparedAnswersList.getInstance().add("firstItem");
//		            	PreparedAnswersList.getInstance().add("secendItem");
		            	
		            	socket.emit("device:questionDone", PreparedAnswersList.getInstance().outputAndClear());
		            }

		            @Override
		            public void on(String event, IOAcknowledge ack, Object... args) {
		            	Log.w("SocketIO","Server triggered event '" + event + "'");
		            	if(event.equals("server:receiveAnswer")){
		            		Gson gson=new Gson();
		            		callback.postExec(gson.toJson(args));
		            	}
		            }
		        });
		        
			} catch (MalformedURLException e) {
				Log.w("SocketIO","Not connected");
				e.printStackTrace();
			}
			
			return null;
		}
		
	}
	
	
}

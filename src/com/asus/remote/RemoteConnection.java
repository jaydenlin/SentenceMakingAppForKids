package com.asus.remote;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import io.socket.*;

public class RemoteConnection {
	
	public void connect(String JSONMessage) {
		new SocketIOConnect(JSONMessage).execute();
	}
	
	class SocketIOConnect extends AsyncTask<Void, Void, Void>{
		String message;
		SocketIO socket;
		public SocketIOConnect(String message){
			this.message=message;
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
		            	socket.emit("device:questionDone", "{\"preparedAnswers\":[\"firstItem\",\"secendItem\"]}");
		            }

		            @Override
		            public void on(String event, IOAcknowledge ack, Object... args) {
		            	Log.w("SocketIO","Server triggered event '" + event + "'");
		            	if(event.equals("remote:selectAnswer")){
		            		
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

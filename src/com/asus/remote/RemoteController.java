package com.asus.remote;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import io.socket.*;

public class RemoteController {
	
	public void connect() {
		new SocketIOConnect().execute();
	}
	
	class SocketIOConnect extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params){
			// TODO Auto-generated method stub
			SocketIO socket;
			try {
				socket = new SocketIO("http://127.0.0.1/");
				socket.connect(new IOCallback() {
		            @Override
		            public void onMessage(JSONObject json, IOAcknowledge ack) {
		                try {
		                	Log.w("SocketIO","Server said:" + json.toString(2));
		                } catch (JSONException e) {
		                    e.printStackTrace();
		                }
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
		            	Log.w("SocketIO","Connection established");
		            }

		            @Override
		            public void on(String event, IOAcknowledge ack, Object... args) {
		            	Log.w("SocketIO","Server triggered event '" + event + "'");
		            }
		        });

		        // This line is cached until the connection is establisched.
		        socket.send("Hello Server!");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				Log.w("SocketIO","Not connected");
				e.printStackTrace();
			}
			
			return null;
		}
		
	}
	
	
}

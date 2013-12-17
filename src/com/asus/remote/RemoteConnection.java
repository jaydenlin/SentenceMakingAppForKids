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

public class RemoteConnection extends AsyncTask<RemoteCallback, Void, Void>{
	
	private RemoteCallback callback;
	private SocketIO socket;
	private Gson gson = new Gson();
	
	@Override
	protected Void doInBackground(RemoteCallback... params) {
		// TODO Auto-generated method stub
		callback = params[0];
		try {
			socket = new SocketIO("http://jaydenlin.tw:8080/");
			
			socket.connect(new IOCallback() {

				@Override
				public void onMessage(JSONObject json, IOAcknowledge ack) {
				}

				@Override
				public void onMessage(String data, IOAcknowledge ack) {
					Log.w("SocketIO", "Server said: " + data);
				}

				@Override
				public void onError(SocketIOException socketIOException) {
					Log.w("SocketIO", "an Error occured");
					
					callback.onError(socketIOException.toString());
					socketIOException.printStackTrace();
				}

				@Override
				public void onDisconnect() {
					Log.w("SocketIO", "Connection terminated.");
					callback.onDisconnect();
				}

				@Override
				public void onConnect() {
					Log.w("SocketIO", "connected");
					// PreparedAnswersList.getInstance().add("firstItem");
					// PreparedAnswersList.getInstance().add("secendItem");
					socket.emit("device:questionDone", PreparedAnswersList
							.getInstance().output());
					callback.onConnect();
				}

				@Override
				public void on(String event, IOAcknowledge ack,
						Object... args) {
					Log.w("SocketIO", "Server triggered event '" + event
							+ "'");
					if (event.equals("server:receiveAnswer")) {
						callback.onAnswerSelected(gson.toJson(args).split(":")[2].split("\"")[1].toString());
					}
					
					if(event.equals("server:requestAnswers")){
						socket.emit("device:questionDone", PreparedAnswersList
								.getInstance().output());
					}
				}
			});

		} catch (MalformedURLException e) {
			Log.w("SocketIO", "Not connected");
			e.printStackTrace();
		}

		
		return null;
	}

}

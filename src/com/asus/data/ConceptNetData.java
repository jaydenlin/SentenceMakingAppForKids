package com.asus.data;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.asus.asyctask.AsyncTaskResponse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.os.AsyncTask;
import android.util.Log;

public class ConceptNetData {

	public static ConceptNetData instance;
	private AsyncTaskResponse<String> delegate;
	private Gson gson;
	
	public static ConceptNetData getInstance() {
		if (instance == null) {
			instance = new ConceptNetData();
		}
		return instance;
	}
	
	private ConceptNetData(){
		
	}
	
	public void searchConceptNet(String text,String surfaceText,AsyncTaskResponse<String> delegate){
		this.delegate=delegate;
		new getConceptNetData().execute(text,surfaceText);
	}
	
	class getConceptNetData extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String text=params[0].trim();
			String surfaceText=params[1].trim();
			
			HttpClient client = new DefaultHttpClient();
			try {
				HttpGet get = new HttpGet("http://conceptnet5.media.mit.edu/data/5.1/search?text="+text+"surfaceText="+surfaceText);
				HttpResponse response = client.execute(get);
				HttpEntity resEntity = response.getEntity();
				String result = EntityUtils.toString(resEntity);
				
				//Log.w(TAG, result);
				return result;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.w(getClass().getSimpleName(), "ClientProtocolException");
				return "";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.w(getClass().getSimpleName(), "IOException");
				return "";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			String parsedResult="";
			
			Log.w(getClass().getSimpleName(), "result:"+result);
			JsonElement jsonElement=new JsonParser().parse(result);
			JsonObject jsonObject=jsonElement.getAsJsonObject();
			if(jsonObject.getAsJsonArray("edges").size()!=0){
				parsedResult=jsonObject.getAsJsonArray("edges").get(0).getAsJsonObject().get("surfaceText").toString();
			}
			Log.w(getClass().getSimpleName(), "parsedResult:"+parsedResult);
			delegate.processFinish(parsedResult);
		}
		
		
		
	}
	
	
	
}

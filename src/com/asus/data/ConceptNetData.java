package com.asus.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.asus.asyctask.AsyncTaskResponse;
import com.asus.translate.TranslateApi;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.os.AsyncTask;
import android.util.Log;

public class ConceptNetData {

	public static ConceptNetData instance;
	private AsyncTaskResponse<List<String>> delegate;
	private Gson gson;

	public static ConceptNetData getInstance() {
		if (instance == null) {
			instance = new ConceptNetData();
		}
		return instance;
	}

	private ConceptNetData() {

	}

	public void searchConceptNet(String text, String surfaceText,
			AsyncTaskResponse<List<String>> delegate) {
		this.delegate = delegate;
		new getConceptNetData().execute(text, surfaceText);
	}

	class getConceptNetData extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String text = params[0].trim();
			String surfaceText = params[1].trim();
			String url;

			if (surfaceText.equals("")) {
				url = "http://conceptnet5.media.mit.edu/data/5.1/search?text="
						+ text;
			} else {
				url = "http://conceptnet5.media.mit.edu/data/5.1/search?text="
						+ text + "&surfaceText=" + surfaceText;
			}
			
			HttpClient client = new DefaultHttpClient();

			try {
				HttpGet get = new HttpGet(url);
				HttpResponse response = client.execute(get);
				HttpEntity resEntity = response.getEntity();
				String result = EntityUtils.toString(resEntity);

				// Log.w(TAG, result);
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
			Log.w(getClass().getSimpleName(), result);
			List<String> parsedResult = new ArrayList<String>();

			JsonElement jsonElement = new JsonParser().parse(result);
			Log.w(getClass().getSimpleName(), result);
			JsonObject jsonObject = jsonElement.getAsJsonObject();

			for (int i = 0; i < jsonObject.getAsJsonArray("edges").size(); i++) {
				JsonObject edges = jsonObject.getAsJsonArray("edges").get(i)
						.getAsJsonObject();
				if (edges.has("surfaceText")) {
					parsedResult.add(edges.get("surfaceText").toString());
				}
			}
			
			
			delegate.processFinish(parsedResult);
		}
	}

}

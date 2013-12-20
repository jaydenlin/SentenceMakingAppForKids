package com.app.data;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import com.app.asyctask.AsyncTaskResponse;
import com.app.translate.SimpleToTraditionText;

import android.os.AsyncTask;
import android.util.Log;

public class WikiData {
	
	private final String TAG=this.getClass().getSimpleName();
	private static WikiData instance;
	private AsyncTaskResponse<String> delegate=null;
	HtmlCleaner htmlCleaner;
	public static WikiData getInstance(){
		if(instance==null){
			instance = new WikiData();
		}
		return instance;
	}
	
	private WikiData(){
		htmlCleaner = new HtmlCleaner();
	}
	
	public void searchWikiData(String searchData,AsyncTaskResponse<String> delegate){
		getWikiDataAsyncTask wikiDataAsyncTask =new getWikiDataAsyncTask(); 
		wikiDataAsyncTask.execute(searchData);
		this.delegate=delegate;
	}
	
	class getWikiDataAsyncTask extends AsyncTask<String, Void, String>{
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpClient client = new DefaultHttpClient();
			try {
				HttpGet get = new HttpGet("http://zh.wikipedia.org/wiki/"+params[0].trim());
				HttpResponse response = client.execute(get);
				HttpEntity resEntity = response.getEntity();
				String result = EntityUtils.toString(resEntity);
				//Log.w(TAG, result);
				return result;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
			
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			String text=parseDescription(result);
			if(!text.equals("")){
				delegate.processFinish(text);
			}
			Log.w(TAG, result);
		}
		
	}
	
	private String parseDescription(String result){
		TagNode rootNode=htmlCleaner.clean(result);
		List<TagNode> Elements = rootNode.getElementListByName("p", true);
		String description=SimpleToTraditionText.StoT(Elements.get(0).getText().toString());
		return description;
	}
}

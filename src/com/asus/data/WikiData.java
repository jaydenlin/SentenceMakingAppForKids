package com.asus.data;

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
import com.asus.asyctask.AsyncTaskResponse;
import android.os.AsyncTask;
import android.util.Log;

public class WikiData {
	
	private final String TAG=this.getClass().getSimpleName();
	private static WikiData instance;
	private AsyncTaskResponse<String> delegate=null;
	HtmlCleaner htmlCleaner;
	public static WikiData getInstance(AsyncTaskResponse<String> delegate){
		if(instance==null){
			instance = new WikiData(delegate);
		}
		return instance;
	}
	
	private WikiData(AsyncTaskResponse<String> delegate){
		this.delegate=delegate;
		htmlCleaner = new HtmlCleaner();
	}
	
	public void getWikiData(String searchData){
		getWikiDataAsyncTask wikiDataAsyncTask =new getWikiDataAsyncTask(); 
		wikiDataAsyncTask.execute(searchData);
	}
	
	class getWikiDataAsyncTask extends AsyncTask<String, Void, String>{
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://zh.wikipedia.org/wiki/"+params[0]);
			try {
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
		return Elements.get(0).getText().toString();
	}
}

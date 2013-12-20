package com.app.translate;

import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class TranslateApi {
	
	public static String translate(String from,String to,String source)
	{
		String api_url;
		try {
			api_url = new StringBuilder("http://fanyi.baidu.com/transapi?from="+from+"&to="+to+"&query=")
			.append(URLEncoder.encode(source,"utf-8")).toString();
			
			HttpClient client=new DefaultHttpClient();
			HttpGet get = new HttpGet(api_url);
			HttpResponse response = client.execute(get);
			HttpEntity resEntity = response.getEntity();
			String json = EntityUtils.toString(resEntity);
			
			Gson gson=new Gson();
			TranslateMode translateMode=gson.fromJson(json, TranslateMode.class);
 
			if(translateMode!=null&&translateMode.getData()!=null&&translateMode.getData().size()==1)
			{
				return translateMode.getData().get(0).getDst();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}

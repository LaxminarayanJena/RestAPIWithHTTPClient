package com.qa.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


public class RestClient {
	//1/Get method without headers
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException
	{
		CloseableHttpClient httpClient=HttpClients.createDefault(); //use only 4.5.3
		HttpGet httpget  = new HttpGet(url);
		CloseableHttpResponse closableHttpResponse=httpClient.execute(httpget); //hit the Geturl		
		return closableHttpResponse;
	}
	
	
	//2/Get method with headers
		public CloseableHttpResponse get(String url,HashMap<String ,String> headerMap) throws ClientProtocolException, IOException
		{
			CloseableHttpClient httpClient=HttpClients.createDefault(); //use only 4.5.3
			HttpGet httpget  = new HttpGet(url);
			
			for(Map.Entry<String, String> entry :headerMap.entrySet())
			{
				httpget.addHeader(entry.getKey(),entry.getValue());
			}
				
			
			
			CloseableHttpResponse closableHttpResponse=httpClient.execute(httpget); //hit the Geturl		
			return closableHttpResponse;
		}

		//B.POST METHOD:
		
		public CloseableHttpResponse post(String url, String entityString,HashMap<String,String> headerMap) throws ClientProtocolException, IOException
		{
			CloseableHttpClient httpClient=HttpClients.createDefault();
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new StringEntity(entityString)); //for payload
			
			//for headers:
			for(Map.Entry<String, String> entry :headerMap.entrySet())
			{
				httppost.addHeader(entry.getKey(),entry.getValue());
			}
			
			CloseableHttpResponse closableHttpResponse=httpClient.execute(httppost);
			return closableHttpResponse;
					
			
		}
}

package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase {
	TestBase testBase;
	String serviceUrl;
	String apiURL;
	String url;
	RestClient restClient;
	CloseableHttpResponse closableHttpResponse;

	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException {
		testBase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		apiURL = prop.getProperty("serviceURL");
		url = serviceUrl + apiURL;

	}

	@Test(priority=1)
	public void getAPITestWithoutHeaders() throws ClientProtocolException, IOException {
		 restClient = new RestClient();
		 closableHttpResponse=restClient.get(url);
		
		//a.statuscode
				int  statusCode=closableHttpResponse.getStatusLine().getStatusCode();
				System.out.println("status code-" +statusCode);
				Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200,"Status code is not 200");
				
				//b.JSON string
				String responseString =EntityUtils.toString(closableHttpResponse.getEntity(),"UTF-8");
				JSONObject responseJson = new JSONObject(responseString); //use specific json version
				System.out.println("response json from api---" +  responseJson);
				
				//--single value assertion
				//per_page
				String perPageValue=TestUtil.getValueByJPath(responseJson, "/per_page");
				System.out.println("value of per page is-" + perPageValue);
				Assert.assertEquals(perPageValue, String.valueOf(3));
				
				//total
				String totalValue=TestUtil.getValueByJPath(responseJson, "/total");
				System.out.println("value of total is-" + totalValue);
				Assert.assertEquals(Integer.parseInt(totalValue), 12);
				
				//get the value from json array
				
				String lastName=TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
				String id=TestUtil.getValueByJPath(responseJson, "/data[0]/id");
				String avatar=TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
				String first_name=TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");
				
				System.out.println(lastName);
				System.out.println(id);
				System.out.println(avatar);
				System.out.println(first_name);
				
				//c.AllHeaders
				Header[] headersArray=closableHttpResponse.getAllHeaders();
				HashMap<String,String> allHeaders = new HashMap<String,String>();
				
				for(Header header: headersArray)
				{
					allHeaders.put(header.getName(),header.getValue());
				}
				System.out.println("headers array-" + allHeaders);
	}

	@Test(priority=2)
	public void getAPITestWithHeaders() throws ClientProtocolException, IOException {
		 restClient = new RestClient();
		 
		 HashMap<String ,String>headerMap = new HashMap<String,String>();
		 headerMap.put("Content-Type", "application/json");
		 headerMap.put("username", "admin");
		 
		 closableHttpResponse=restClient.get(url);
		
		//a.statuscode
				int  statusCode=closableHttpResponse.getStatusLine().getStatusCode();
				System.out.println("status code-" +statusCode);
				Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200,"Status code is not 200");
				
				//b.JSON string
				String responseString =EntityUtils.toString(closableHttpResponse.getEntity(),"UTF-8");
				JSONObject responseJson = new JSONObject(responseString); //use specific json version
				System.out.println("response json from api---" +  responseJson);
				
				//--single value assertion
				//per_page
				String perPageValue=TestUtil.getValueByJPath(responseJson, "/per_page");
				System.out.println("value of per page is-" + perPageValue);
				Assert.assertEquals(perPageValue, String.valueOf(3));
				
				//total
				String totalValue=TestUtil.getValueByJPath(responseJson, "/total");
				System.out.println("value of total is-" + totalValue);
				Assert.assertEquals(Integer.parseInt(totalValue), 12);
				
				//get the value from json array
				
				String lastName=TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
				String id=TestUtil.getValueByJPath(responseJson, "/data[0]/id");
				String avatar=TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
				String first_name=TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");
				
				System.out.println(lastName);
				System.out.println(id);
				System.out.println(avatar);
				System.out.println(first_name);
				
				//c.AllHeaders
				Header[] headersArray=closableHttpResponse.getAllHeaders();
				HashMap<String,String> allHeaders = new HashMap<String,String>();
				
				for(Header header: headersArray)
				{
					allHeaders.put(header.getName(),header.getValue());
				}
				System.out.println("headers array-" + allHeaders);
	}

	
	
	
}

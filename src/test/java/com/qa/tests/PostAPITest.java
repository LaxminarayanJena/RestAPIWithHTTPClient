package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

public class PostAPITest extends TestBase{
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

	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException
	{
		restClient = new RestClient();
		 HashMap<String ,String>headerMap = new HashMap<String,String>();
		 headerMap.put("Content-Type", "application/json");
	
	

	//jackson API:
			ObjectMapper mapper = new ObjectMapper();
			Users users = new Users("ram", "leader"); //expected users obejct
			
	//object to json file: (marshling)
			mapper.writeValue(new File("D:\\Projects\\RestApi\\src\\main\\java\\com\\qa\\data\\users.json"), users);
		
	//object to json in String:
			String usersJsonString=mapper.writeValueAsString(users);
			System.out.println("usersJsonString");
			
	//call the api
			closableHttpResponse=restClient.post(url, usersJsonString, headerMap); 
	
	//validate response from API	
			//1.status code:
			int statusCode=closableHttpResponse.getStatusLine().getStatusCode();
			Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_CODE_201);
			
			//2.JsonString
			String responseString=EntityUtils.toString(closableHttpResponse.getEntity(), "UTF-8");
			JSONObject  responseJson= new JSONObject(responseString);
			System.out.println("The response from API is :"+responseJson);
			
	//json to java object //unmarshelling
			
			Users userResobj=mapper.readValue(responseString, Users.class);  //actual users object
			System.out.println(userResobj);
			
			Assert.assertTrue(users.getName().equals(userResobj.getName()));
			Assert.assertTrue(users.getJob().equals(userResobj.getJob()));
			
			System.out.println(userResobj.getId());
			System.out.println(userResobj.getCreatedAt());
			
	}
	
	
}

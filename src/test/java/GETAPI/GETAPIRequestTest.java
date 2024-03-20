package GETAPI;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GETAPIRequestTest {
	RequestSpecification request;
	
	@BeforeTest
	public void setup() {
		RestAssured.baseURI="https://gorest.co.in";		
		request =RestAssured.given();		
		request.header("Authorization", "Bearer 010d4e13a2801c70433dfde11d4d4efe729193d38955aac178b379b564a9aa5e");
	}
	
	@Test
	public void getAllUsersAPITest() {		
		Response getResponse = request.get("/public/v2/users");
		
		//*------------------------------------*
		getResponse.prettyPrint();		
		int statusCode = getResponse.getStatusCode();
		System.out.println("The status code is :" + statusCode);		
		Assert.assertEquals(statusCode, 200); 
		
		String statusLine = getResponse.statusLine();
		System.out.println("The status Line is :" + statusLine);		
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK"); 
		
		String cacheStatus = getResponse.header("CF-Cache-Status");
		System.out.println("The Cache status value in the Header is :" + cacheStatus);	
		String contentType = getResponse.header("Content-Type");
		System.out.println("The Content Type value in the Header is :" + contentType);
		
		//Fetch all headers
		List<Header> headersList = getResponse.headers().asList();
		System.out.println("The number of Headers is :" + headersList.size());
		
		for(Header h: headersList)
		{
			System.out.println(h.getName() + ":" + h.getValue());
		}
	}
	
	@Test
	public void getAllUsersWithQueryParams() {		
		Response getResponse = request.get("/public/v2/users");request.get("/public/v2/users");

		//*------------------------------------*
		getResponse.prettyPrint();		
		int statusCode = getResponse.getStatusCode();
		System.out.println("The status code is :" + statusCode);		
		Assert.assertEquals(statusCode, 200); 
	}

}

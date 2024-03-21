package AuthAPI;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static io.restassured.RestAssured.*;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AuthTests {
	
	@BeforeTest
	public void allureSetup() {
		RestAssured.filters(new AllureRestAssured());
	}
	
	@Test
	public void verifyAuthUsingJWTToken() {
		RestAssured.baseURI="https://fakestoreapi.com";
		
		String tokenID = given()
		.contentType(ContentType.JSON)
		.body("{\r\n"
				+ "    \"username\": \"mor_2314\",\r\n"
				+ "    \"password\": \"83r5^_\"\r\n"
				+ "}")
		.when()
		.post("/auth/login")
		.then()
		.assertThat().statusCode(200).and().extract().path("token");
		System.out.println("Token is :" +tokenID);
		
		String []tokenarr = tokenID.split("\\.");
		System.out.println("Header-First part of the token is :" + tokenarr[0]);
		System.out.println("Payload-Second part of the token is :" + tokenarr[1]);
		System.out.println("Signature-Third part of the token is :" + tokenarr[2]);		
	}
	
	@Test
	public void verifyAuthUsingBasicAuth() {
		RestAssured.baseURI="https://the-internet.herokuapp.com";
		
		given().log().all()		
		.auth().basic("admin", "admin")		
		.when()
		.get("/basic_auth")
		.then()
		.assertThat().statusCode(200).and()
		.extract().body().asPrettyString();
	}
	
	@Test
	public void verifyAuthUsingPremmptiveAuth() {
		RestAssured.baseURI="https://the-internet.herokuapp.com";
		
		given().log().all()		
		.auth().preemptive().basic("admin", "admin")		
		.when()
		.get("/basic_auth")
		.then()
		.assertThat().statusCode(200).and()
		.extract().body().asPrettyString();
	}
	
	@Test
	public void verifyAuthUsingDigestAuth() {
		RestAssured.baseURI="https://the-internet.herokuapp.com";
		
		given().log().all()		
		.auth().digest("admin", "admin")			
		.when()
		.get("/basic_auth")
		.then()
		.assertThat().statusCode(200).and()
		.extract().body().asPrettyString();
	}
	
	@Test
	public void verifyAuthUsingAPIKeyAuth() {
		RestAssured.baseURI="http://api.weatherapi.com";
		
		Response  getResponse= given().log().all()		
		.queryParam("key", "35e41f9ad3464fa9af381917232809")
		.queryParam("q", "London")
		.queryParam("aqi", "no")
		.when()
		.get("/v1/current.json");
		
		getResponse.then().assertThat().statusCode(200).and()
					.assertThat().body("location.name",equalToIgnoringCase("London")).and()
					.assertThat().body("current.condition.text", equalToIgnoringCase("Partly cloudy"));
		
	}
	
	
	

}

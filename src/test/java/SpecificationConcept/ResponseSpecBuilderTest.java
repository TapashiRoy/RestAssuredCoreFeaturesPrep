package SpecificationConcept;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpecBuilderTest {
	
	public static ResponseSpecification userResponseSpec_200_Success() {
		ResponseSpecification responseSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectStatusCode(200)
					.expectHeader("Server", "cloudflare").build();
		return responseSpec;
	}
	
	public static ResponseSpecification userResponseSpec_200_Success_with_Body() {
		ResponseSpecification responseSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectStatusCode(200)
					.expectHeader("Server", "cloudflare").expectBody("$.size()", equalTo(10)).build();
		return responseSpec;
	}
	
	public static ResponseSpecification userResponseSpec_401_Auth_Fail() {
		ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(401)
				.expectHeader("Server", "cloudflare").build();
	return responseSpec;
	}
	
	@Test
	public void getAllUsers_With_ResponseSpec200_Test() {
		RestAssured.baseURI="https://gorest.co.in";	
		RestAssured.given().header("Authorization", "Bearer 010d4e13a2801c70433dfde11d4d4efe729193d38955aac178b379b564a9aa5e").get("/public/v2/users").then()
			.assertThat().spec(userResponseSpec_200_Success());
	}
	
	@Test
	public void getUsers_With_ResponseSpec401_Test() {
		RestAssured.baseURI="https://gorest.co.in";	
		RestAssured.given().log().all().header("Authorization", "Bearer 0110d4e13a2801c70433dfde11d4d4efe729193d38955aac178b379b564a9aa5e").get("/public/v2/users").then()
		.assertThat().spec(userResponseSpec_401_Auth_Fail());
	}
	
	@Test
	public void getAllUsers_With_ResponseSpec200WithBody_Test() {
		RestAssured.baseURI="https://gorest.co.in";	
		RestAssured.given().log().all().header("Authorization", "Bearer 010d4e13a2801c70433dfde11d4d4efe729193d38955aac178b379b564a9aa5e").get("/public/v2/users").then()
			.assertThat().spec(userResponseSpec_200_Success_with_Body());
	}
	

}

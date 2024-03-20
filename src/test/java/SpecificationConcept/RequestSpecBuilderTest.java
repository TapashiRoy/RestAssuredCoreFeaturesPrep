package SpecificationConcept;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecBuilderTest {
	
	public static RequestSpecification userReqSpec() {
		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://gorest.co.in").and().setContentType(ContentType.JSON).and()
				.addHeader("Authorization", "Bearer 010d4e13a2801c70433dfde11d4d4efe729193d38955aac178b379b564a9aa5e").build();
		return requestSpec;
	}
	
	@Test
	public void getUser_With_RequestSpec() {	
		RestAssured.given().log().all().spec(userReqSpec()).get("/public/v2/users").then().assertThat().statusCode(200);
	}
	
	@Test
	public void getUser_WithParameters_With_RequestSpec() {
		RestAssured.given().log().all().spec(userReqSpec()).queryParam("name", "UserTesting")
			.get("/public/v2/users").then().assertThat().statusCode(200);
	}

}

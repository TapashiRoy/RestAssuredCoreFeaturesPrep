package PUTAPI;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class UpdateUserTest {
	//First we will create a User, fetch the ID and then update it accordingly
	
	private static String randomEmailGenerator() {
		String randomEmail = "APIAutomation" + System.currentTimeMillis() + "@gmail.com";
		return randomEmail;
	}
	
	@Test
	public void updateUserTest() {
		RestAssured.baseURI = "https://gorest.co.in";
		
		UserPOJO postUser = new UserPOJO.UserPOJOBuilder().name("UserTesting").email(randomEmailGenerator()).gender("female")
				.status("active").build();
		Response postRes = given().log().all()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer 010d4e13a2801c70433dfde11d4d4efe729193d38955aac178b379b564a9aa5e")
				.body(postUser) // Serialization is happening here
				.when().log().all().post("/public/v2/users");
		Integer userID = postRes.jsonPath().get("id");
		System.out.println("ID is : " + userID);
		
		System.out.println("-----------------------------------");
		
		//Update the eexisting user via Setters
		postUser.setName("UpdateUser");
		postUser.setGender("male");	
		postUser.setStatus("inactive");
		
		// PUT API to update the same user 
		Response putRes = given().log().all()
			    .contentType(ContentType.JSON)
			   .header("Authorization", "Bearer 010d4e13a2801c70433dfde11d4d4efe729193d38955aac178b379b564a9aa5e")
			   .body(postUser) //Put request is done here
			   .when().log().all().put("/public/v2/users/" +userID);
		putRes.then().assertThat().statusCode(200)
					.and().assertThat().body("id", equalTo(userID))
					.and().body("name", equalTo(postUser.getName()))
					.and().body("gender", equalTo(postUser.getGender()))
					.and().body("status", equalTo(postUser.getStatus()));		
	}
}

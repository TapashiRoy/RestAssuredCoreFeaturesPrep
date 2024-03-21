	package DELETEAPI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteUserTest {
	//Create the user first -->Response Code 201
	//and then delete the user -->Response Code 204
	//Finally fetch the user again -->Response Code 404
	
	private static String randomEmailGenerator() {
		String randomEmail = "APIAutomation" + System.currentTimeMillis() + "@gmail.com";
		return randomEmail;
	}
	
	@Test
	public void deleteUserTest() {
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
		
		// DELETE API to delete the same user 
		Response deleteRes = given().log().all()			   
			   .header("Authorization", "Bearer 010d4e13a2801c70433dfde11d4d4efe729193d38955aac178b379b564a9aa5e")			   
			   .when().log().all().delete("/public/v2/users/" +userID);
		deleteRes.then().assertThat().statusCode(204);
		
		System.out.println("-----------------------------------");	
		
		//GET request to verify the deletion
		Response getRes = given().log().all()			   
		   .header("Authorization", "Bearer 010d4e13a2801c70433dfde11d4d4efe729193d38955aac178b379b564a9aa5e")			   
		   .when().get("/public/v2/users/" +userID);
		getRes.then().assertThat().statusCode(404).and().assertThat().body("message", equalToIgnoringCase("Resource not found"));		
	}

}

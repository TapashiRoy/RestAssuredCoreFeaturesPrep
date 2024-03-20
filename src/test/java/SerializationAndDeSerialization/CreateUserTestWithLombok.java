package SerializationAndDeSerialization;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserTestWithLombok {

	private static String randomEmailGenerator() {
		String randomEmail = "APIAutomation" + System.currentTimeMillis() + "@gmail.com";
		return randomEmail;
	}

	@Test
	public void createUserTest_Serialization_DeSerialization() {
		RestAssured.baseURI = "https://gorest.co.in";
		UserPOJO postUser = new UserPOJO("UserTesting", randomEmailGenerator(), "female", "active");

		Response postRes = given().contentType(ContentType.JSON)
				.header("Authorization", "Bearer 010d4e13a2801c70433dfde11d4d4efe729193d38955aac178b379b564a9aa5e")
				.body(postUser) // Serialization is happening here
				.when().log().all().post("/public/v2/users");
		Integer userID = postRes.jsonPath().get("id");
		System.out.println("ID is : " + userID);

		// GET API to get the same user in the response
		Response getRes = given()
				.header("Authorization", "Bearer 010d4e13a2801c70433dfde11d4d4efe729193d38955aac178b379b564a9aa5e")
				.when().log().all().get("/public/v2/users/" + userID);

		// DeSerialization method
		ObjectMapper mapper = new ObjectMapper();
		try {
			UserPOJO getUser = mapper.readValue(getRes.getBody().asString(), UserPOJO.class); // DeSerialization is happening
																						// here
			// Printing the values after de-Serialization
			System.out.println(" ID is :" + getUser.getId() + " Name is : " + getUser.getName() + " Email is : "
					+ getUser.getEmail() + " Gender is : " + getUser.getGender() + " Status is : "
					+ getUser.getStatus());
			// Assertions
			Assert.assertEquals(getUser.getName(), postUser.getName());
			Assert.assertEquals(getUser.getId(), userID);
			Assert.assertEquals(getUser.getEmail(), postUser.getEmail());
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void createUserTest_Serialization_DeSerialization_BuilderPattern() {
		//If we use Builder, we don't need parameterized constructor in POJO class
		//It is more refined approach
		
		UserPOJO postUser = new UserPOJO.UserPOJOBuilder().name("UserTesting").email(randomEmailGenerator()).gender("female")
				.status("active").build();
		Response postRes = given().contentType(ContentType.JSON)
				.header("Authorization", "Bearer 010d4e13a2801c70433dfde11d4d4efe729193d38955aac178b379b564a9aa5e")
				.body(postUser) // Serialization is happening here
				.when().log().all().post("/public/v2/users");
		Integer userID = postRes.jsonPath().get("id");
		System.out.println("ID is : " + userID);

		// GET API to get the same user in the response
		Response getRes = given()
				.header("Authorization", "Bearer 010d4e13a2801c70433dfde11d4d4efe729193d38955aac178b379b564a9aa5e")
				.when().log().all().get("/public/v2/users/" + userID);

		// DeSerialization method
		ObjectMapper mapper = new ObjectMapper();
		try {
			UserPOJO getUser = mapper.readValue(getRes.getBody().asString(), UserPOJO.class); // DeSerialization is happening
																						// here
			// Printing the values after de-Serialization
			System.out.println(" ID is :" + getUser.getId() + " Name is : " + getUser.getName() + " Email is : "
					+ getUser.getEmail() + " Gender is : " + getUser.getGender() + " Status is : "
					+ getUser.getStatus());
			// Assertions
			Assert.assertEquals(getUser.getName(), postUser.getName());
			Assert.assertEquals(getUser.getId(), userID);
			Assert.assertEquals(getUser.getEmail(), postUser.getEmail());
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}

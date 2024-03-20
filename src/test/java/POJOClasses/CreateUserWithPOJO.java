package POJOClasses;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;

public class CreateUserWithPOJO {
	
	private String randomemailgenerator() {
		String email = "APIautomation" + System.currentTimeMillis() + "@gmail.com";
		return email; 
	}
	
	@Test
	public void createAndGetUser_UsingPOJO() {
		RestAssured.baseURI = "https://gorest.co.in";
		CreateUser user = new CreateUser("UserTesting",randomemailgenerator(),"female","active");
		
		//Create the User using POST call
		int id = given().log().all()
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer 010d4e13a2801c70433dfde11d4d4efe729193d38955aac178b379b564a9aa5e")
			.body(user)
		.when()
			.post("/public/v2/users")
		.then().log().all()
			.assertThat().statusCode(201).and()
				.body("name", equalTo(user.getName())).and()
				.body("status", equalTo(user.getStatus()))
				.extract().path("id");
		System.out.println("User ID is : " + id);
		
		//Verify the user using GET call
		
		given().log().all()
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer 010d4e13a2801c70433dfde11d4d4efe729193d38955aac178b379b564a9aa5e")
		.when()
			.get("/public/v2/users/" +id)
		.then().log().all()
			.assertThat().statusCode(200).and()
			.body("name", equalTo(user.getName())).and()
			.body("id", equalTo(id));	
	}
}

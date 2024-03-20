package POJOClasses;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BookingAuthTestWithPOJO {

	@Test
	public void getAuthToken_JSON_AsPOJO() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		Credentials cred= new Credentials("admin", "password123");

		Response res = given().log().all()
				.contentType(ContentType.JSON)
				.body(cred)
				.when().log().all()
				.post("/auth");

		String token = res.then().log().all()
						.assertThat().statusCode(200).extract().path("token");
		System.out.println("Token is : " + token);
		Assert.assertNotNull(token);
	}
}

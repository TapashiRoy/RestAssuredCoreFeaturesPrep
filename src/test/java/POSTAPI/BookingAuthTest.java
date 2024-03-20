package POSTAPI;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BookingAuthTest {

	@Test
	public void getAuthToken_JSON_AsString() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";

		Response res = given().contentType(ContentType.JSON)
				.body("{\r\n" + "    \"username\" : \"admin\",\r\n" + "    \"password\" : \"password123\"\r\n" + "}")
				.when().post("/auth");

		String token = res.then().assertThat().statusCode(200).extract().path("token");
		System.out.println("Token is : " + token);
		Assert.assertNotNull(token);
	}

	@Test
	public void getAuthToken_JSON_AsFile() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";

		Response res = given().contentType(ContentType.JSON)
				.body(new File("./src/test/resources/testdata/BasicAuth.json")).when().post("/auth");

		String token = res.then().assertThat().statusCode(200).extract().path("token");
		System.out.println("Token is : " + token);
		Assert.assertNotNull(token);
	}
	
	@Test
	public void addUserAPITest() {
		//Create the Booking using POST call	
		
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		
		Response postRes = given().log().all().contentType(ContentType.JSON).body(new File("./src/test/resources/testdata/CreateBooking.json"))
							.when().post("/booking");
		int bookingID = postRes.then().assertThat().statusCode(200).extract().path("bookingid");
		System.out.println("BookingID is : " + bookingID);
		Assert.assertNotNull(bookingID);
		
		//Verify the same booking id through GET call
		Response getRes = given().when().pathParam("bookid", bookingID)
				.log().all().get("/booking/{bookid}");
		
		getRes.then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and()
				.body("firstname", is(notNullValue())).and().body("lastname", is(notNullValue())).and()
				.body("firstname", equalTo("Jim")).and().body("lastname", equalTo("Brown"));
	}

}

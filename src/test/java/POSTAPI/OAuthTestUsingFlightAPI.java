package POSTAPI;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OAuthTestUsingFlightAPI {

	static String accessToken;

	@BeforeMethod
	public void getAuthToken() {
		RestAssured.baseURI = "https://test.api.amadeus.com";

		accessToken = given().log().all().header("Content-Type", "application/x-www-form-urlencoded")
				.formParam("grant_type", "client_credentials")
				.formParam("client_id", "nfXDZqvRYZ5YgdkBWThkPASivqMpy6Kj")
				.formParam("client_secret", "8Nw7MrQxgm2KodQK").when().post("/v1/security/oauth2/token").then()
				.assertThat().statusCode(200).extract().path("access_token");
		System.out.println(accessToken);
	}

	@Test
	public void getFlightDetails() {
		Response res = given().log().all().header("Authorization", "Bearer " + accessToken).queryParam("origin", "PAR")
				.and().queryParam("maxPrice", 200).when().get("/v1/shopping/flight-destinations").then().assertThat()
				.statusCode(200).and().extract().response();

		JsonPath js = res.jsonPath();
		String type = js.get("data[0].type");
		System.out.println("The Type is : " + type);
	}

}

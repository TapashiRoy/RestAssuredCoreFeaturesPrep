package SchemaValidation;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class SchemaValidationTest {

	@Test
	public void createUserAPISchemaValidationTest() {
		RestAssured.baseURI = "https://gorest.co.in";

		given().log().all().contentType(ContentType.JSON)
				.header("Authorization", "Bearer 010d4e13a2801c70433dfde11d4d4efe729193d38955aac178b379b564a9aa5e")
				.body(new File("./src/test/resources/testdata/adduser.json")).when().post("/public/v2/users").then()
				.assertThat().body(matchesJsonSchemaInClasspath("gorestcreateuserschema.json"));

	}

	@Test
	public void getUserAPISchemaValidationTest() {
		RestAssured.baseURI = "https://gorest.co.in";

		given().log().all().contentType(ContentType.JSON)
				.header("Authorization", "Bearer 010d4e13a2801c70433dfde11d4d4efe729193d38955aac178b379b564a9aa5e")
				.when().get("/public/v2/users").then().assertThat().statusCode(200).and()
				.body(matchesJsonSchemaInClasspath("gorestgetuserschema.json"));

	}

}

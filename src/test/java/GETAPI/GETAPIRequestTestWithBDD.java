package GETAPI;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

public class GETAPIRequestTestWithBDD {

	@Test
	public void getProductsTest() {
		Response res = given().when().log().all().get("https://fakestoreapi.com/products");
		res.body().prettyPrint();
		res.then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and()
				.header("Connection", "keep-alive").and().body("$.size()", equalTo(20)).and()
				.body("id", is(notNullValue())).and().body("title", is(notNullValue())).and()
				.body("title", hasItem("DANVOUY Womens T Shirt Casual Cotton Short"));

	}

	@Test
	public void getAllUsers() {
		RestAssured.baseURI = "https://gorest.co.in";

		Response res = given()
				.header("Authorization", "Bearer 010d4e13a2801c70433dfde11d4d4efe729193d38955aac178b379b564a9aa5e")
				.when().get("/public/v2/users");
		res.body().prettyPrint();
		res.then().assertThat().statusCode(200).and().statusLine("HTTP/1.1 200 OK").and().contentType(ContentType.JSON)
				.and().body("$.size()", equalTo(10));
	}

	@Test
	public void getAllProductsWithALimit() {
		RestAssured.baseURI = "https://fakestoreapi.com";

		Response res = given().queryParam("limit", "5").when().get("/products");
		res.body().prettyPrint();
		res.then().assertThat().statusCode(200).and().statusLine("HTTP/1.1 200 OK").and().contentType(ContentType.JSON)
				.and().body("$.size()", equalTo(5));
	}

	@Test
	public void getProductDataAPI_ExtractBodyWithJSONArray() {
		RestAssured.baseURI = "https://fakestoreapi.com";

		Response res = given().queryParam("limit", "10").when().get("/products");
		res.body().prettyPrint();

		JsonPath js = res.jsonPath();
		int firstProdID = js.getInt("[0].id");
		System.out.println("First Product ID is: " + firstProdID);

		String firstProdTitle = js.getString("[0].title");
		System.out.println("First Product Title is: " + firstProdTitle);

		float firstProdCount = js.getFloat("[0].rating.count");
		System.out.println("First Product Rating Count is: " + firstProdCount);

		// Finding the entire list of Ids, Titles

		List<Integer> idList = js.getList("id");
		List<String> titleList = js.getList("title");
		List<Float> rateList = js.getList("rating.rate", Float.class);
		List<Integer> countList = js.getList("rating.count");

		for (int i = 0; i < idList.size(); i++) {
			int id = idList.get(i);
			String title = titleList.get(i);
			float rate = rateList.get(i);
			int count = countList.get(i);

			System.out.println("ID : " + id + " Title : " + title + " Rate :" + rate + " Count :" + count);
		}

	}

	@Test
	public void getAllProducts_UsingExtractMethod() {
		RestAssured.baseURI = "https://fakestoreapi.com";

		Response res = given().when().get("/products/1");
		res.body().prettyPrint();
		
		int userID= res.then().extract().path("id");
		String title = res.then().extract().path("title");
		System.out.println("ID : " + userID);
		System.out.println("Title : " + title);		
	}

}

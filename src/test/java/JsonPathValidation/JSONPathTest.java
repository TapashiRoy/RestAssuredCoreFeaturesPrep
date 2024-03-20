package JsonPathValidation;

import org.testng.annotations.Test;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class JSONPathTest {

	@Test
	public void getCircuitDataWithJSONPath() {
		RestAssured.baseURI = "http://ergast.com";

		Response res = given().when().pathParam("year", "2017").get("/api/f1/{year}/circuits.json");

		String jsonResponse = res.asString();
		System.out.println(jsonResponse);

		// List of all the countries
		List<String> countryList = JsonPath.read(jsonResponse, "$..Circuits..country");
		int number = countryList.size();
		System.out.println("Number of countries are: " + number);
		for (int i = 0; i < number; i++) {
			System.out.println(countryList.get(i));
		}

		// Find the MRData CircuitURL
		String circuitUrl = JsonPath.read(jsonResponse, "$.MRData.url");
		System.out.println("The MRData Circuit URL is : " + circuitUrl);

		// Find the Season
		String season = JsonPath.read(jsonResponse, "$.MRData.CircuitTable.season");
		System.out.println("The Circuit Season is : " + season);

	}

	@Test
	public void getAllProductsTest() {
		RestAssured.baseURI = "https://fakestoreapi.com";

		Response res = given().when().log().all().get("/products");
		String jsonResponse = res.asString();
		System.out.println(jsonResponse);

		// Find All the Product Titles
		List<String> titleNames = JsonPath.read(jsonResponse, "$[*].title");
		System.out.println("Number of Titles are: " + titleNames.size());
		System.out.println(titleNames);

		System.out.println("------------------------");
		// Fetch titles and prices when category is Jewelry
		List<Map<String, Object>> titleAndPriceData = JsonPath.read(jsonResponse,
				"$[?(@.category=='jewelery')].[\"title\",\"price\"]");
		for (Map<String, Object> list : titleAndPriceData) {
			String title = (String) list.get("title");
			Object price = (Object) list.get("price");

			System.out.println("Title is : " + title);
			System.out.println("Price is : " + price);
		}

		System.out.println("------------------------");
		// Fetch title, price and id when category is electronics
		List<Map<String, Object>> electronicsList = JsonPath.read(jsonResponse,
				"$[?(@.category=='electronics')].[\"title\",\"price\",\"id\"]");
		for (Map<String, Object> list : electronicsList) {
			String title = (String) list.get("title");
			Object price = (Object) list.get("price");
			Integer id = (Integer) list.get("id");

			System.out.println("Title is : " + title);
			System.out.println("Price is : " + price);
			System.out.println("ID is : " + id);
		}

	}

}

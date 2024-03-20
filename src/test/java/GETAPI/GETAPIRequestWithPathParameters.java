package GETAPI;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GETAPIRequestWithPathParameters {
	@Test
	public void getCircuitData() {
		RestAssured.baseURI = "http://ergast.com";

		Response res = given().when().pathParam("year", "2017")
				.get("/api/f1/{year}/circuits.json");
		res.body().prettyPrint();
		
		JsonPath js = res.jsonPath();
		res.then().assertThat().statusCode(200).and().body("MRData.CircuitTable.season", equalTo("2017"))
				.and().body("MRData.CircuitTable.Circuits.circuitId", hasSize(20));
		String series = res.then().extract().path("MRData.series");
		System.out.println("Series : " + series);
		
		List<String> circuitNames = js.getList("MRData.CircuitTable.Circuits.circuitName");
		for(int i=0;i<circuitNames.size();i ++) {
			String circuitName = circuitNames.get(i);
			System.out.println("circuit Name : " + circuitName);			
		}
		
	}
	
	@DataProvider
	public Object[][] getCircuitYearData() {
		return new Object[][] {
				{"2016", 21},
				{"2017", 20}						
		}; 
	}
	
	@Test(dataProvider = "getCircuitYearData")
	public void getCircuitDataWithDataProviders(String circuityear, int value) {
		RestAssured.baseURI = "http://ergast.com";

		Response res = given().when().pathParam("year", circuityear)
				.get("/api/f1/{year}/circuits.json");
		res.body().prettyPrint();
		
		JsonPath js = res.jsonPath();
		res.then().assertThat().statusCode(200).and().body("MRData.CircuitTable.season", equalTo(circuityear))
				.and().body("MRData.CircuitTable.Circuits.circuitId", hasSize(value));
		String series = res.then().extract().path("MRData.series");
		System.out.println("Series : " + series);
		
		List<String> circuitNames = js.getList("MRData.CircuitTable.Circuits.circuitName");
		for(int i=0;i<circuitNames.size();i ++) {
			String circuitName = circuitNames.get(i);
			System.out.println("circuit Name : " + circuitName);			
		}
		
	}

}

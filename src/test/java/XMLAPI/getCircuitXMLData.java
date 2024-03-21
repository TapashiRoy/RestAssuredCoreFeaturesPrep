package XMLAPI;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class getCircuitXMLData {
	
	@Test
	public void getCircuitData() {
		RestAssured.baseURI = "http://ergast.com";
		Response res = given().when().pathParam("year", "2017")
				.get("/api/f1/{year}/circuits.xml")
				.then().extract().response();
		
		String responseBody = res.body().asString();
		System.out.println(responseBody);
		
		//Create objects of XMLPath
		XmlPath xmlpath = new XmlPath(responseBody);
		List<String> curcuitNames = xmlpath.getList("MRData.CircuitTable.Circuit.CircuitName");		
		for(String p : curcuitNames) {
			System.out.println("The circuit Names are :" +  p);
		}
		
		System.out.println("-------------------------------");
		
		List<String> curcuitIDs = xmlpath.getList("MRData.CircuitTable.Circuit.@circuitId");
		for(String p : curcuitIDs) {
			System.out.println("The circuit ID is :" +  p);
		}
		
		System.out.println("-------------------------------");
		
		int circuitNameCount = xmlpath.getList("MRData.CircuitTable.Circuit.CircuitName").size();
		System.out.println("Circuit Name Count is: " + circuitNameCount );
		
		System.out.println("-------------------------------");
		
		//Query for finding the locality where circuit location is America
		// ** does DeepScan
		String locality = xmlpath.get("**.findAll {it.@circuitId=='americas'}.Location.Locality");
		System.out.println("Locality Name is: " + locality );
		
		String latitudeValue = xmlpath.get("**.findAll {it.@circuitId=='americas'}.Location.@lat");
		System.out.println("Latitide value is: " + latitudeValue );
		String longitudeValue = xmlpath.get("**.findAll {it.@circuitId=='americas'}.Location.@long");
		System.out.println("Latitide value is: " + longitudeValue );
		
		//Url where circuit id is americas
		String url =xmlpath.get("**.findAll {it.@circuitId=='americas'}.@url");
		System.out.println("URL value is: " + url );
	}

}

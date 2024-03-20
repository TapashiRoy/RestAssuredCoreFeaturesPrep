package Lombok;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProductAPITest {
	
	@Test
	public void getAllProductsTest_DeserializationUsingPOJO() {
		RestAssured.baseURI = "https://fakestoreapi.com";
		
		Response res = given().when().get("/products");
		//JSON to POJO mapping - Deserialization
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			//Response contains header, cookies, we specifically need body to work on
			GetAllProductsResponse productResponse[] = mapper.readValue(res.getBody().asString(), GetAllProductsResponse[].class);//JSON->JAVA Object
			for(GetAllProductsResponse p :productResponse) {
				System.out.println("ID is : " + p.getId());
				System.out.println("Title is : " + p.getTitle());
				System.out.println("Price is : " + p.getPrice());
				System.out.println("Description is : " + p.getdescription());
				System.out.println("Category is : " + p.getCategory());
				System.out.println("Image is : " + p.getImage());
				System.out.println("Rate is : " + p.getRating().getRate());
				System.out.println("Count is : " + p.getRating().getCount());
				System.out.println("-------------------------------------------");
			}			
			
		} catch (JsonMappingException e) {			
			e.printStackTrace();
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void getAllProductsTest_DeserializationUsingLombok() {
		RestAssured.baseURI = "https://fakestoreapi.com";
		
		Response res = given().when().get("/products");
		//JSON to POJO mapping - Deserialization
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			GetAllProductsLombok productResponse[] = mapper.readValue(res.getBody().asString(), GetAllProductsLombok[].class);	
			for(GetAllProductsLombok p :productResponse) {
				System.out.println("ID is : " + p.getId());
				System.out.println("Title is : " + p.getTitle());
				System.out.println("Price is : " + p.getPrice());
				System.out.println("Description is : " + p.getDescription());
				System.out.println("Category is : " + p.getCategory());
				System.out.println("Image is : " + p.getImage());
				System.out.println("Rate is : " + p.getRating().getRate());
				System.out.println("Count is : " + p.getRating().getCount());
				System.out.println("-------------------------------------------");
			}				
		} catch (JsonMappingException e) {			
			e.printStackTrace();
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		}
		
	}
}

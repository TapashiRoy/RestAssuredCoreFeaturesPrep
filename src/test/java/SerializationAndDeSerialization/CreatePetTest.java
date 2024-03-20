package SerializationAndDeSerialization;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import SerializationAndDeSerialization.PetDetailsPOJO.Category;
import SerializationAndDeSerialization.PetDetailsPOJO.Tag;

import static io.restassured.RestAssured.*;

import java.util.Arrays;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreatePetTest {
	
	@Test
	public void createPetTest_Serialization_DeSerialization() {
		RestAssured.baseURI = "https://petstore.swagger.io";
		
		Category category = new Category(10, "Bull Dog");
		Tag tag = new Tag(10, "Pet Dog");
		List<Tag> tags = Arrays.asList(tag);
		List<String> photoUrls = Arrays.asList("https://www.dog.com","https://www.dog1.com");
		
		PetDetailsPOJO postpetDetails = new PetDetailsPOJO(110, category, "doggie",photoUrls,tags,"available");		
		Response postRes= given()
			.contentType(ContentType.JSON)
			.body(postpetDetails)      // Serialization is happening here
		.when()
			.post("/v2/pet");
		postRes.prettyPrint();
		postRes.then().assertThat().statusCode(200);
		
		// DeSerialization method
		ObjectMapper mapper = new ObjectMapper();
		try {
			PetDetailsPOJO getPetDetails = mapper.readValue(postRes.body().asString(),PetDetailsPOJO.class ); // DeSerialization is happening here
			// Printing the values after de-Serialization
			System.out.println("ID is :" + getPetDetails.getId() + " Name is : " + getPetDetails.getName() + " Status is : "
								+ getPetDetails.getStatus());
			System.out.println("Category Details are :" + getPetDetails.getCategory().getId() + " & " + getPetDetails.getCategory().getName() );
			System.out.println("PhotoURL Details are :" + getPetDetails.getPhotoUrls());
			System.out.println("Tag Details are :" + getPetDetails.getTags().get(0).getName() + " & "+ getPetDetails.getTags().get(0).getId() );
			
			// Assertions
			Assert.assertEquals(postpetDetails.getId(), getPetDetails.getId());
			Assert.assertEquals(postpetDetails.getName(), getPetDetails.getName());			
			Assert.assertEquals(postpetDetails.getStatus(), getPetDetails.getStatus());
			Assert.assertEquals(postpetDetails.getCategory().getId(), getPetDetails.getCategory().getId());
			Assert.assertEquals(postpetDetails.getCategory().getId(), getPetDetails.getCategory().getId());
			Assert.assertEquals(postpetDetails.getCategory().getName(),getPetDetails.getCategory().getName());
			Assert.assertEquals(postpetDetails.getPhotoUrls(), getPetDetails.getPhotoUrls());
			Assert.assertEquals(postpetDetails.getTags().get(0).getName(), getPetDetails.getTags().get(0).getName());
			Assert.assertEquals(postpetDetails.getTags().get(0).getId(), getPetDetails.getTags().get(0).getId());
			
		} catch (JsonMappingException e) {		
			e.printStackTrace();
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		}
	}
	
	@Test
	public void createPetTest_Serialization_DeSerialization_UsingBuilderPattern() {
		RestAssured.baseURI = "https://petstore.swagger.io";
		
		Category category = new Category.CategoryBuilder()
									.id(10)
									.name("Bull Dog")
									.build();
		Tag tag = new Tag.TagBuilder()
						.id(10)
						.name("Pet Dog")
						.build();
		
		List<Tag> tags = Arrays.asList(tag);
		List<String> photoUrls = Arrays.asList("https://www.dog.com","https://www.dog1.com");
		
		PetDetailsPOJO postpetDetails = new PetDetailsPOJO
									.PetDetailsPOJOBuilder()
									.id(110)
									.category(category)
									.name("doggie")
									.photoUrls(photoUrls)
									.tags(tags)
									.status("available")
									.build();			
		Response postRes= given()
			.contentType(ContentType.JSON)
			.body(postpetDetails)      // Serialization is happening here
		.when()
			.post("/v2/pet");
		postRes.prettyPrint();
		postRes.then().assertThat().statusCode(200);
		
		// DeSerialization method
		ObjectMapper mapper = new ObjectMapper();
		try {
			PetDetailsPOJO getPetDetails = mapper.readValue(postRes.body().asString(),PetDetailsPOJO.class ); // DeSerialization is happening here
			// Printing the values after de-Serialization
			System.out.println("ID is :" + getPetDetails.getId() + " Name is : " + getPetDetails.getName() + " Status is : "
								+ getPetDetails.getStatus());
			System.out.println("Category Details are :" + getPetDetails.getCategory().getId() + " & " + getPetDetails.getCategory().getName() );
			System.out.println("PhotoURL Details are :" + getPetDetails.getPhotoUrls());
			System.out.println("Tag Details are :" + getPetDetails.getTags().get(0).getName() + " & "+ getPetDetails.getTags().get(0).getId() );
			
			// Assertions
			Assert.assertEquals(postpetDetails.getId(), getPetDetails.getId());
			Assert.assertEquals(postpetDetails.getName(), getPetDetails.getName());			
			Assert.assertEquals(postpetDetails.getStatus(), getPetDetails.getStatus());
			Assert.assertEquals(postpetDetails.getCategory().getId(), getPetDetails.getCategory().getId());
			Assert.assertEquals(postpetDetails.getCategory().getId(), getPetDetails.getCategory().getId());
			Assert.assertEquals(postpetDetails.getCategory().getName(),getPetDetails.getCategory().getName());
			Assert.assertEquals(postpetDetails.getPhotoUrls(), getPetDetails.getPhotoUrls());
			Assert.assertEquals(postpetDetails.getTags().get(0).getName(), getPetDetails.getTags().get(0).getName());
			Assert.assertEquals(postpetDetails.getTags().get(0).getId(), getPetDetails.getTags().get(0).getId());
			
		} catch (JsonMappingException e) {		
			e.printStackTrace();
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		}
	}

}

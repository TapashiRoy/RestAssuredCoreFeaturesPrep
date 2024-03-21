package MultiBody;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.io.File;

public class MultiBodyAPUITest {
	
	@Test
	public void bodyWithTextTest() {
		RestAssured.baseURI="http://httpbin.org";
		String payload ="This is for testing";
		
		given().log().all()
			   .contentType(ContentType.TEXT)
			   .body(payload)
		.when()
			   .post("/post")
	    .then()
	    		.assertThat().statusCode(200).and()
	    		.body("data", containsString(payload)).and()
	    		.body("url", containsString("http://httpbin.org/post"));		
	}
	
	@Test
	public void bodyWithXMLData() {
		RestAssured.baseURI="http://httpbin.org";
		String payload ="<!DOCTYPE html>\r\n"
				+ "<!--[if IE]><![endif]-->\r\n"
				+ "<!--[if IE 8 ]><html dir=\"ltr\" lang=\"en\" class=\"ie8\"><![endif]-->\r\n"
				+ "<!--[if IE 9 ]><html dir=\"ltr\" lang=\"en\" class=\"ie9\"><![endif]-->\r\n"
				+ "<!--[if (gt IE 9)|!(IE)]><!-->\r\n"
				+ "<html dir=\"ltr\" lang=\"en\">\r\n"
				+ "<!--<![endif]-->\r\n"
				+ "<head>\r\n"
				+ "<meta charset=\"UTF-8\" />\r\n"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
				+ "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
				+ "<title>Your Store</title>\r\n"
				+ "</head>\r\n"
				+ "</html>";
		
		given().log().all()
			   .contentType(ContentType.HTML)
			   .body(payload)
		.when()
			   .post("/post")
	    .then()
	    		.assertThat().statusCode(200).and()
	    		.body("data", containsString("Your Store")).and()
	    		.body("url", containsString("http://httpbin.org/post"));		
	}
	
	@Test
	public void bodyWithPDFData() {
		RestAssured.baseURI="http://httpbin.org";
		given().log().all()
		   .contentType(ContentType.MULTIPART)
		   .multiPart("name","testing")
		   .multiPart("filename", new File("C:\\Users\\lifei\\OneDrive\\Desktop\\Challan-Admission-2015-16.pdf"))
		.when()
		   .post("/post")
        .then()
    		.assertThat().statusCode(200);
	}
	
	@Test
	public void bodyWithBinaryFile() {
		RestAssured.baseURI="http://httpbin.org";
		given().log().all()
		   .header("Content-Type", "image/gif")
		   .body(new File("C:\\Users\\lifei\\OneDrive\\Desktop\\DS.gif"))		   
		.when()
		   .post("/post")
        .then()
    		.assertThat().statusCode(200);
	}
	

}

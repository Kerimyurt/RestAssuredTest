import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import files.payload;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import jdk.jfr.Description;

public class APITestCase {

	@Test
	@Description("Check if the API starts with an empty store.")
	public void testDoRequestForEmptyResponse() {
		given()
		   .contentType("application/json")
		.when()
		   .get("/api/books/")
		.then()
		   .assertThat().statusCode(200).body("isEmpty()", Matchers.is(true));
		
	}
	
	@Test
	@Description("Check if author are required field.")
	public void testDoRequestToSeeMissingAuthorFieldWarning() {
		given()
		   .contentType("application/json")
		   .body(payload.MissingAuthorField())
		.when()
		   .put("/api/books/")
		.then()
		   .assertThat().statusCode(400).body("error", equalTo("Field 'author' is required"));
		
	}
	
	@Test
	@Description("Check if title is required field.")
	public void testDoRequestToSeeMissingTitleFieldWarning() {
		given()
		   .contentType("application/json")
		   .body(payload.MissingTitleField())
		.when()
		   .put("/api/books/")
		.then()
		   .assertThat().statusCode(400).body("error", equalTo("Field 'title' is required"));
		
	}
	
	@Test
	@Description("Check if author cannot be empty.")
	public void testDoRequestToSeeEmptyAuthorFieldWarning() {
		given()
		   .contentType("application/json")
		   .body(payload.EmptyAuthorField())
		.when()
		   .put("/api/books/")
		.then()
		   .assertThat().statusCode(400).body("error", equalTo("Field 'author' cannot be empty."));
		
	}
	
	@Test
	@Description("Check if title cannot be empty.")
	public void testDoRequestToSeeEmptytitleFieldWarning() {
		given()
		   .contentType("application/json")
		   .body(payload.MissingTitleField())
		.when()
		   .put("/api/books/")
		.then()
		   .assertThat().statusCode(400).body("error", equalTo("Field 'title' cannot be empty."));
		
	}
	
	@Test
	@Description("Check if the id field is read−only.")
	public void testDoRequestToVerifyIdFieldIsReadOnly() {
		given()
		   .contentType("application/json")
		   .body(payload.ContainsIdField())
		.when()
		   .put("/api/books/")
		.then()
		   .assertThat().statusCode(400);
		
	}
	
	@Test
	@Description("Check if you can create a new book via PUT.")
	public void testDoRequestForCreatingANewBook() {
		// Create a new book
		given()
		   .contentType("application/json")
		   .body(payload.RightPayload())
		.when()
		   .put("/api/books/")
		.then()
		   .assertThat().statusCode(200);
		
		// Check if this book created successfully
		
		String response =
		given()
		   .contentType("application/json")
		   .body(payload.RightPayload())
		.when()
		   .get("/api/books/1")
		.then()
		   .assertThat().statusCode(200)
		.extract()
		   .response().asString();
		
		JsonPath parsedJson = new JsonPath(response);
		
		String author = parsedJson.getString("author");
		String title = parsedJson.getString("title");
		
		JsonPath parsedRightPayload = new JsonPath(payload.RightPayload());
		String currentAuthor = parsedRightPayload.getString("author");
		String currentTitle = parsedRightPayload.getString("title");
		
		assertThat(author, equalTo(currentAuthor));
		assertThat(title, equalTo(currentTitle));
	}
	
	@Test
	@Description("Check if you cannot create a duplicate book.")
	public void testDoRequestToVerifyDuplicatedBookCanNotBeCreated() {
		for(int i = 0; i < 2; i++) {
			if(i==0) {
				given()
				   .contentType("application/json")
				   .body(payload.RightPayload())
				.when()
				   .put("/api/books/")
				.then()
				   .assertThat().statusCode(200);
			}
			else {
				given()
				   .contentType("application/json")
				   .body(payload.RightPayload())
				.when()
				   .put("/api/books/")
				.then()
				   .assertThat().statusCode(400).body("error", equalTo("Another book with similar title and author already exists."));
				
			}
			
		}
		
	}

}

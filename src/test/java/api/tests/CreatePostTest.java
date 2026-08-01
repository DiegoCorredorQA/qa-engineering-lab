package api.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreatePostTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void shouldCreatePost() {
        String requestBody = """
                {
                  "title": "Learning REST Assured",
                  "body": "Creating a post using REST Assured",
                  "userId": 1
                }
                """;

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .log().all()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", equalTo(101))
                .body("title", equalTo("Learning REST Assured"))
                .body("body", equalTo(
                        "Creating a post using REST Assured"
                ))
                .body("userId", equalTo(1))
                .body("id", notNullValue());
    }
}
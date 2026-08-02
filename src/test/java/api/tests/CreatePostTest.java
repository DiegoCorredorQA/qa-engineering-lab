package api.tests;

import api.specifications.RequestSpecs;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreatePostTest {
    private RequestSpecification requestSpec;

    @BeforeClass
    public void setUp() {
        requestSpec = RequestSpecs.jsonPlaceholderRequestSpec();
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
                .spec(requestSpec)
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
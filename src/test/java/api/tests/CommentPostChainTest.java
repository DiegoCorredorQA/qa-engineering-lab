package api.tests;

import api.specifications.RequestSpecs;
import api.specifications.ResponseSpecs;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CommentPostChainTest {
    private RequestSpecification requestSpec;
    private ResponseSpecification successfulResponseSpec;

    @BeforeClass
    public void setUp() {
        requestSpec = RequestSpecs.jsonPlaceholderRequestSpec();
        successfulResponseSpec = ResponseSpecs.successfulJsonResponseSpec();
    }



    @Test
    public void shouldGetPostAssociatedWithComment() {
        Response commentResponse =
                given()
                        .spec(requestSpec)
                        .when()
                        .get("/comments/{id}", 1)
                        .then()
                        .spec(successfulResponseSpec)
                        .body("email", notNullValue())
                        .body("body", not(emptyString()))
                        .extract()
                        .response();

        int postId = commentResponse.jsonPath().getInt("postId");

        System.out.println("Extracted postId: " + postId);

        given()
                .spec(requestSpec)
                .when()
                .get("/posts/{postId}", postId)
                .then()
                .spec(successfulResponseSpec)
                .body("id", equalTo(postId))
                .body("title", not(emptyString()));
    }
}
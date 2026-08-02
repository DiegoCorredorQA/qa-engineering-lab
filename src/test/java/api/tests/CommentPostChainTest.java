package api.tests;

import api.specifications.RequestSpecs;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.emptyString;

public class CommentPostChainTest {
    private RequestSpecification requestSpec;

    @BeforeClass
    public void setUp() {
        requestSpec = RequestSpecs.jsonPlaceholderRequestSpec();
    }

    @Test
    public void shouldGetPostAssociatedWithComment() {
        Response commentResponse =
                given()
                        .spec(requestSpec)
                        .when()
                        .get("/comments/{id}", 1)
                        .then()
                        .log().all()
                        .statusCode(200)
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
                .log().all()
                .statusCode(200)
                .body("id", equalTo(postId))
                .body("title", not(emptyString()));
    }
}
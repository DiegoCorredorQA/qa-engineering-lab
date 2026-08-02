package api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.emptyString;

public class CommentPostChainTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void shouldGetPostAssociatedWithComment() {
        Response commentResponse =
                given()
                        .log().all()
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
                .log().all()
                .when()
                .get("/posts/{postId}", postId)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(postId))
                .body("title", not(emptyString()));
    }
}
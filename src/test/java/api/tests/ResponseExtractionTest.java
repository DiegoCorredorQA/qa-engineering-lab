package api.tests;

import api.specifications.RequestSpecs;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;

public class ResponseExtractionTest {
    private RequestSpecification requestSpec;

    @BeforeClass
    public void setUp() {
        requestSpec = RequestSpecs.jsonPlaceholderRequestSpec();
    }


    @Test
    public void shouldExtractUserIdAndReuseItInAnotherRequest() {

        Response postResponse =
                given()
                        .spec(requestSpec)
                        .when()
                        .get("/posts/{postId}", 1)
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .response();

        int userId = postResponse.jsonPath().getInt("userId");

        System.out.println("Extracted userId: " + userId);

        given()
                .spec(requestSpec)
                .queryParam("userId", userId)
                .when()
                .get("/posts")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("userId", everyItem(equalTo(userId)));
    }
}
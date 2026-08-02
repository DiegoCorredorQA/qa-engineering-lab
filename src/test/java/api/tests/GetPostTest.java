package api.tests;

import api.specifications.RequestSpecs;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import org.testng.Assert;

public class GetPostTest {
    private RequestSpecification requestSpec;

    @BeforeClass
    public void setUp() {
        requestSpec = RequestSpecs.jsonPlaceholderRequestSpec();
    }

    @Test
    public void shouldGetPostById() {
        Response response =
            given()
                    .spec(requestSpec)
                    .pathParam("postId", 1)
            .when()
                    .get("/posts/{postId}")
            .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("userId", equalTo(1))
                .body("title", notNullValue())
                .body("body", notNullValue())
                .extract()
                .response();

        String title = response.path("title");
        String body = response.path("body");

        Assert.assertFalse(
                title.isBlank(),
                "The post title should not be blank"
        );

        Assert.assertFalse(
                body.isBlank(),
                "The post body should not be blank"
        );

        System.out.println("Post Title: " + title);
    }

    @Test
    public void shouldReturnNotFoundForNonExistingPost() {
        given()
                .spec(requestSpec)
                .pathParam("postId", 9999)
                .when()
                .get("/posts/{postId}")
                .then()
                .log().all()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body("$", anEmptyMap());
    }

    @Test
    public void shouldGetPostsByUserId() {
        given()
                .spec(requestSpec)
                .queryParam("userId", 1)
                .when()
                .get("/posts")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("userId", everyItem(equalTo(1)));
    }

    @Test
    public void shouldReturnEmptyListForNonExistingUserId() {
        given()
                .spec(requestSpec)
                .queryParam("userId", 9999)
                .when()
                .get("/posts")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", equalTo(0));
    }

    @Test
    public void shouldGetPostsForUserTwo() {
        given()
                .spec(requestSpec)
                .queryParam("userId", 2)
                .when()
                .get("/posts")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("userId", everyItem(equalTo(2)));
    }
}
package api.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import io.restassured.response.Response;
import org.testng.Assert;

public class GetPostTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void shouldGetPostById() {
        Response response =
            given()
                    .log().all()
            .when()
                    .get("/posts/1")
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
}
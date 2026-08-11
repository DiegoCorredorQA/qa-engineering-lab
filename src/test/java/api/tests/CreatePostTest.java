package api.tests;

import api.models.PostResponse;
import api.specifications.RequestSpecs;
import api.specifications.ResponseSpecs;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import api.models.PostRequest;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class CreatePostTest {
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;

    @BeforeClass
    public void setUp() {
        requestSpec = RequestSpecs.jsonPlaceholderRequestSpec();
        responseSpec = ResponseSpecs.createdfulJsonResponseSpec();
    }
    @Test
    public void shouldCreatePost() {
        PostRequest requestBody = new PostRequest("Learning REST Assured", "Creating a post using REST Assured", 1);
        PostResponse postResponse =
            given()
                    .spec(requestSpec)
                    .body(requestBody)
                    .when()
                    .post("/posts")
                    .then()
                    .spec(responseSpec)
                    .log().all()
                    .extract()
                    .as(PostResponse.class);

        assertNotNull(postResponse.getId());
        assertEquals(postResponse.getTitle(), requestBody.getTitle());
        assertEquals(postResponse.getBody(), requestBody.getBody());
        assertEquals(postResponse.getUserId(), requestBody.getUserId());
    }
}
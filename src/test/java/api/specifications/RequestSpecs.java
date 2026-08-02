package api.specifications;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class RequestSpecs {
    public static RequestSpecification jsonPlaceholderRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com")
                .setContentType(JSON)
                .setAccept(JSON)
                .log(LogDetail.ALL)
                .build();
    }
}

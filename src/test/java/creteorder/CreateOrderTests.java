package creteorder;

import base.BaseHttpClient;
import endpoint.EndPoints;
import json.CreateOrderRequestCard;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTests {

//   тело ответа содержит track.
    @Test
    public void createOrderTest(){
        CreateOrderRequestCard order = new CreateOrderRequestCard(
                "Naruto",
                "Uchiha",
                "Lenin st.",
                "5",
                "8915123456",
                5,
                "220",
                "Hello World",
                new String[]{"BLACK"}
        );
        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body(order)
                .when()
                .post(EndPoints.CREATE_ORDER)
                .then().statusCode(201)
                .and()
                .assertThat().body("track",notNullValue());
    }
}

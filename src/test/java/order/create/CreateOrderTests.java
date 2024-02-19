package order.create;

import base.BaseHttpClient;
import base.PostApi;
import constants.EndPoints;
import json.CreateOrderRequestCard;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTests {
    private PostApi postApi = new PostApi();
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

        postApi
                .doPost(EndPoints.ORDER,order)
                .then().statusCode(201)
                .and()
                .assertThat().body("track",notNullValue());
    }
}

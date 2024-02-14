package order.bytracknumber;

import base.BaseHttpClient;
import endpoint.EndPoints;
import json.CreateOrderRequestCard;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderByTrackNumderTests {
    @Before
    public void createOrder(){
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
                .post(EndPoints.ORDER)
                .then().statusCode(201)
                .and()
                .assertThat().body("track",notNullValue());
    }
//    @Test
//    todo успешный запрос возвращает объект с заказом;
//    todo запрос без номера заказа возвращает ошибку;
//    todo запрос с несуществующим заказом возвращает ошибку.
}

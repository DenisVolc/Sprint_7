package order.bytracknumber;

import base.BaseHttpClient;
import endpoint.EndPoints;
import json.CreateOrderRequestCard;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderByTrackNumderTests {
    private int orderTrackNumber;

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
        orderTrackNumber = given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body(order)
                .when()
                .post(EndPoints.ORDER)
                .then()
                .extract().path("track");
    }
    @Test
    public void getOrderTest(){//    todo успешный запрос возвращает объект с заказом;

        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .when()
                .get(EndPoints.getOrderByTrackNumber(orderTrackNumber))
                .then().statusCode(200)
                .assertThat().body("order",notNullValue());
    }
    @Test
    public void noTrackNumberGetOrderTest(){//    todo запрос без номера заказа возвращает ошибку;
        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .when()
                .get(EndPoints.getOrderByTrackNumber())
                .then().statusCode(400)
                .assertThat().body("message",equalTo("Недостаточно данных для поиска"));


    }
    @Test
    public void wrongTrackNumberGetOrderTest(){//    todo запрос с несуществующим заказом возвращает ошибку.

        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .when()
                .get(EndPoints.getOrderByTrackNumber(orderTrackNumber+1))
                .then().statusCode(404)
                .assertThat().body("message",equalTo("Заказ не найден"));
    }



}

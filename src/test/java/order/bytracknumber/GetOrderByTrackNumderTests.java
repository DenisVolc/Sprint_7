package order.bytracknumber;

import base.BaseHttpClient;
import base.GetApi;
import base.PostApi;
import constants.EndPoints;
import json.CreateOrderRequestCard;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderByTrackNumderTests {
    private String orderTrackNumber;
    private GetApi getApi = new GetApi();
    private PostApi postApi = new PostApi();

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
        orderTrackNumber = postApi
                .doPost(EndPoints.ORDER,order)
                .then()
                .extract().path("track").toString();
    }
    @Test
    public void getOrderTest(){//     успешный запрос возвращает объект с заказом;
        getApi.getOrderByTrackNumber(orderTrackNumber)
        .then().statusCode(200)
        .assertThat().body("order",notNullValue());
    }
    @Test
    public void noTrackNumberGetOrderTest(){//     запрос без номера заказа возвращает ошибку;
        getApi.getOrderByTrackNumber("")
        .then().statusCode(400)
        .assertThat().body("message",equalTo("Недостаточно данных для поиска"));
    }
    @Test
    public void wrongTrackNumberGetOrderTest(){//     запрос с несуществующим заказом возвращает ошибку.
        getApi.getOrderByTrackNumber(orderTrackNumber+1).then().statusCode(404)
                .assertThat().body("message",equalTo("Заказ не найден"));
    }
}

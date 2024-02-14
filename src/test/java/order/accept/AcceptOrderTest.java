package order.accept;

import base.BaseHttpClient;
import endpoint.EndPoints;
import json.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AcceptOrderTest {
    private int orderTrackNumber;
    private int orderId;
    private int courierId;
    private String index;
    private String login = "user";
    private String password = "1234";
    @Before
    public void setUp() {
        //создать заказ, получить трек-номер
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


        // вывести заказ по трек-номеру, получить айди заказа
        orderId = given()
                .spec(BaseHttpClient.baseRequestSpec())
                .when()
                .get(EndPoints.getOrderByTrackNumber(orderTrackNumber))
                .then().extract()
                .path("order.id");
        //создать курьера
        index = String.valueOf((int)(Math.random()*10000));
        login += index;
        CreateCourierCard courierCard = new CreateCourierCard(
                login ,
                password,
                "");
        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body(courierCard)
                .when()
                .post(EndPoints.CREATE_COURIER);
        //залогиниться курьером, получить айди курьера
        LoginCourierRequestCard card = new LoginCourierRequestCard(login,password);
        courierId = given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body(card)
                .when()
                .post(EndPoints.LOGIN_COURIER)
                .then().extract()
                .path("id");
        System.out.println(courierId);
    }
    @Test
    public void acceptOrder(){//    1.успешный запрос возвращает ok: true;
        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .when()
                .put(EndPoints.acceptOrder(orderId,courierId))
                .then().statusCode(200)
                .and().assertThat().body("ok",equalTo(true));
    }


//todo    3.если передать неверный id курьера, запрос вернёт ошибку;

//todo    5.если передать неверный номер заказа, запрос вернёт ошибку.

    @After
    public void cleanUp(){
        //deleteCourier(idCourier){}
        given()//удалил пользователя
                .spec(BaseHttpClient.baseRequestSpec())
                .delete(EndPoints.deleteCourier(String.valueOf(courierId)));
    }
}


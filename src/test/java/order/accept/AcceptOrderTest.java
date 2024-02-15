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
    private String orderTrackNumber;
    private String orderId;
    private String courierId;
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
                .extract().path("track").toString();


        // вывести заказ по трек-номеру, получить айди заказа
        orderId = given()
                .spec(BaseHttpClient.baseRequestSpec())
                .when()
                .queryParam("t",orderTrackNumber)
                .get(EndPoints.ORDER_BY_TRACK)
                .then().extract()
                .path("order.id").toString();
        //создать курьера
        index = BaseHttpClient.getRandomIndex();
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
                .path("id").toString();
        System.out.println(courierId);
    }
    @Test
    public void acceptOrder(){//    1.успешный запрос возвращает ok: true;
        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .when()
                .queryParam("courierId",courierId)
                .put(EndPoints.ACCEPT_ORDER+orderId)
                .then().statusCode(200)
                .and().assertThat().body("ok",equalTo(true));
    }
    //    2.если не передать id курьера, запрос вернёт ошибку;
    //    4.если не передать номер заказа, запрос вернёт ошибку;
    @Test
    public void emptyFieldsAcceptOrder(){ //Здесь возвращается 404, вместо 400. Похоже на дефект

        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .when()
                .queryParam("courierId","")
                .put(EndPoints.ACCEPT_ORDER)
                .then().statusCode(400);
    }
    @Test
    public void emptyOrderIdAcceptOrder(){ //Здесь возвращается 404, вместо 400. Похоже на дефект
        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .when()
                .queryParam("courierId",courierId)
                .put(EndPoints.ACCEPT_ORDER)
                .then().statusCode(400);
    }
    @Test
    public void emptyCourierIdAcceptOrder(){
        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .when()
                .queryParam("courierId","")
                .put(EndPoints.ACCEPT_ORDER+orderId)
                .then().statusCode(400);
    }


//    3.если передать неверный id курьера, запрос вернёт ошибку;
//    5.если передать неверный номер заказа, запрос вернёт ошибку.

    @Test
    public void wrongFieldsAcceptOrder(){
        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .when()
                .queryParam("courierId",courierId+1)
                .put(EndPoints.ACCEPT_ORDER+orderId+1)
                .then().statusCode(404);
    }
    @Test
    public void wrongOrderIdAcceptOrder(){
        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .when()
                .queryParam("courierId",courierId)
                .put(EndPoints.ACCEPT_ORDER+orderId+1)
                .then().statusCode(404);
    }
    @Test
    public void wrongCourierIdAcceptOrder(){
        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .when()
                .queryParam("courierId",courierId+1)
                .put(EndPoints.ACCEPT_ORDER+orderId)
                .then().statusCode(404);
    }
    @After
    public void cleanUp(){
        //deleteCourier(idCourier){}
        given()//удалил пользователя
                .spec(BaseHttpClient.baseRequestSpec())
                .delete(EndPoints.DELETE_COURIER + courierId);
    }
}


package order.accept;

import base.*;
import constants.EndPoints;
import json.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.Matchers.equalTo;


public class AcceptOrderTest {
    private String orderId;
    private String courierId;
    private String login = "user";
    private GetApi getApi = new GetApi();
    private DeleteApi deleteApi = new DeleteApi();
    private PostApi postApi = new PostApi();
    private PutApi putApi = new PutApi();
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

        String orderTrackNumber = postApi.doPost(EndPoints.ORDER, order)
                .then()
                .extract().path("track").toString();
        // вывести заказ по трек-номеру, получить айди заказа
        orderId = getApi.getOrderByTrackNumber(orderTrackNumber)
                .then().extract().path("order.id").toString();

        //создать курьера
        String index = BaseHttpClient.getRandomIndex();
        login += index;
        String password = "1234";
        CreateCourierCard courierCard = new CreateCourierCard(
                login ,
                password,
                "");

        postApi.doPost(EndPoints.CREATE_COURIER,courierCard);
        //залогиниться курьером, получить айди курьера
        LoginCourierRequestCard card = new LoginCourierRequestCard(login, password);
        courierId = postApi
                .doPost(EndPoints.LOGIN_COURIER,card)
                .then().extract()
                .path("id").toString();
    }
    @Test
    public void acceptOrder(){//    1.успешный запрос возвращает ok: true;
                putApi.acceptOrderPutRequest(orderId,courierId)
                        .then().statusCode(200)
                        .and().assertThat().body("ok",equalTo(true));
    }
    //    2.если не передать id курьера, запрос вернёт ошибку;
    //    4.если не передать номер заказа, запрос вернёт ошибку;
    @Test
    public void emptyFieldsAcceptOrder(){ //Здесь возвращается 404, вместо 400. Похоже на дефект
          putApi.acceptOrderPutRequest()
                  .then().statusCode(400);
    }
    @Test
    public void emptyOrderIdAcceptOrder(){ //Здесь возвращается 404, вместо 400. Похоже на дефект
        putApi.acceptOrderPutRequest(courierId)
                .then().statusCode(400);
    }
    @Test
    public void emptyCourierIdAcceptOrder(){
        putApi.acceptOrderPutRequest(orderId,"").then().statusCode(400);
    }
//    3.если передать неверный id курьера, запрос вернёт ошибку;
//    5.если передать неверный номер заказа, запрос вернёт ошибку.
    @Test
    public void wrongFieldsAcceptOrder(){
        putApi.acceptOrderPutRequest(orderId+1,courierId+1)
                .then().statusCode(404);
    }
    @Test
    public void wrongOrderIdAcceptOrder(){
        putApi.acceptOrderPutRequest(orderId+1,courierId)
                .then().statusCode(404);
    }
    @Test
    public void wrongCourierIdAcceptOrder(){
        putApi.acceptOrderPutRequest(orderId,courierId+1)
                .then().statusCode(404);

    }
    @After
    public void cleanUp(){
        deleteApi.deleteCourier(courierId);
    }
}


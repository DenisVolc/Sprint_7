package order.accept;

import base.BaseHttpClient;
import endpoint.EndPoints;
import json.CreateCourierCard;
import json.CreateOrderRequestCard;
import json.LoginCourierRequestCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class EmptyFieldsAccpetOrderParmTest {
    private int orderTrackNumber;
    private static int orderId;
    private int testOrderId;
    private static int courierId;
    private int testCourierId;
    private String index;
    private String login = "user";
    private String password = "1234";

    public EmptyFieldsAccpetOrderParmTest(int testOrderId, int testCourierId) {
        this.testOrderId = testOrderId;
        this.testCourierId = testCourierId;
    }


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
                .get(EndPoints.getIdByTrackNumber(orderTrackNumber))
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
    @Parameterized.Parameters
    public static Object[][]acceptCombination(){
        return new Object[][]{
                {"",""},
                {orderId,""},
                {"",courierId}
        };
    }
    @Test
    public void emptyFieldsAcceptOrder(){
        //todo    2.если не передать id курьера, запрос вернёт ошибку;
        //todo    4.если не передать номер заказа, запрос вернёт ошибку;
        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .when()
                .put(EndPoints.acceptOrder(testOrderId,testCourierId))
                .then().statusCode(400);
    }
    @After
    public void cleanUp(){
        //deleteCourier(idCourier){}
        given()//удалил пользователя
                .spec(BaseHttpClient.baseRequestSpec())
                .delete(EndPoints.deleteCourier(String.valueOf(courierId)));
    }
}

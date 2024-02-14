package listorders;

import base.BaseHttpClient;
import endpoint.EndPoints;
import json.CancelOrderRequestCard;
import json.CreateOrderRequestCard;
import json.OrderListResponseCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.notNullValue;

public class OrderList {
    private OrderListResponseCard orderNumber;
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
         orderNumber = given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body(order)
                .when()
                .post(EndPoints.ORDER)
                .body()
                .as(OrderListResponseCard.class);

//        System.out.println(orderNumber.getTrack());
    }
    @Test
    public void listOders(){
        System.out.println(orderNumber.getTrack());
         given()
                .spec(BaseHttpClient.baseRequestSpec())
                .when()
                .get(EndPoints.ORDER)//"?id=" + orderNumber.getTrack()
                .then().statusCode(200)
                .and().assertThat().body("orders.id",notNullValue());
    }
//    @After
//    public void cleanUp(){
//        CancelOrderRequestCard cancelRequest= new CancelOrderRequestCard(orderNumber.getTrack());
//        given()
//                .spec(BaseHttpClient.baseRequestSpec())
//                .body(cancelRequest)
//                .when()
//                .put(EndPoints.CANCEL_ORDER);
//    }

}

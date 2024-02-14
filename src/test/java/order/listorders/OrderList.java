package order.listorders;

import base.BaseHttpClient;
import endpoint.EndPoints;
import json.CreateOrderRequestCard;
import json.CreateOrderResponseCard;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.notNullValue;

public class OrderList {
    private CreateOrderResponseCard orderNumber;
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
                .post(EndPoints.ORDER);
    }
    @Test
    public void listOders(){
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

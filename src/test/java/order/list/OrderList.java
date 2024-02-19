package order.list;


import base.GetApi;
import base.PostApi;
import constants.EndPoints;
import json.CreateOrderRequestCard;

import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.Matchers.notNullValue;

public class OrderList {
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
        postApi.doPost(EndPoints.ORDER,order);
    }
    @Test
    public void listOrders(){
        getApi.getOrders()
                .then().statusCode(200)
                .and().assertThat().body("orders.id",notNullValue());
    }


}

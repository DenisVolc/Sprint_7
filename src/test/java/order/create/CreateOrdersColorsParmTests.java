package order.create;

import base.BaseHttpClient;
import endpoint.EndPoints;
import json.CreateOrderRequestCard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


//    можно указать один из цветов — BLACK или GREY;
//    можно указать оба цвета;
//    можно совсем не указывать цвет;
@RunWith(Parameterized.class)
public class CreateOrdersColorsParmTests {
    private String[] color;
    public CreateOrdersColorsParmTests(String[] color){
        this.color=color;
    }
    @Parameterized.Parameters
    public static Object[][]authCombination(){
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK","GREY"}},
                {new String[]{""}}
        };
    }
    @Test
    public void createOrderColorTest() {// сделать параметризированым  и перебрать три комбинации, бещ логина, без пароля, без ничего
        CreateOrderRequestCard order = new CreateOrderRequestCard(
                "Naruto",
                "Uchiha",
                "Lenin st.",
                "5",
                "8915123456",
                5,
                "220",
                "Hello World",
                color
        );
        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body(order)
                .when()
                .post(EndPoints.ORDER)
                .then().statusCode(201)
                .and()
                .assertThat().body("track",notNullValue());;
    }
}

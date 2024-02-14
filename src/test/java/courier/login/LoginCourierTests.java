package courier.login;

import base.BaseHttpClient;
import endpoint.EndPoints;
import json.CreateCourierCard;

import json.LoginCourierResponseCard;
import json.LoginCourierRequestCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTests {
    private String index;
    private String login = "user";
    private String password = "123";
    @Before
    public void setUp(){
        index = String.valueOf((int)(Math.random()*10000));
        login += index;
        CreateCourierCard courierCard = new CreateCourierCard(
                login ,
                password,
                null);

        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body(courierCard)
                .when()
                .post(EndPoints.CREATE_COURIER);
    }
    //    курьер может авторизоваться;
    //    успешный запрос возвращает id.
    @Test
    public void authTest(){
        LoginCourierRequestCard card = new LoginCourierRequestCard(login,password);
        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body(card)
                .when()
                .post(EndPoints.LOGIN_COURIER)
                .then().statusCode(200)
                .and()
                .assertThat().body("id",notNullValue());
    }



//    если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;

    @Test
    public void notExistAuthTest(){
        LoginCourierRequestCard card = new LoginCourierRequestCard(login+1,password);
        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body(card)
                .when()
                .post(EndPoints.LOGIN_COURIER)
                .then().statusCode(404);
    }

    @After
    public void cleanUp(){
        LoginCourierRequestCard getIdCard = new LoginCourierRequestCard(
                login
                ,password);

        LoginCourierResponseCard idCard = given()//записал id пользователя
                .spec(BaseHttpClient.baseRequestSpec())
                .body(getIdCard)
                .post(EndPoints.LOGIN_COURIER)
                .body().as(LoginCourierResponseCard.class);

        given()//удалил пользователя
                .spec(BaseHttpClient.baseRequestSpec())
                .body(idCard)
                .delete(EndPoints.deleteCourier(idCard.getId()));
    }
}

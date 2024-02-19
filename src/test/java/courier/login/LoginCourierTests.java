package courier.login;

import base.BaseHttpClient;
import base.DeleteApi;
import base.PostApi;
import constants.EndPoints;
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
    private DeleteApi deleteApi = new DeleteApi();
    private PostApi postApi = new PostApi();
    @Before
    public void setUp(){
        index = BaseHttpClient.getRandomIndex();
        login += index;
        CreateCourierCard courierCard = new CreateCourierCard(
                login ,
                password,
                null);

        postApi.doPost(EndPoints.CREATE_COURIER,courierCard);
    }
    //    курьер может авторизоваться;
    //    успешный запрос возвращает id.
    @Test
    public void authTest(){
        LoginCourierRequestCard card = new LoginCourierRequestCard(login,password);

        postApi.doPost(EndPoints.LOGIN_COURIER,card)
                .then().statusCode(200)
                .and()
                .assertThat().body("id",notNullValue());
    }
//    если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Test
    public void notExistAuthTest(){
        LoginCourierRequestCard card = new LoginCourierRequestCard(login+1,password);
        postApi.doPost(EndPoints.LOGIN_COURIER,card)
                .then().statusCode(404);
    }
    @After
    public void cleanUp(){
        LoginCourierRequestCard getIdCard = new LoginCourierRequestCard(
                login
                ,password);

        LoginCourierResponseCard idCard = postApi
                .doPost(EndPoints.LOGIN_COURIER,getIdCard)
                .body().as(LoginCourierResponseCard.class);

        deleteApi.deleteCourier(idCard.getId());
    }
}

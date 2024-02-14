package login;

import base.BaseHttpClient;
import endpoint.EndPoints;
import json.CreateCourierCard;
import json.LoginCourierResponseCard;
import json.LoginCourierRequestCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Parameterized.class)
public class RequiriedFiledsLoginCourierParmTest {

    private String index;
    private String login;
    private String setUpLogin = "sdasdfadfaas";
    private String setUpPassword = "123";
    private String password = "1234";

    public RequiriedFiledsLoginCourierParmTest(String login, String password){
        this.login=login;
        this.password=password;
    }
    @Parameterized.Parameters
    public static Object[][]authCombination(){
        return new Object[][]{
                {"",""},
                {"","123"},
                {"sdasdfadfaas",""}
        };
    }
    @Before
    public void setUp(){
//        index = String.valueOf((int)(Math.random()*10000));
//        login += index;
        CreateCourierCard courierCard = new CreateCourierCard(
                setUpLogin ,
                setUpPassword,
                null);

        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body(courierCard)
                .when()
                .post(EndPoints.CREATE_COURIER);
    }
    //     для авторизации нужно передать все обязательные поля;
//    система вернёт ошибку, если неправильно указать логин или пароль;
//    если какого-то поля нет, запрос возвращает ошибку;
    @Test
    public void requiriedFiledsCourier() {
        LoginCourierRequestCard courierCard = new LoginCourierRequestCard(
                login ,
                password
                );

        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body(courierCard)
                .when()
                .post(EndPoints.LOGIN_COURIER)
                .then().statusCode(400)
                .and().assertThat().body("message",equalTo("Недостаточно данных для входа"));
    }
    @After
    public void cleanUp(){
        LoginCourierRequestCard getIdCard = new LoginCourierRequestCard(
                setUpLogin
                ,setUpPassword);

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

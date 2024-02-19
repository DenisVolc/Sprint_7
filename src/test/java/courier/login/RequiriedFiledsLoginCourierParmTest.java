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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Parameterized.class)
public class RequiriedFiledsLoginCourierParmTest {

    private String login;
    private static String setUpLogin = "sdasdfadfaas";
    private static String setUpPassword = "123";
    private String password;
    private DeleteApi deleteApi = new DeleteApi();
    private PostApi postApi = new PostApi();

    public RequiriedFiledsLoginCourierParmTest(String login, String password){
        this.login=login;
        this.password=password;
    }
    @Parameterized.Parameters
    public static Object[][]authCombination(){
        return new Object[][]{
                {"",""},
                {"",setUpPassword},
                {setUpLogin,""}
        };
    }
    @Before
    public void setUp(){
        CreateCourierCard courierCard = new CreateCourierCard(
                setUpLogin ,
                setUpPassword,
                null);


        postApi.doPost(EndPoints.CREATE_COURIER,courierCard);
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

        postApi.doPost(EndPoints.LOGIN_COURIER,courierCard)
                .then().statusCode(400).and().assertThat()
                .body("message",equalTo("Недостаточно данных для входа"));
    }
    @After
    public void cleanUp(){
        LoginCourierRequestCard getIdCard = new LoginCourierRequestCard(
                setUpLogin
                ,setUpPassword);

        LoginCourierResponseCard idCard = postApi
                .doPost(EndPoints.LOGIN_COURIER,getIdCard)
                .body().as(LoginCourierResponseCard.class);

        deleteApi.deleteCourier(idCard.getId());
    }

}

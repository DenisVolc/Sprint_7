package deletecourier;

import base.BaseHttpClient;
import endpoint.EndPoints;
import json.CreateCourierCard;
import json.LoginCourierRequestCard;
import json.LoginCourierResponseCard;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

import static org.hamcrest.core.IsEqual.equalTo;

public class DeleteCourierTest {
    private String index;
    private String login = "user";
    private String password = "1234";
    LoginCourierResponseCard courierId ;
    LoginCourierRequestCard loginCourierRequestCard;
    @Before
    public void setUp() {
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

        loginCourierRequestCard = new LoginCourierRequestCard(login,password);

        courierId =  given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body(loginCourierRequestCard)
                .when()
                .post(EndPoints.LOGIN_COURIER)
                .body().as(LoginCourierResponseCard.class);
    }
    @Test
    public void deleteCourier(){     //    2.успешный запрос возвращает ok: true;
        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .delete(EndPoints.deleteCourier(courierId.getId()))
                .then().statusCode(200)
                .and()
                .assertThat().body("ok",equalTo(true));
    }
    @Test
    public void noIdDeleteCourier(){//    3.если отправить запрос без id, вернётся ошибка;
        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body("{\"id\":}")
                .delete(EndPoints.deleteCourier(""))
                .then().statusCode(400);
    }
    @Test
    public void wrongIddeleteCourier(){//    4.если отправить запрос с несуществующим id, вернётся ошибка.
        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .delete(EndPoints.deleteCourier(courierId.getId()+1))
                .then().statusCode(404);
    }





}

package courier.delete;

import base.BaseHttpClient;
import base.DeleteApi;
import base.PostApi;
import constants.EndPoints;
import json.CreateCourierCard;
import json.DeleteCourierRequest;
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
    private DeleteApi deleteAPi = new DeleteApi();
    private PostApi postApi = new PostApi();
    LoginCourierResponseCard courier;
    LoginCourierRequestCard loginCourierRequestCard;
    @Before
    public void setUp() {
        index = BaseHttpClient.getRandomIndex();
        login += index;
        CreateCourierCard courierCard = new CreateCourierCard(
                login ,
                password,
                "");
        postApi.doPost(EndPoints.CREATE_COURIER,courierCard);

        loginCourierRequestCard = new LoginCourierRequestCard(login,password);
        courier = postApi
                .doPost(EndPoints.LOGIN_COURIER,loginCourierRequestCard)
                .body().as(LoginCourierResponseCard.class);
    }
    @Test
    public void deleteCourier(){     //    2.успешный запрос возвращает ok: true;
        deleteAPi.deleteCourier(courier.getId())
                .then().statusCode(200).and()
                .assertThat().body("ok",equalTo(true));
    }
    @Test
    public void noIdDeleteCourier(){//    3.если отправить запрос без id, вернётся ошибка;
        deleteAPi.deleteNoIdCourier().then().statusCode(400);
    }
    @Test
    public void wrongIdDeleteCourier(){//    4.если отправить запрос с несуществующим id, вернётся ошибка.
        deleteAPi.deleteCourier(courier.getId()+1)
                .then().statusCode(404);
    }
}

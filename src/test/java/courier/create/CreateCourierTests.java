package courier.create;

import base.BaseHttpClient;
import base.DeleteApi;
import base.PostApi;
import json.CreateCourierCard;
import constants.EndPoints;
import json.LoginCourierRequestCard;
import json.LoginCourierResponseCard;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateCourierTests {
    private String index;
    private String login = "user";
    private String password = "1234";
    private String firstName = "saske";
    private DeleteApi deleteApi = new DeleteApi();
    private PostApi postApi = new PostApi();
    @Before
    public void setUp() {
        index = BaseHttpClient.getRandomIndex();
        login += index;
        firstName += index;
    }
    @Test
    public void createCourier() {
        CreateCourierCard courierCard = new CreateCourierCard(
                login ,
                password,
                firstName);

         postApi.doPost(EndPoints.CREATE_COURIER,courierCard)
                 .then().statusCode(201)
                 .and()
                 .assertThat().body("ok",equalTo(true));

    }
    @Test
    public void duplicateCourier() {
        CreateCourierCard courierCard = new CreateCourierCard(
                login ,
                password,
                firstName);
        postApi.doPost(EndPoints.CREATE_COURIER,courierCard);
        postApi.doPost(EndPoints.CREATE_COURIER,courierCard)
                .then().statusCode(409);
    }
    @After
    public void cleanUp(){
        LoginCourierRequestCard getIdCard = new LoginCourierRequestCard(
                login
                ,password);

        LoginCourierResponseCard idCard =postApi
                .doPost(EndPoints.LOGIN_COURIER,getIdCard)
                .body().as(LoginCourierResponseCard.class);

        deleteApi.deleteCourier(idCard.getId());
    }
}

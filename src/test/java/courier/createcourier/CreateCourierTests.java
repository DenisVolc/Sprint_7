package courier.createcourier;

import base.BaseHttpClient;
import json.CreateCourierCard;
import endpoint.EndPoints;
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

    @Before
    public void setUp() {
        index = String.valueOf((int)(Math.random()*10000));
        login += index;
        firstName += index;
    }
    @Test
    public void createCourier() {
        CreateCourierCard courierCard = new CreateCourierCard(
                login ,
                password,
                firstName);

         given()
                 .spec(BaseHttpClient.baseRequestSpec())
                 .body(courierCard)
                 .when()
                 .post(EndPoints.CREATE_COURIER)
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

        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body(courierCard)
                .when()
                .post("/api/v1/courier")
                ;

        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body(courierCard)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(409);
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

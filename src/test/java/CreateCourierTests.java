import json.CreateCourierCard;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateCourierTests {
    private String sufix;
    private String login = "ninja";
    private String password = "1234";

    private String firstName = "saske";

    @Before
    public void setUp() {
        sufix = String.valueOf((int)(Math.random()*10000));
        login += sufix;
        firstName += sufix;
    }
    @Test
    public void createCourier() {
        CreateCourierCard courierCard = new CreateCourierCard(
                login ,
                password,
                "saske");

         given()
                 .spec(BaseHttpClient.baseRequestSpec())
                 .body(courierCard)
                 .when()
                 .post("/api/v1/courier")
                 .then().statusCode(201)
                 .and()
                 .assertThat().body("ok",equalTo(true))
         ;

    }
    @Test
    public void duplicateCourier() {
        CreateCourierCard courierCard = new CreateCourierCard(
                login ,
                password,
                "saske");

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
                .then().statusCode(409)
        ;
    }
    @Test
    public void requiriedFiledsCourier() {//todo сделать параметризированым  и перебрать три комбинации, бещ логина, без пароля, без ничего
            CreateCourierCard courierCard = new CreateCourierCard(
                    null ,
                    password,
                    "saske");

            given()
                    .spec(BaseHttpClient.baseRequestSpec())
                    .body(courierCard)
                    .when()
                    .post("/api/v1/courier")
                    .then().statusCode(400);
    }








//    @After
//    public void cleanUp(){
//        GetIdCard getIdCard = new GetIdCard(
//                login
//                ,password);
//
//        IdCard idCard = given()//записал id пользователя
//                .spec(BaseHttpClient.baseRequestSpec())
//                .body(getIdCard)
//                .post("/api/v1/courier/login")
//                .body().as(IdCard.class);
//
//        given()//удалил пользователя
//                .spec(BaseHttpClient.baseRequestSpec())
//                .body("{\"id\": \""+idCard.getId()+"\"}")
//                .delete("/api/v1/courier/"+idCard.getId());
//
//    }

}
//1.курьера можно создать;
//2.нельзя создать двух одинаковых курьеров;
//3.чтобы создать курьера, нужно передать в ручку все обязательные поля;
//4.запрос возвращает правильный код ответа;
//5.успешный запрос возвращает ok: true;
//6.если одного из полей нет, запрос возвращает ошибку;
//7.если создать пользователя с логином, который уже есть, возвращается ошибка.
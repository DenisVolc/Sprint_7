import json.CreateCourierCard;
import json.GetIdCard;
import json.IdCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.Random;

public class Post {
    private String sufix = String.valueOf((int)(Math.random()*10000));
    private String login = "ninja";
    private String password = "1234";

    @Before
    public void setUp() {

    }
    @Test
    public void createCourier() {
        CreateCourierCard courierCard = new CreateCourierCard(//вариант 1
                login + sufix,
                password,
                "saske");
//        String json = "{\"login\": \"VasyaVasilev\",\"password\": \"123\",\"firstName\": \"Vasya\"}"; //Вариант 2
//        File jsonFile = new File("src/json/newCourier.json");//вариант 3

         given()
                 .spec(BaseHttpClient.baseRequestSpec())
                 .body(courierCard)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(201);

    }
    @After
    public void cleanUp(){
        GetIdCard getIdCard = new GetIdCard(
                login
                ,password );

        IdCard idCard = given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body(getIdCard)
                .post("/api/v1/courier/login")
                .body().as(IdCard.class);
        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body("{\"id\": \""+idCard.getId()+"\"}")
                .delete("/api/v1/courier/:id");

        System.out.println(idCard.getId());
    }

}

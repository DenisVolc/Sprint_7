import io.restassured.RestAssured;
import json.CreateCourierCard;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import io.restassured.filter.log.*;

import java.io.File;

public class Post {
    @Before
//    public void setUp() {
//        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
//    }
    @Test
    public void getMyInfoStatusCode() {
        CreateCourierCard courierCard = new CreateCourierCard(//вариант 1
                "VasyaVasilev",
                "123",
                "Vasya");
        String json = "{\"login\": \"VasyaVasilev\",\"password\": \"123\",\"firstName\": \"Vasya\"}"; //Вариант 2
        File jsonFile = new File("src/json/newCourier.json");//вариант 3

         given()
                 .spec(BaseHttpClient.baseRequestSpec())
                 .body(json)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(201);
    }
}

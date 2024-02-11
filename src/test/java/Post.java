import io.restassured.RestAssured;
import json.CreateCourierCard;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class Post {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }
    @Test
    public void getMyInfoStatusCode() {
        CreateCourierCard courierCard = new CreateCourierCard(
                "VasyaVasilev",
                "123",
                "Vasya");

         given()
                .body(courierCard)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(201);
    }
}

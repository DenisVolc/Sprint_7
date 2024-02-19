package base;

import constants.URL;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseHttpClient {
    public static RequestSpecification baseRequestSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(URL.BASE_URL)
                .addHeader("Content-Type", "application/json")
                .setRelaxedHTTPSValidation()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new ErrorLoggingFilter())
                .build();
    }

    protected Response doGetRequest(String path){
        return RestAssured.given()
                .spec(baseRequestSpec())
                .get(path)
                .thenReturn();
    }
    protected Response doGetRequest(String path, String param, String data){
        return RestAssured.given()
                .spec(baseRequestSpec())
                .when()
                .queryParam(param,data)
                .get(path)
                .thenReturn();
    }

    protected Response doPostRequest(String path, Object body){
        return RestAssured.given()
                .spec(baseRequestSpec())
                .body(body)
                .when()
                .post(path)
                .thenReturn();
    }
    protected Response doPutRequest(String path, String param, String data){
        return RestAssured.given()
                .spec(baseRequestSpec())
                .when()
                .queryParam(param,data)
                .put(path)
                .thenReturn();
    }
    protected Response doDeleteRequest(String path){
        return RestAssured.given()
                .spec(BaseHttpClient.baseRequestSpec())
                .delete(path)
                .thenReturn();
    }
    protected Response doDeleteRequest(String path,Object body){
        return RestAssured.given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body(body)
                .delete(path)
                .thenReturn();
    }
    public static String getRandomIndex(){
        return String.valueOf((int)(Math.random()*1000000));
    }


}

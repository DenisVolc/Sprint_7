package base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

public class BaseHttpClient {
    public static RequestSpecification baseRequestSpec(){
        return new RequestSpecBuilder()
                .setBaseUri("https://qa-scooter.praktikum-services.ru")
                .addHeader("Content-Type", "application/json")
                .setRelaxedHTTPSValidation()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
//                .addFilter(new ErrorLoggingFilter())
                .build();
    }
}

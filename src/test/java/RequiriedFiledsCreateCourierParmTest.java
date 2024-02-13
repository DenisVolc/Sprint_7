import json.CreateCourierCard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
@RunWith(Parameterized.class)
public class RequiriedFiledsCreateCourierParmTest {

    private String login;
    private String password = "1234";

    public RequiriedFiledsCreateCourierParmTest(String login, String password){
        this.login=login;
        this.password=password;
    }
    @Parameterized.Parameters
    public static Object[][]authCombination(){
        return new Object[][]{
                {null,null},
                {null,"123"},
                {"user",null}
        };
    }

    @Test
    public void requiriedFiledsCourier() {//todo сделать параметризированым  и перебрать три комбинации, бещ логина, без пароля, без ничего
        CreateCourierCard courierCard = new CreateCourierCard(
                login ,
                password,
                "name");

        given()
                .spec(BaseHttpClient.baseRequestSpec())
                .body(courierCard)
                .when()
                .post(EndPoints.createCourier)
                .then().statusCode(400);
    }
}

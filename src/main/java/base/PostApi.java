package base;

import io.restassured.response.Response;

public class PostApi extends BaseHttpClient {
    public Response doPost(String path,Object body){
        return doPostRequest(path,body).thenReturn();
    }

}

package base;

import constants.EndPoints;
import io.restassured.response.Response;

public class PutApi extends BaseHttpClient{
    public Response acceptOrderPutRequest(String orderId,String courierId){
        return doPutRequest(EndPoints.ACCEPT_ORDER + orderId,"courierId",courierId).thenReturn();
    }
    public Response acceptOrderPutRequest(){
        return doPutRequest(EndPoints.ACCEPT_ORDER ,"courierId","").thenReturn();
    }
    public Response acceptOrderPutRequest(String courierId){
        return doPutRequest(EndPoints.ACCEPT_ORDER ,"courierId",courierId).thenReturn();
    }

}

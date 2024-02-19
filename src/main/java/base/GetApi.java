package base;

import constants.EndPoints;
import io.restassured.response.Response;

public class GetApi extends BaseHttpClient{
    public Response getOrders(){
        return doGetRequest(EndPoints.ORDER);
    }
    public Response getOrderByTrackNumber(String data){
        return doGetRequest(EndPoints.ORDER_BY_TRACK,"t",data);
    }
}

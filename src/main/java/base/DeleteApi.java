package base;

import constants.EndPoints;
import io.restassured.response.Response;

public class DeleteApi extends BaseHttpClient{
    public Response deleteCourier(String courierId){
        return doDeleteRequest(EndPoints.DELETE_COURIER+courierId)
                .thenReturn();
    }
    public Response deleteNoIdCourier(){
        return doDeleteRequest(EndPoints.DELETE_COURIER,"{\"id\":}")
                .thenReturn();
    }
}

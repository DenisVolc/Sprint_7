package endpoint;

public class EndPoints {
    public static final String CREATE_COURIER = "/api/v1/courier";
    public static final String LOGIN_COURIER = "/api/v1/courier/login";
    public static String deleteCourier(String id){
        return "/api/v1/courier/"+id;
    }
    public static final String ORDER = "/api/v1/orders";
    public static final String CANCEL_ORDER = "/api/v1/orders/cancel";
}

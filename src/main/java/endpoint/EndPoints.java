package endpoint;

public class EndPoints {
    public static final String CREATE_COURIER = "/api/v1/courier";
    public static final String LOGIN_COURIER = "/api/v1/courier/login";
    public static String deleteCourier(String id){
        return "/api/v1/courier/"+id;
    }
    public static final String ORDER = "/api/v1/orders";
    public static String getOrderByTrackNumber(int track){return  "/api/v1/orders/track?t="+track;}
    public static String getOrderByTrackNumber(){return  "/api/v1/orders/track?t=";}
    public static String acceptOrder(int orderId,int courierId){return "/api/v1/orders/accept/"+orderId+"?courierId="+courierId;}
    public static final String CANCEL_ORDER = "/api/v1/orders/cancel";
}

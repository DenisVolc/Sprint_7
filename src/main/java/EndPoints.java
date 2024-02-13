public class EndPoints {
    public static final String createCourier = "/api/v1/courier";
    public static final String loginCourier = "/api/v1/courier/login";
    public static String deleteCourier(String id){
        return "/api/v1/courier/"+id;
    }
}

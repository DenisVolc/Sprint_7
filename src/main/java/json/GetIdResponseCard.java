package json;

public class GetIdResponseCard {
    private String id;

    public GetIdResponseCard(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id.toString();
    }
}

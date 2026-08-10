package api.models;

public class PostRequest {
    private String title;
    private String body;
    private Integer userId;

    public PostRequest(String title, String body, Integer userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Integer getUserId() {
        return userId;
    }
}

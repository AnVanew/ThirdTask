package Models;

public class Comment {
    private String userName;
    private String comment;

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Comment(String userName, String comment) {
        this.userName = userName;
        this.comment = comment;
    }
}

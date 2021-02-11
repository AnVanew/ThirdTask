package DBWork;

import Models.Comment;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentsDB {
    private Logger logger = Logger.getLogger(BookManageDB.class);
    ResultSet resultSet;

    public void addComment(int bookId, String userName, String comment){
        String query = "INSERT INTO comments (id, user_name, comment) VALUES ("+bookId+", '"+userName+"', '"+comment+"')";
        DBWorker.executeUpdate(query);
    }

    public List<Comment> getAllComments(int bookId){
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM comments WHERE id = "+bookId;
        resultSet = DBWorker.executeQuery(query);
        try {
            while (resultSet.next()){
                comments.add(new Comment(
                        resultSet.getString("user_name"),
                        resultSet.getString("comment"))
                );
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        DBWorker.closeConnect();
        return comments;
    }

    public void deleteBookComments(int bookId){
        String query = "DELETE FROM COMMENTS WHERE id = " +bookId;
        DBWorker.executeUpdate(query);
    }
}

package DBWork;

import Models.Comment;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CommentsDB {
    private Logger logger = Logger.getLogger(BookManageDB.class);

    public void addComment(int bookId, String userName, String comment){
        String query = "INSERT INTO comments (id, user_name, comment) VALUES ("+bookId+", '"+userName+"', '"+comment+"')";
        DBWorker.executeUpdate(query);
    }

    public List<Comment> getAllComments(int bookId){
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM comments WHERE id = "+bookId;
        comments = (List<Comment>) DBWorker.executeQuery(query, (resultSet)->{
            List<Comment> commentsList = new ArrayList<>();
            do{
                commentsList.add(new Comment(
                        resultSet.getString("user_name"),
                        resultSet.getString("comment"))
                );
            }while (resultSet.next());
            return commentsList;
        });
        return comments;
    }

    public void deleteBookComments(int bookId){
        String query = "DELETE FROM COMMENTS WHERE book_id = " +bookId;
        DBWorker.executeUpdate(query);
    }
}

package DBWork;

import Models.Comment;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentsDB {

    public void addComment(int bookId, String userName, String comment){
        String query = "INSERT INTO comments (book_id, user_name, comment) VALUES ("+bookId+", '"+userName+"', '"+comment+"')";
        DBWorker.executeUpdate(query);
    }

    public List<Comment> getAllComments(int bookId){
        List<Comment> comments;
        String query = "SELECT * FROM comments WHERE book_id = ?";
        comments = DBWorker.executeQuery(query, (preparedStatement)->{
            preparedStatement.setInt(1, bookId);},
            this::collectCommentFromResultSet);
        return comments;
    }

    private List<Comment> collectCommentFromResultSet(ResultSet resultSet) throws SQLException {
        List<Comment> commentsList = new ArrayList<>();
        do{
            commentsList.add(new Comment(
                resultSet.getString("user_name"),
                resultSet.getString("comment"))
            );
        }while (resultSet.next());
        return commentsList;
    }

    public void deleteBookComments(int bookId){
        String query = "DELETE FROM COMMENTS WHERE book_id = " +bookId;
        DBWorker.executeUpdate(query);
    }
}

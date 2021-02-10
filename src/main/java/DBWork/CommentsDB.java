package DBWork;

import Models.Book;
import Models.Comment;
import org.apache.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentsDB {
    private Logger logger = Logger.getLogger(BookManageDB.class);
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    DBWorker dbWorker = new DBWorker();

    public void addComment(int bookId, String userName, String comment){
        try {
            preparedStatement = dbWorker.getConnection().prepareStatement(
                    "INSERT INTO comments (id, user_name, comment) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, bookId);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, comment);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbWorker.CloseConnect();
        }
    }

    public List<Comment> getAllComments(int bookId){
        List<Comment> comments = new ArrayList<>();
        try {
            preparedStatement = dbWorker.getConnection().prepareStatement(
                    "SELECT * FROM comments WHERE id = ?");
            preparedStatement.setInt(1, bookId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                comments.add(new Comment(
                        resultSet.getString("user_name"),
                        resultSet.getString("comment"))
                );
            }
        }
        catch (SQLException e){
            logger.error(e);
        }
        finally {
            dbWorker.CloseConnect();
        }
        return comments;
    }
}

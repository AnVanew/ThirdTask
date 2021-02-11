package DBWork;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MarksDB {

    private Logger logger = Logger.getLogger(BookManageDB.class);
    ResultSet resultSet;

    public void like(int bookId) {
        String query = "UPDATE marks SET likes = likes + 1 WHERE id = " + bookId;
        DBWorker.executeUpdate(query);
    }

    public void dislike(int bookId){
        String query = "UPDATE marks SET dislikes = dislikes + 1 WHERE id = " + bookId;
        DBWorker.executeUpdate(query);
    }

    public void createBookMarks(int bookId){
        String query = "INSERT INTO marks (id, likes, dislikes) VALUES (" + bookId + ", 0, 0)";
        DBWorker.executeUpdate(query);
    }

    public int getBookLikes(int bookId){
        int bookLikes = 0;
        String query = "SELECT likes FROM MARKS WHERE ID = "+bookId;
        resultSet = DBWorker.executeQuery(query);
        try {
            resultSet.next();
            bookLikes = resultSet.getInt("likes");
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        DBWorker.closeConnect();
        return bookLikes;
    }

    public int getBookDislikes(int bookId){
        int bookDislikes = 0;
        String query = "SELECT dislikes FROM MARKS WHERE ID = "+bookId;
        resultSet = DBWorker.executeQuery(query);
        try {
            resultSet.next();
            bookDislikes = resultSet.getInt("dislikes");
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        DBWorker.closeConnect();
        return bookDislikes;
    }

    public void deleteBookMarks(int bookId){
        String query = "DELETE FROM MARKS WHERE id = " + bookId;
        DBWorker.executeUpdate(query);
    }

}

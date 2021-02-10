package DBWork;

import Models.Book;
import org.apache.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MarksDB {

    private Logger logger = Logger.getLogger(BookManageDB.class);
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    DBWorker dbWorker = new DBWorker();

    public void like(Book book) {
        try {
            preparedStatement = dbWorker.getConnection().prepareStatement(
                    "UPDATE marks SET likes = likes + 1 WHERE id = (SELECT id from BOOKS  WHERE autor_id = (SELECT id from autors WHERE name = ? AND surname = ?) AND book_name = ?)");
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getSurname());
            preparedStatement.setString(3, book.getBookName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbWorker.CloseConnect();
        }
    }

    public void dislike(Book book){
        try {
            preparedStatement = dbWorker.getConnection().prepareStatement(
                    "UPDATE marks SET dislikes = dislikes + 1 WHERE id = (SELECT id from BOOKS  WHERE autor_id = (SELECT id from autors WHERE name = ? AND surname = ?) AND book_name = ?)");
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getSurname());
            preparedStatement.setString(3, book.getBookName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbWorker.CloseConnect();
        }
    }

    public void createBookMarks(int bookId){
        try {
            preparedStatement = dbWorker.getConnection().prepareStatement(
                    "INSERT INTO marks (id, likes, dislikes) VALUES (?, 0, 0)");
            preparedStatement.setInt(1, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbWorker.CloseConnect();
        }
    }

    public int getBookLikes(int bookId){
        int bookLikes = 0;
        try {
            preparedStatement = dbWorker.getConnection().prepareStatement(
                    "SELECT likes FROM MARKS WHERE ID = ?");
            preparedStatement.setInt(1, bookId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            bookLikes = resultSet.getInt("likes");
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbWorker.CloseConnect();
        }
        return bookLikes;
    }

    public int getBookDislikes(int bookId){
        int bookLikes = 0;
        try {
            preparedStatement = dbWorker.getConnection().prepareStatement(
                    "SELECT dislikes FROM MARKS WHERE ID = ?");
            preparedStatement.setInt(1, bookId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            bookLikes = resultSet.getInt("dislikes");
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            dbWorker.CloseConnect();
        }
        return bookLikes;
    }
}

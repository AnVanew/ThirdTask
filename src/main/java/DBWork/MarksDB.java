package DBWork;

import org.apache.log4j.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MarksDB {

    private final Logger logger = Logger.getLogger(BookManageDB.class);

    public void like(int bookId) {
        String query = "INSERT INTO MARKS (book_id,likes, dislikes) VALUES("+bookId+",1,0)";
        DBWorker.executeUpdate(query);
    }

    public void dislike(int bookId){
        String query = "INSERT INTO MARKS (book_id,likes, dislikes) VALUES("+bookId+",0,1)";
        DBWorker.executeUpdate(query);
    }

    public int getBookLikes(int bookId){
        int bookLikes;
        String query = "SELECT SUM (likes) FROM MARKS WHERE book_id = ?";
        bookLikes = DBWorker.executeQuery(query, (preparedStatement) -> preparedStatement.setInt(1, bookId),
            this::countMarkFromResultSet);
        return bookLikes;
    }

    public int getBookDislikes(int bookId){
        int bookDislikes;
        String query = "SELECT SUM (dislikes) FROM MARKS WHERE book_id = ?";
        bookDislikes = DBWorker.executeQuery(query, (preparedStatement) ->  preparedStatement.setInt(1, bookId),
            this::countMarkFromResultSet);
        return bookDislikes;
    }
    private int countMarkFromResultSet(ResultSet resultSet) throws SQLException {
        return resultSet.getInt(1);
    }

    public void deleteBookMarks(int bookId){
        String query = "DELETE FROM MARKS WHERE book_id  = " + bookId;
        DBWorker.executeUpdate(query);
    }

}

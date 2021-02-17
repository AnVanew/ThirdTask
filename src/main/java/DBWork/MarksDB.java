package DBWork;

import org.apache.log4j.Logger;

import java.sql.ResultSet;

public class MarksDB {

    private Logger logger = Logger.getLogger(BookManageDB.class);
    ResultSet resultSet;

    public void like(int bookId) {
        String query = "INSERT INTO MARKS (book_id,likes, dislikes) VALUES("+bookId+",1,0)";
        DBWorker.executeUpdate(query);
    }

    public void dislike(int bookId){
        String query = "INSERT INTO MARKS (book_id,likes, dislikes) VALUES("+bookId+",0,1)";
        DBWorker.executeUpdate(query);
    }

    public void createBookMarks(int bookId){
        String query = "INSERT INTO marks (id, likes, dislikes) VALUES (" + bookId + ", 0, 0)";
        DBWorker.executeUpdate(query);
    }

    public int getBookLikes(int bookId){
        int bookLikes = 0;
        String query = "SELECT SUM (likes) FROM MARKS WHERE book_id = "+bookId;
        bookLikes = (int) DBWorker.executeQuery(query, (resultSet) -> {
            Integer res = resultSet.getInt(1);
            return res;
        });
        return bookLikes;
    }

    public int getBookDislikes(int bookId){
        int bookDislikes;
        String query = "SELECT SUM (dislikes) FROM MARKS WHERE book_id = "+bookId;
        bookDislikes = (int) DBWorker.executeQuery(query, (resultSet) -> {
            Integer res = resultSet.getInt(1);
            return res;
        });
        return bookDislikes;
    }

    public void deleteBookMarks(int bookId){
        String query = "DELETE FROM MARKS WHERE book_id  = " + bookId;
        DBWorker.executeUpdate(query);
    }

}

package DBWork;

import Models.Author;
import Models.Book;
import org.apache.log4j.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookManageDB {
    private final static Logger logger = Logger.getLogger(BookManageDB.class);

    public static void addBookWithAuthor(String name, String surname, String bookName, int year, String annotation){
        if (AuthorsDB.checkSameAuthor(name,surname)) AuthorsDB.addAuthor(name, surname);
        addBook(name, surname, bookName, year, annotation);
    }

    private static void addBook(String name, String surname, String bookName, int year, String annotation){
        int authorId = AuthorsDB.getAuthorId(name, surname);
        String query = "INSERT INTO BOOKS (author_id, book_name, year, annotation) VALUES("+authorId+",'"+bookName+"',"+year+",'"+annotation+"')";
        DBWorker.executeUpdate(query);
    }

    public static int getBookId(String bookName, int authorId){
        int bookId=-1;
        String query = "SELECT ID FROM BOOKS WHERE book_name = ?  AND author_id = ?";
        Integer res = DBWorker.executeQuery(query, (preparedStatement)->{
            preparedStatement.setString(1, bookName);
            preparedStatement.setInt(2, authorId);},
            BookManageDB::bookIdFromResultSet);
        if (res != null) bookId = res;
        return bookId;
    }

    private static int bookIdFromResultSet(ResultSet resultSet) throws SQLException{
        return resultSet.getInt(1);
    }

    public static void deleteBook(int bookId){
        String query = "DELETE FROM BOOKS WHERE id =  "+bookId;
        DBWorker.executeUpdate(query);
    }

    public static List<Book> getBooksByAuthor(int authorId){
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM BOOKS JOIN AUTHORS ON BOOKS.AUTHOR_ID = AUTHORS.ID WHERE author_id = ?";
        List<Book> res =  DBWorker.executeQuery(query, (preparedStatement) ->{
            preparedStatement.setInt(1, authorId);},
            BookManageDB::collectBookFromResultSet);
        if(res != null) books = res;
        return books;
    }

    public static List<Book> getAllBooks(){
        List<Book> books;
        String query = "SELECT * FROM BOOKS JOIN AUTHORS ON BOOKS.AUTHOR_ID = AUTHORS.ID";
        books = DBWorker.executeQuery(query, (preparedStatement) ->{},
                BookManageDB::collectBookFromResultSet);
        return books;
    }

    private static List<Book> collectBookFromResultSet(ResultSet resultSet) throws SQLException {
        List<Book> booksList = new ArrayList<>();
        do{ booksList.add(bookFromResultSet(resultSet));
        }while (resultSet.next());
        return booksList;
    }

    public static Book getBookByBookId(int bookId){
        Book book;
        String query = "SELECT * FROM BOOKS JOIN AUTHORS ON BOOKS.AUTHOR_ID = AUTHORS.ID WHERE BOOKS.id = ?";
        book =  DBWorker.executeQuery(query, (preparedStatement)->{
                    preparedStatement.setInt(1, bookId);},
                BookManageDB::bookFromResultSet);
        return book;
    }

    private static Book bookFromResultSet(ResultSet resultSet) throws SQLException{
        Book bookDB = new Book(
                new Author(
                        resultSet.getString("name"),
                        resultSet.getString("surname")
                ),
                resultSet.getString("book_name"),
                resultSet.getInt("year"),
                resultSet.getString("annotation"));
        return bookDB;
    }

    public static void updateBook(String newAnnotation,int bookId){
        String query = "UPDATE books set annotation = '"+newAnnotation+"' WHERE id = "+bookId;
        DBWorker.executeUpdate(query);
    }
}

package DBWork;

import Models.Author;
import Models.Book;
import org.apache.log4j.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookManageDB {
    private final Logger logger = Logger.getLogger(BookManageDB.class);
    AuthorsDB authorsDB = new AuthorsDB();

    public void addBookWithAuthor(String name, String surname, String bookName, int year, String annotation){
        if (authorsDB.checkSameAuthor(name,surname)) authorsDB.addAuthor(name, surname);
        addBook(name, surname, bookName, year, annotation);
    }

    private void addBook(String name, String surname, String bookName, int year, String annotation){
        int authorId = authorsDB.getAuthorId(name, surname);
        String query = "INSERT INTO BOOKS (author_id, book_name, year, annotation) VALUES("+authorId+",'"+bookName+"',"+year+",'"+annotation+"')";
        DBWorker.executeUpdate(query);
    }

    public int getBookId(String bookName, int authorId){
        int bookId=-1;
        String query = "SELECT ID FROM BOOKS WHERE book_name = ?  AND author_id = ?";
        bookId = DBWorker.executeQuery(query, (preparedStatement)->{
            preparedStatement.setString(1, bookName);
            preparedStatement.setInt(2, authorId);},
            this::bookIdFromResultSet);
        return bookId;
    }

    private int bookIdFromResultSet(ResultSet resultSet) throws SQLException{
        return resultSet.getInt(1);
    }

    public void deleteBook(int bookId){
        String query = "DELETE FROM BOOKS WHERE id =  "+bookId;
        DBWorker.executeUpdate(query);
    }

    public List<Book> getBooksByAuthor(int authorId){
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM BOOKS JOIN AUTHORS ON BOOKS.AUTHOR_ID = AUTHORS.ID WHERE author_id = ?";
        List<Book> res =  DBWorker.executeQuery(query, (preparedStatement) ->{
            preparedStatement.setInt(1, authorId);},
            this::collectBookFromResultSet);
        if(res != null) books = res;
        return books;
    }

    public List<Book> getAllBooks(){
        List<Book> books;
        String query = "SELECT * FROM BOOKS JOIN AUTHORS ON BOOKS.AUTHOR_ID = AUTHORS.ID";
        books = DBWorker.executeQuery(query, (preparedStatement) ->{},
                this::collectBookFromResultSet);
        return books;
    }

    private List<Book> collectBookFromResultSet(ResultSet resultSet) throws SQLException {
        List<Book> booksList = new ArrayList<>();
        do{ booksList.add(bookFromResultSet(resultSet));
        }while (resultSet.next());
        return booksList;
    }

    public Book getBookByAuthorAndBookName(int bookId){
        Book book;
        String query = "SELECT * FROM BOOKS JOIN AUTHORS ON BOOKS.AUTHOR_ID = AUTHORS.ID WHERE BOOKS.id = ?";
        book =  DBWorker.executeQuery(query, (preparedStatement)->{
                    preparedStatement.setInt(1, bookId);},
                this::bookFromResultSet);
        return book;
    }

    private Book bookFromResultSet(ResultSet resultSet) throws SQLException{
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

    public void updateBook(String newAnnotation,int bookId){
        String query = "UPDATE books set annotation = '"+newAnnotation+"' WHERE id = "+bookId;
        DBWorker.executeUpdate(query);
    }
}

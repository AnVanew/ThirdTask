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

    public int getBookId(Book book){
        int bookId=-1;
        String query = "SELECT ID FROM BOOKS WHERE book_name = ?  AND author_id = (SELECT ID FROM AUTHORS WHERE NAME = ? AND SURNAME = ?)";
        bookId = DBWorker.executeQuery(query, (preparedStatement)->{
            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2, book.getName());
            preparedStatement.setString(3, book.getSurname());},
            this::bookIdFromResultSet);
        return bookId;
    }

    public int getBookId(String name, String surname, String bookName){
        int bookId=-1;
        String query = "SELECT ID FROM BOOKS WHERE book_name = ?  AND author_id = (SELECT ID FROM AUTHORS WHERE NAME = ? AND SURNAME = ?)";
        bookId = DBWorker.executeQuery(query, (preparedStatement)->{
            preparedStatement.setString(1, bookName);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);},
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

    public List<Book> getBooksByAuthor(String name, String surname){
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM BOOKS JOIN AUTHORS ON AUTHORS.ID = BOOKS.AUTHOR_ID WHERE name = ? AND surname = ?";
        List<Book> res =  DBWorker.executeQuery(query, (preparedStatement) ->{
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);},
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

    public Book getBookByAuthorAndBookName(String name, String surname, String bookName ){
        Book book;
        String query = "SELECT * FROM BOOKS JOIN AUTHORS ON AUTHORS.ID = BOOKS.AUTHOR_ID WHERE name = ? AND surname = ? AND book_name = ?";
        book =  DBWorker.executeQuery(query, (preparedStatement)->{
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, surname);
                    preparedStatement.setString(3, bookName);},
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

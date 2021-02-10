package DBWork;

import Models.Book;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookManageDB {
    private Logger logger = Logger.getLogger(BookManageDB.class);
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    DBWorker dbWorker = new DBWorker();
    MarksDB marksDB = new MarksDB();


    public void addBookWithAutor(String name, String surname, String bookname, int year, String annotation){
        if (!checkSameAutor(name,surname)) addAutor(name, surname);
        addBook(name, surname, bookname, year, annotation);
        Book book = new Book(name, surname, bookname, year, annotation);
        marksDB.createBookMarks(getBookId(book));
    }

    private boolean checkSameAutor(String name, String surname){
        try {
            preparedStatement = dbWorker.getConnection().prepareStatement("SELECT * FROM AUTORS WHERE name = ? AND surname = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return true;
        }
        catch (SQLException e){
            logger.error(e);
        }
        finally {
            dbWorker.CloseConnect();
        }
        return false;
    }

    private void addAutor(String name, String surname){
        try {
            preparedStatement = dbWorker.getConnection().prepareStatement("INSERT INTO Autors (name, surname) VALUES(?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            logger.error(e);
        }
        finally {
            dbWorker.CloseConnect();
        }
    }

    private void addBook(String name, String surname, String bookName, int year, String annotation){
        try {
            int autorId = getAutorId(name, surname);
            preparedStatement = dbWorker.getConnection().prepareStatement("INSERT INTO BOOKS (autor_id, book_name, year, annotation) VALUES(?,?,?,?)");
            preparedStatement.setInt(1, autorId);
            preparedStatement.setString(2, bookName);
            preparedStatement.setInt(3, year);
            preparedStatement.setString(4, annotation);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            logger.error(e);
        }
        finally {
            dbWorker.CloseConnect();
        }
    }

    private int getAutorId(String name, String surname){
        int autorId=-1;
        try {
            preparedStatement = dbWorker.getConnection().prepareStatement("SELECT ID FROM AUTORS WHERE NAME = ? AND SURNAME = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            autorId = resultSet.getInt("id");
        }
        catch (SQLException e){
            logger.error(e);
        }
        finally {
            dbWorker.CloseConnect();
        }
        return autorId;
    }

    public int getBookId(Book book){
        int bookId=-1;
        try {
            preparedStatement = dbWorker.getConnection().prepareStatement("SELECT ID FROM BOOKS WHERE book_name = ? AND autor_id = (SELECT ID FROM AUTORS WHERE NAME = ? AND SURNAME = ?)");
            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2, book.getName());
            preparedStatement.setString(3, book.getSurname());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            bookId = resultSet.getInt("id");
        }
        catch (SQLException e){
            logger.error(e);
        }
        finally {
            dbWorker.CloseConnect();
        }
        return bookId;
    }

    public int getBookId(String name, String surname, String bookName){
        int bookId=-1;
        try {
            preparedStatement = dbWorker.getConnection().prepareStatement("SELECT ID FROM BOOKS WHERE book_name = ? AND autor_id = (SELECT ID FROM AUTORS WHERE NAME = ? AND SURNAME = ?)");
            preparedStatement.setString(1, bookName);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            bookId = resultSet.getInt("id");
        }
        catch (SQLException e){
            logger.error(e);
        }
        finally {
            dbWorker.CloseConnect();
        }
        return bookId;
    }

    public void deleteBook(int bookId){
        try {
            preparedStatement = dbWorker.getConnection().prepareStatement(
            "DELETE FROM BOOKS WHERE id =  ?");
            preparedStatement.setInt(1,bookId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            logger.error(e);
        }
        finally {
            dbWorker.CloseConnect();
        }
    }

    public Book getBooksByAutorAndBookName(String name, String surname, String bookName ){
        Book book = null;
        try {
            preparedStatement = dbWorker.getConnection().prepareStatement(
                    "SELECT * FROM BOOKS JOIN AUTORS ON AUTORS.ID = BOOKS.AUTOR_ID WHERE name = ? AND surname = ? AND book_name = ?");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,surname);
            preparedStatement.setString(3,bookName);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            book = new Book(
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("book_name"),
                        resultSet.getInt("year"),
                        resultSet.getString("annotation") );

        }
        catch (SQLException e){
            logger.error(e);
        }
        finally {
            dbWorker.CloseConnect();
        }
        return book;
    }

    public List<Book> getAllBooks(){
        List<Book> books = new ArrayList<>();
        try {
            preparedStatement = dbWorker.getConnection().prepareStatement(
                    "SELECT * FROM BOOKS JOIN AUTORS ON BOOKS.AUTOR_ID = AUTORS.ID");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                books.add(new Book(
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("book_name"),
                        resultSet.getInt("year"),
                        resultSet.getString("annotation") )
                );
            }
        }
        catch (SQLException e){
            logger.error(e);
        }
        finally {
            dbWorker.CloseConnect();
        }
        return books;
    }

    public void updateBook(String newAnnotation,int bookId){
        try {
            preparedStatement = dbWorker.getConnection().prepareStatement(
                    "UPDATE books set annotation = ? WHERE id = ?");
            preparedStatement.setString(1,newAnnotation);
            preparedStatement.setInt(2, bookId);
            preparedStatement.executeUpdate();

        }
        catch (SQLException e){
            logger.error(e);
        }
        finally {
            dbWorker.CloseConnect();
        }
    }
}

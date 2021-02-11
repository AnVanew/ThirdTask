package DBWork;

import Models.Book;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookManageDB {
    private Logger logger = Logger.getLogger(BookManageDB.class);
    ResultSet resultSet;
    MarksDB marksDB = new MarksDB();


    public void addBookWithAutor(String name, String surname, String bookname, int year, String annotation){
        if (!checkSameAutor(name,surname)) addAutor(name, surname);
        addBook(name, surname, bookname, year, annotation);
        int bookId = getBookId(name ,surname, bookname);
        marksDB.createBookMarks(bookId);
    }

    public boolean checkSameAutor(String name, String surname){
        String query = "SELECT * FROM AUTORS WHERE name = '" + name +  "' AND surname = '"+surname+"'";
        resultSet = DBWorker.executeQuery(query);
        try {
            if (resultSet.next()) return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DBWorker.closeConnect();
        return false;
    }

    private void addAutor(String name, String surname){
        String query = "INSERT INTO Autors (name, surname) VALUES('"+name+"','"+surname+"')";
        DBWorker.executeUpdate(query);
    }

    private void addBook(String name, String surname, String bookName, int year, String annotation){
        int autorId = getAutorId(name, surname);
        String query = "INSERT INTO BOOKS (autor_id, book_name, year, annotation) VALUES("+autorId+",'"+bookName+"',"+year+",'"+annotation+"')";
        DBWorker.executeUpdate(query);
    }

    private int getAutorId(String name, String surname){
        int autorId=-1;
        String query = "SELECT ID FROM AUTORS WHERE NAME = '"+name+"' AND SURNAME = '"+surname+"'";
        resultSet = DBWorker.executeQuery(query);
        try {
            resultSet.next();
            autorId = resultSet.getInt("id");
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        DBWorker.closeConnect();
        return autorId;
    }

    public int getBookId(Book book){
        int bookId=-1;
        String query = "SELECT ID FROM BOOKS WHERE book_name = '"+book.getBookName()+"' AND autor_id = (SELECT ID FROM AUTORS WHERE NAME = '"+book.getName()+"' AND SURNAME = '"+book.getSurname()+"')";
        resultSet = DBWorker.executeQuery(query);
        try {
            resultSet.next();
            bookId = resultSet.getInt("id");
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        DBWorker.closeConnect();
        return bookId;
    }

    public int getBookId(String name, String surname, String bookName){
        int bookId=-1;
        String query = "SELECT ID FROM BOOKS WHERE book_name = '"+bookName+"' AND autor_id = (SELECT ID FROM AUTORS WHERE NAME = '"+name+"' AND SURNAME = '"+surname+"')";
        resultSet = DBWorker.executeQuery(query);
        try {
            resultSet.next();
            bookId = resultSet.getInt("id");
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        DBWorker.closeConnect();
        return bookId;
    }

    public void deleteBook(int bookId){
        String query = "DELETE FROM BOOKS WHERE id =  "+bookId;
        DBWorker.executeUpdate(query);
    }

    public Book getBooksByAutorAndBookName(String name, String surname, String bookName ){
        Book book = null;
        String query = "SELECT * FROM BOOKS JOIN AUTORS ON AUTORS.ID = BOOKS.AUTOR_ID WHERE name = '"+name+"' AND surname = '"+surname+"' AND book_name = '"+bookName+"'";
        resultSet = DBWorker.executeQuery(query);
        try {
            if (resultSet.next())
                book = new Book(
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("book_name"),
                        resultSet.getInt("year"),
                        resultSet.getString("annotation"));
        } catch (SQLException throwables){
            logger.error(throwables);
        }
        DBWorker.closeConnect();
        return book;
    }

    public List<Book> getAllBooks(){
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM BOOKS JOIN AUTORS ON BOOKS.AUTOR_ID = AUTORS.ID";
        resultSet = DBWorker.executeQuery(query);
        try {
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
        DBWorker.closeConnect();
        return books;
    }

    public void updateBook(String newAnnotation,int bookId){
        String query = "UPDATE books set annotation = '"+newAnnotation+"' WHERE id = "+bookId;
        DBWorker.executeUpdate(query);
    }
}

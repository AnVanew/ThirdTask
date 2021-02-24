package DBWork;

import Models.Author;
import Models.Book;
import org.apache.log4j.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

public class BookManageDB {
    private final static Logger logger = Logger.getLogger(BookManageDB.class);

    public static void addBookWithAuthor(String name, String surname, String bookName, int year, String annotation){
        if (AuthorsDB.checkSameAuthor(name,surname)) AuthorsDB.addAuthor(name, surname);
        addBook(name, surname, bookName, year, annotation);
    }

    private static void addBook(String name, String surname, String bookName, int year, String annotation){
        Author author = AuthorsDB.getAuthorByNameAndSurname(name, surname);
        String query = "INSERT INTO BOOKS (author_id, book_name, year, annotation) VALUES("+author.getId()+",'"+bookName+"',"+year+",'"+annotation+"')";
        DBWorker.executeUpdate(query);
    }

    public static void deleteBook(int bookId){
        String query = "DELETE FROM BOOKS WHERE id =  "+bookId;
        DBWorker.executeUpdate(query);
    }

    public static List<Book> getBooksByAuthor(int authorId){
        List<Book> books = new ArrayList<>();
        String query = "SELECT name, surname, author_id, book_name, year, annotation, BOOKS.id FROM BOOKS JOIN AUTHORS ON BOOKS.AUTHOR_ID = AUTHORS.ID WHERE author_id = ?";
        List<Book> res =  DBWorker.executeQuery(query, (preparedStatement) ->{
            preparedStatement.setInt(1, authorId);},
            BookManageDB::collectBookFromResultSet);
        if(res != null) books = res;
        return books;
    }

    public static List<Book> getAllBooks(){
        List<Book> books;
        String query = "SELECT name, surname, author_id, book_name, year, annotation, BOOKS.id FROM BOOKS JOIN AUTHORS ON BOOKS.AUTHOR_ID = AUTHORS.ID";
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

    public static Book getBookByAuthorAndName(String name, String surName, String bookName){
        Book book;
        Author author = AuthorsDB.getAuthorByNameAndSurname(name, surName);
        String query = "SELECT name, surname, author_id, book_name, year, annotation, BOOKS.id FROM BOOKS JOIN AUTHORS ON BOOKS.AUTHOR_ID = AUTHORS.ID WHERE book_name = ? AND author_id = ?";
        book =  DBWorker.executeQuery(query, (preparedStatement)->{
                    preparedStatement.setString(1, bookName);
                    preparedStatement.setInt(2,author.getId());},
                BookManageDB::bookFromResultSet);
        return book;
    }

    private static Book bookFromResultSet(ResultSet resultSet) throws SQLException{
        Book bookDB = new Book (
               new Author(
                       resultSet.getString("name"),
                       resultSet.getString("surname"),
                       resultSet.getInt("author_id")
               ),
                resultSet.getString("book_name"),
                resultSet.getString("annotation"),
                resultSet.getInt("year"),
                resultSet.getInt("BOOKS.id")
        );
        return bookDB;
    }

    public static void updateBook(String newAnnotation,int bookId){
        String query = "UPDATE books set annotation = '"+newAnnotation+"' WHERE id = "+bookId;
        DBWorker.executeUpdate(query);
    }
}

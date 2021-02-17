package DBWork;

import Models.Autor;
import Models.Book;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BookManageDB {
    private Logger logger = Logger.getLogger(BookManageDB.class);

    public void addBookWithAutor(String name, String surname, String bookname, int year, String annotation){
        if (checkSameAutor(name,surname)) addAutor(name, surname);
        addBook(name, surname, bookname, year, annotation);
    }

    public void addAutor(String name, String surname){
        if (checkSameAutor(name, surname)){
            String query = "INSERT INTO Autors (name, surname) VALUES('"+name+"','"+surname+"')";
            logger.info(name +" "+ surname);
            DBWorker.executeUpdate(query);
        }
    }

    private boolean checkSameAutor(String name, String surname){
        if( getAutorId(name, surname) == -1) return true;
        return false;
    }

    private void addBook(String name, String surname, String bookName, int year, String annotation){
        int autorId = getAutorId(name, surname);
        String query = "INSERT INTO BOOKS (autor_id, book_name, year, annotation) VALUES("+autorId+",'"+bookName+"',"+year+",'"+annotation+"')";
        DBWorker.executeUpdate(query);
    }

    private int getAutorId(String name, String surname){
        int autorId=-1;
        String query = "SELECT ID FROM AUTORS WHERE NAME = '"+name+"' AND SURNAME = '"+surname+"'";
        Object res =  DBWorker.executeQuery(query, (resultSet) ->{
           int id = resultSet.getInt(1);
           return id;
        });
        if (res != null) autorId = (int) res;
        return autorId;
    }

    public Autor getAutorById(int id){
        Autor autor = null;
        String query = "SELECT * FROM AUTORS WHERE id = " +id;
        try {
            autor = (Autor) DBWorker.executeQuery(query, (resultSet) -> {
                Autor autorDB = new Autor(
                        resultSet.getString("name"),
                        resultSet.getString("surname")
                );
                return autorDB;
            });
        }catch (Exception e){
            logger.info("Autor not found");
            logger.error(e);
        }
        return autor;
    }

    public int getBookId(Book book){
        int bookId=-1;
        String query = "SELECT ID FROM BOOKS WHERE book_name = '"+book.getBookName()+"' AND autor_id = (SELECT ID FROM AUTORS WHERE NAME = '"+book.getName()+"' AND SURNAME = '"+book.getSurname()+"')";
        bookId = (int) DBWorker.executeQuery(query, (resultSet)->{
            int id = resultSet.getInt(1);
            return id;
        });
        return bookId;
    }

    public int getBookId(String name, String surname, String bookName){
        int bookId=-1;
        String query = "SELECT ID FROM BOOKS WHERE book_name = '"+bookName+"' AND autor_id = (SELECT ID FROM AUTORS WHERE NAME = '"+name+"' AND SURNAME = '"+surname+"')";
        bookId = (int) DBWorker.executeQuery(query, (resultSet)->{
            int id = resultSet.getInt(1);
            return id;
        });
        return bookId;
    }

    public void deleteBook(int bookId){
        String query = "DELETE FROM BOOKS WHERE id =  "+bookId;
        DBWorker.executeUpdate(query);
    }

    public List<Book> getBooksByAutor(String name, String surname){
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM BOOKS JOIN AUTORS ON AUTORS.ID = BOOKS.AUTOR_ID WHERE name = '"+name+"' AND surname = '"+surname+"'";
        Object res = (List<Book>) DBWorker.executeQuery(query, (resultSet) ->{
            List<Book> booksList = new ArrayList<>();
            do{
                booksList.add(new Book(
                        new Autor(
                                resultSet.getString("name"),
                                resultSet.getString("surname")
                        ),
                        resultSet.getString("book_name"),
                        resultSet.getInt("year"),
                        resultSet.getString("annotation") )
                );
            }while (resultSet.next());
            return booksList;
        });
        if(res != null) books = (List<Book>) res;
        return books;
    }

    public Book getBookByAutorAndBookName(String name, String surname, String bookName ){
        Book book = null;
        String query = "SELECT * FROM BOOKS JOIN AUTORS ON AUTORS.ID = BOOKS.AUTOR_ID WHERE name = '"+name+"' AND surname = '"+surname+"' AND book_name = '"+bookName+"'";
        book = (Book) DBWorker.executeQuery(query, (resultSet)->{
            Book bookDB = new Book(
                    new Autor(
                            resultSet.getString("name"),
                            resultSet.getString("surname")
                    ),
                    resultSet.getString("book_name"),
                    resultSet.getInt("year"),
                    resultSet.getString("annotation"));
            return bookDB;
        });
        return book;
    }

    public List<Book> getAllBooks(){
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM BOOKS JOIN AUTORS ON BOOKS.AUTOR_ID = AUTORS.ID";
        books = (List<Book>) DBWorker.executeQuery(query, (resultSet) ->{
            List<Book> booksList = new ArrayList<>();
                do {
                    booksList.add(new Book(
                            new Autor(
                                    resultSet.getString("name"),
                                    resultSet.getString("surname")
                            ),
                            resultSet.getString("book_name"),
                            resultSet.getInt("year"),
                            resultSet.getString("annotation"))
                    );
                } while (resultSet.next());
            return booksList;
        });
        return books;
    }

    public List<Autor> getAllAutors(){
        List<Autor> autors = new ArrayList<>();
        String query = "SELECT * FROM AUTORS";
        autors = (List<Autor>) DBWorker.executeQuery(query, (resultSet)->{
            List<Autor> autorsList = new ArrayList<>();
            do {
                autorsList.add(new Autor(
                        resultSet.getString("name"),
                        resultSet.getString("surname")) );
            }while (resultSet.next());
            return autorsList;
        });
        return autors;
    }

    public void updateBook(String newAnnotation,int bookId){
        String query = "UPDATE books set annotation = '"+newAnnotation+"' WHERE id = "+bookId;
        DBWorker.executeUpdate(query);
    }
}

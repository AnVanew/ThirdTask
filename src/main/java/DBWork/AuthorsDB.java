package DBWork;

import Models.Author;
import org.apache.log4j.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorsDB {
    private static final Logger logger = Logger.getLogger(AuthorsDB.class);

    public static void addAuthor(String name, String surname){
        if (checkSameAuthor(name, surname)){
            String query = "INSERT INTO Authors (name, surname) VALUES('"+name+"','"+surname+"')";
            DBWorker.executeUpdate(query);
        }
    }

    public static boolean checkSameAuthor(String name, String surname){
        if( getAuthorId(name, surname) == -1) return true;
        return false;
    }

    public static int getAuthorId(String name, String surname){
        int authorId=-1;
        String query = "SELECT ID FROM AUTHORS WHERE NAME = ? AND SURNAME = ?";
        Integer res =  DBWorker.executeQuery(query, (preparedStatement) ->{
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, surname);},
                (resultSet) ->{
                    return resultSet.getInt(1);
                });
        if (res != null) authorId =  res;
        return authorId;
    }

    public static Author getAuthorById(int id){
        Author author;
        String query = "SELECT * FROM AUTHORS WHERE id = ?";
        author = DBWorker.executeQuery(query, (preparedStatement) -> {
            preparedStatement.setInt(1, id);},
            AuthorsDB::authorFromResultSet);
        if (author == null )logger.info("Author not found");
        return author;
    }

    public static List<Author> getAllAuthors(){
        List<Author> authors;
        String query = "SELECT * FROM AUTHORS";
        authors = DBWorker.executeQuery(query, (preparedStatement)->{},
            AuthorsDB::collectAuthorFromResultSet);
        return authors;
    }

    private static List<Author> collectAuthorFromResultSet(ResultSet resultSet) throws SQLException {
        List<Author> autorsList = new ArrayList<>();
        do { autorsList.add( authorFromResultSet(resultSet) );
        }while (resultSet.next());
        return autorsList;
    }

    private static Author authorFromResultSet(ResultSet resultSet) throws SQLException {
        String name =  resultSet.getString("name");
        String surname =  resultSet.getString("surname");
        Author author = new Author(name, surname);
        return author;
    }
}

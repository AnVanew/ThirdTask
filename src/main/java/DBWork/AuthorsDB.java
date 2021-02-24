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
        Author author = getAuthorByNameAndSurname(name, surname);
        return author == null;
    }

    public static Author getAuthorById(int id){
        Author author;
        String query = "SELECT name, surname, id FROM AUTHORS WHERE id = ?";
        author = DBWorker.executeQuery(query, (preparedStatement) -> {
            preparedStatement.setInt(1, id);},
            AuthorsDB::authorFromResultSet);
        if (author == null )logger.info("Author not found");
        return author;
    }

    public static Author getAuthorByNameAndSurname(String name, String surname){
        Author author;
        String query = "SELECT name, surname, id FROM AUTHORS WHERE name = ? AND surname= ? ";
        author = DBWorker.executeQuery(query, (preparedStatement) -> {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, surname);},
                AuthorsDB::authorFromResultSet);
        if (author == null )logger.info("Author not found");
        return author;
    }

    public static List<Author> getAllAuthors(){
        List<Author> authors;
        String query = "SELECT name, surname, id FROM AUTHORS";
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
        int id = resultSet.getInt("AUTHORS.id");
        Author author = new Author(name, surname, id);
        return author;
    }
}

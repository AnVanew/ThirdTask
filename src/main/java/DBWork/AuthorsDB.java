package DBWork;

import Models.Author;
import org.apache.log4j.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static DBWork.DataBaseConst.*;

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
        String query = "SELECT " +
                "name " + AUTHOR_NAME_COLUMN +
                ", surname " + AUTHOR_SURNAME_COLUMN +
                ", id " + AUTHOR_ID_COLUMN +
                " FROM AUTHORS a " +
                " WHERE a.id = ?";
        author = DBWorker.executeQuery(query, (preparedStatement) -> {
            preparedStatement.setInt(1, id);},
            AuthorsDB::getAuthorFromResultSet);
        if (author == null )logger.info("Author not found");
        return author;
    }

    public static Author getAuthorByNameAndSurname(String name, String surname){
        Author author;
        String query = "SELECT " +
                "name " + AUTHOR_NAME_COLUMN +
                ", surname " + AUTHOR_SURNAME_COLUMN +
                ", id " + AUTHOR_ID_COLUMN +
                " FROM AUTHORS a " +
                " WHERE a.name = ? AND a.surname= ? ";
        author = DBWorker.executeQuery(query, (preparedStatement) -> {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);},
            AuthorsDB::getAuthorFromResultSet);
        if (author == null )logger.info("Author not found");
        return author;
    }

    public static List<Author> getAllAuthors(){
        List<Author> authors;
        String query = "SELECT " +
                "name " + AUTHOR_NAME_COLUMN +
                ", surname " + AUTHOR_SURNAME_COLUMN +
                ", id " + AUTHOR_ID_COLUMN +
                " FROM AUTHORS ";
        authors = DBWorker.executeQuery(query, (preparedStatement)->{},
            AuthorsDB::collectAuthorFromResultSet);
        return authors;
    }

    private static List<Author> collectAuthorFromResultSet(ResultSet resultSet) throws SQLException {
        List<Author> authorsList = new ArrayList<>();
        do { authorsList.add( getAuthorFromResultSet(resultSet) );
        }while (resultSet.next());
        return authorsList;
    }

    public static Author getAuthorFromResultSet(ResultSet resultSet) throws SQLException {
        String name =  resultSet.getString(AUTHOR_NAME_COLUMN);
        String surname =  resultSet.getString(AUTHOR_SURNAME_COLUMN);
        int id = resultSet.getInt(AUTHOR_ID_COLUMN);
        Author author = new Author(name, surname, id);
        return author;
    }
}

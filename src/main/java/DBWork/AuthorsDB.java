package DBWork;

import Models.Author;
import org.apache.log4j.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorsDB {
    Logger logger = Logger.getLogger(AuthorsDB.class);

    public void addAuthor(String name, String surname){
        if (checkSameAuthor(name, surname)){
            String query = "INSERT INTO Authors (name, surname) VALUES('"+name+"','"+surname+"')";
            DBWorker.executeUpdate(query);
        }
    }

    public boolean checkSameAuthor(String name, String surname){
        if( getAuthorId(name, surname) == -1) return true;
        return false;
    }

    public int getAuthorId(String name, String surname){
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

    public Author getAuthorById(int id){
        Author author;
        String query = "SELECT * FROM AUTHORS WHERE id = ?";
        author = DBWorker.executeQuery(query, (preparedStatement) -> {
            preparedStatement.setInt(1, id);},
            this::authorFromResultSet);
        if (author == null )logger.info("Author not found");
        return author;
    }

    public List<Author> getAllAuthors(){
        List<Author> authors;
        String query = "SELECT * FROM AUTHORS";
        authors = DBWorker.executeQuery(query, (preparedStatement)->{},
            this::collectAuthorFromResultSet);
        return authors;
    }

    private List<Author> collectAuthorFromResultSet(ResultSet resultSet) throws SQLException {
        List<Author> autorsList = new ArrayList<>();
        do { autorsList.add( authorFromResultSet(resultSet) );
        }while (resultSet.next());
        return autorsList;
    }

    private Author authorFromResultSet(ResultSet resultSet) throws SQLException {
        Author author = new Author(
            resultSet.getString("name"),
            resultSet.getString("surname"));
        return author;
    }
}

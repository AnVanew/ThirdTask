package DBWork;

import org.apache.log4j.Logger;
import java.sql.*;

public class DBWorker {

    private final static Logger logger = Logger.getLogger(DBWorker.class);
    private final static String URL = "jdbc:h2:mem:default;DB_CLOSE_DELAY=-1";
    private final static String user = "postgres";
    private final static String password = "1709dada99";

    private static Connection createConnection() throws SQLException {
        Connection connection;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(URL, user, password);
            logger.info("New connect   "+ connection);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            throw new SQLException();
        }
        return connection;
    }

    public static void executeUpdate(String querry) {
        try (Connection connection = createConnection()) {
            connection.createStatement().executeUpdate(querry);
        }
        catch (SQLException e){
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T executeQuery(String queryString, StatementConsumer consumer, ResultSetConsumer<T> resultSetConsumer) {
        ResultSet resultSet;
        T t = null;
        try(Connection connection = createConnection()) {
                PreparedStatement statement = connection.prepareStatement(queryString);
                consumer.consume(statement);
                resultSet = statement.executeQuery();
                if (resultSet.next()) t = resultSetConsumer.consume(resultSet);
        } catch (Exception e)  {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return t;
    }

    @FunctionalInterface
    public interface StatementConsumer {
        void consume(PreparedStatement preparedStatement) throws Exception;
    }

    @FunctionalInterface
    public  interface ResultSetConsumer <T> {
        T consume(ResultSet resultSet) throws Exception;
    }

    public static void  createDB(){
        String createBooksTable = "CREATE TABLE books (\n" +
                "    id int PRIMARY KEY AUTO_INCREMENT,\n" +
                "    author_id int,\n" +
                "    book_name VARCHAR (50),\n" +
                "    year int,\n" +
                "    annotation VARCHAR(200),\n" +
                "    FOREIGN KEY (author_id) REFERENCES AUTHORS (id)\n" +
                ")";
        String createAuthorsTable = "CREATE TABLE authors (\n" +
                "    id int PRIMARY KEY AUTO_INCREMENT,\n" +
                "    name VARCHAR (50),\n" +
                "    surname VARCHAR (100)\n" +
                ")";
        String createMarksTable = "CREATE TABLE marks (\n" +
                "    id int PRIMARY KEY AUTO_INCREMENT,\n" +
                "    book_id int,\n" +
                "    likes int,\n" +
                "    dislikes int,\n" +
                "    FOREIGN KEY (book_id) REFERENCES BOOKS (id)\n" +
                ")";
        String createCommentsTable = "CREATE TABLE comments (\n" +
                "    id int PRIMARY KEY AUTO_INCREMENT,\n" +
                "    book_id int,\n" +
                "    user_name VARCHAR (50),\n" +
                "    comment VARCHAR (1000),\n" +
                "    FOREIGN KEY (book_id) REFERENCES BOOKS (id)\n" +
                ")";
        String createNameSurnameIndex = "CREATE INDEX name_surname_index ON authors (name, surname)";
        String createAuthorIdIndex = "CREATE INDEX authorId_index ON books (author_id)";
        String createBookNameAuthorIdIndex = "CREATE INDEX bookName_authorId_index ON books (book_name, author_id)";

        DBWorker.executeUpdate(createAuthorsTable);
        DBWorker.executeUpdate(createBooksTable);
        DBWorker.executeUpdate(createMarksTable);
        DBWorker.executeUpdate(createCommentsTable);
        DBWorker.executeUpdate(createNameSurnameIndex);
        DBWorker.executeUpdate(createAuthorIdIndex);
        DBWorker.executeUpdate(createBookNameAuthorIdIndex);
    }
}

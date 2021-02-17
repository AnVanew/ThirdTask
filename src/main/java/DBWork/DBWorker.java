package DBWork;

import org.apache.log4j.Logger;

import java.sql.*;

public class DBWorker {

    private static Logger logger = Logger.getLogger(DBWorker.class);
    private static Connection connection;
    private static String URL = "jdbc:h2:mem:default;DB_CLOSE_DELAY=-1";
    private static String user = "postgres";
    private static String password = "1709dada99";

    public static Connection getConnection () throws SQLException {
        connection =  createConnection();
        return connection;
    }

    private static Connection createConnection() throws SQLException {
        Connection connection = null;
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

    public static void closeConnect() {
        try {
            connection.close();
            logger.info("Connection   "+ connection + "  is close");
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public static void executeUpdate(String querry) {
        try {
            Connection connection = createConnection();
            try {
                connection.createStatement().executeUpdate(querry);
            }
            catch (SQLException e){
                logger.error(e);
            }
            finally {
                connection.close();
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
    }

    public static Object executeQuery(String queryString, StatementConsumer consumer) {
        Object t = null;
        try {
            Connection connection = createConnection();
            try {
                PreparedStatement statement = connection.prepareStatement(queryString);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) t = consumer.consume(resultSet);
            } finally {
                connection.close();
                logger.info("Connection   "+ connection + "  is close");
            }
        } catch (Exception e)  {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return t;
    }

    @FunctionalInterface
    public interface StatementConsumer {
        Object consume(ResultSet resultSet) throws Exception;
    }


    public static void  createDB(){
        String createBooksTable = "CREATE TABLE books (\n" +
                "    id int PRIMARY KEY AUTO_INCREMENT,\n" +
                "    autor_id int,\n" +
                "    book_name VARCHAR (50),\n" +
                "    year int,\n" +
                "    annotation VARCHAR(200),\n" +
                "    FOREIGN KEY (autor_id) REFERENCES AUTORS (id)\n" +
                ")";
        String createAutorsTable = "CREATE TABLE autors (\n" +
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
        String createNameSurnameIndex = "CREATE INDEX name_surname_index ON autors (name, surname)";
        String createAutorIdIndex = "CREATE INDEX autorId_index ON books (autor_id)";
        String createBookNameAutorIdIndex = "CREATE INDEX bookName_autorId_index ON books (book_name, autor_id)";

        DBWorker.executeUpdate(createAutorsTable);
        DBWorker.executeUpdate(createBooksTable);
        DBWorker.executeUpdate(createMarksTable);
        DBWorker.executeUpdate(createCommentsTable);
        DBWorker.executeUpdate(createNameSurnameIndex);
        DBWorker.executeUpdate(createAutorIdIndex);
        DBWorker.executeUpdate(createBookNameAutorIdIndex);
    }

}

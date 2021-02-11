package DBWork;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public static ResultSet executeQuery(String querry) {
        ResultSet resultSet = null;
        try {
            resultSet = getConnection().createStatement().executeQuery(querry);
        } catch (SQLException throwables) {
            closeConnect();
            logger.error(throwables);
        }
        return resultSet;
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

}

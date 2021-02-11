package DBWork;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBWorker {

    private static Logger logger = Logger.getLogger(DBWorker.class);
    private static Connection connection;
    private static String URL = "jdbc:h2:file:C:/Users/naic infa/Desktop/prog/Java/ThirdTask/DataBase";
    private static String user = "postgres";
    private static String password = "1709dada99";

    public static Connection getConnection () throws SQLException {
        createConnection();
        return connection;
    }

    private static void createConnection() throws SQLException {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(URL, user, password);
            logger.info("New connect   "+ connection);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            throw new SQLException();
        }
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
          getConnection().createStatement().executeUpdate(querry);
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        finally {
            closeConnect();
        }
    }

}

package DBWork;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBWorker {

    private Logger logger = Logger.getLogger(DBWorker.class);
    private Connection connection;
    private String URL = "jdbc:h2:file:C:/Users/naic infa/Desktop/prog/Java/ThirdTask/DataBase";
    private String user = "postgres";
    private String password = "1709dada99";

    public Connection getConnection () throws SQLException {
        createConnection();
        return connection;
    }

    void createConnection() throws SQLException {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(URL, user, password);
            logger.info("New connect   "+ connection);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            throw new SQLException();
        }
    }
    public void CloseConnect() {
        try {
            connection.close();
            logger.info("Connection   "+ connection + "  is close");
        } catch (SQLException e) {
            logger.error(e);
        }
    }

}

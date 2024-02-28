package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @description: 配置类
 * @author Linweijun
 * @date 2024/1/2 22:43
 * @version 1.0
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/traindb";//traindb
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";
    private Connection connection;

    public void openConnection() {

        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            Logutil.getInstance().getLogger().severe(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

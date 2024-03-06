package utils;

import lombok.Getter;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Linweijun
 * @version 1.0
 * @description: 配置类
 * @date 2024/1/2 22:43
 */
@Getter
public class DatabaseConnection {
    private static final String URL;
    private static final String USER_NAME;
    private static final String PASSWORD;
    private Connection connection;

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("MyService/config/db.properties"));
            URL = properties.getProperty("url");
            USER_NAME = properties.getProperty("username");
            PASSWORD = properties.getProperty("password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openConnection() {

        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            Logutil.getInstance().getLogger().severe(e.getMessage());
        }
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

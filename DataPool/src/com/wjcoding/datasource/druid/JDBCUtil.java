package com.wjcoding.datasource.druid;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import javax.sql.StatementEvent;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @author Cynicism
 * @version 1.0
 * @project JavaWeb
 * @description 工具
 * @date 2024/3/3 21:46:09
 */
public class JDBCUtil {

    private static DataSource dataSource;

    static {
        Properties properties = new Properties();
        //放在src下可以通过classloader获取
        try {
            properties.load(DruidDemo.class.getClassLoader().getResourceAsStream("druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close(Statement statement,Connection connection){
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
       close(statement, connection);
    }

    public static DataSource getDataSource(){
        return dataSource;
    }


}

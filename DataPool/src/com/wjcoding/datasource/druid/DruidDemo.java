package com.wjcoding.datasource.druid;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Cynicism
 * @version 1.0
 * @project JavaWeb
 * @description 阿里的数据连接池
 * @date 2024/3/3 21:30:57
 */
public class DruidDemo {

    public static void main(String[] args)  {
//        Properties properties = new Properties();
//        //放在src下可以通过classloader获取
//        properties.load(DruidDemo.class.getClassLoader().getResourceAsStream("druid.properties"));
//        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
//
//        System.out.println(dataSource.getConnection());
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            statement = connection.prepareStatement("select * from train_number");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                System.out.println(resultSet.getString(1)+" "+ resultSet.getString(2)+" "+ resultSet.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.close(resultSet,statement,connection);
        }



    }
}

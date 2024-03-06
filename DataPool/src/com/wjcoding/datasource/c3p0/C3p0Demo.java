package com.wjcoding.datasource.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Cynicism
 * @version 1.0
 * @project JavaWeb
 * @description c3p0数据连接池
 * @date 2024/3/3 20:56:53
 */
public class C3p0Demo {
    public static void main(String[] args) throws SQLException {
        //常用空参默认配置，同时连接数量超过定义的数量报错，xml配置文件需要在类路径下，也就是src，xml文件名必须是这个
        DataSource ds = new ComboPooledDataSource();
        Connection connection = ds.getConnection();
        System.out.println(connection);
    }
}

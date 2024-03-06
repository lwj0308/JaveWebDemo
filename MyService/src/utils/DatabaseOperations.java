package utils;

import lombok.extern.java.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @description: 数据库操作
 * @author Linweijun
 * @date 2024/1/2 22:47
 * @version 1.0
 */
@Log
public class DatabaseOperations {
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    private static volatile DatabaseOperations instance;

    public static DatabaseOperations getInstance(){
        if (instance == null){
            synchronized (DatabaseOperations.class){
                if (instance == null){
                    instance = new DatabaseOperations();
                }
            }
        }
        return instance;
    }
    private DatabaseOperations() {
        databaseConnection.openConnection();
    }
    public ConcurrentLinkedDeque<ResultSet> resultSets = new ConcurrentLinkedDeque<>();
    public ConcurrentLinkedDeque<PreparedStatement> preparedStatements = new ConcurrentLinkedDeque<>();

    public ResultSet executeQuery(String sql, Object... objects) {
        if (isCloseConnection()) {
            reOpen();
        }
        try  {
            PreparedStatement statement = databaseConnection.getConnection().prepareStatement(sql);
            preparedStatements.add(statement);
            for (int i = 0; i < objects.length; i++) {
                statement.setObject(i + 1, objects[i]);
            }
            ResultSet resultSet = statement.executeQuery();
            resultSets.add(resultSet);
            return resultSet;
        } catch (SQLException e) {
            closePC();
            throw new RuntimeException(e);
        }
    }

    public int executeUpdate(String sql, Object... objects) {
        if (isCloseConnection()) {
            reOpen();
        }
        try {
            PreparedStatement statement = databaseConnection.getConnection().prepareStatement(sql);
            preparedStatements.add(statement);
            for (int i = 0; i < objects.length; i++) {
                statement.setObject(i + 1, objects[i]);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            closePC();
            throw new RuntimeException(e);
        }
    }
    /**
     * 功能描述:
     * 释放资源
     *
     * @return: void
     * @author Linweijun
     * @date 2024/1/3 9:13
     */
    public void closePC(){
        preparedStatements.forEach(p -> {
            try {
                p.close();
                preparedStatements.remove(p);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        databaseConnection.closeConnection();
    }

    /**
     * 功能描述:
     * 数据库连接是否断开
     *
     * @return: boolean
     * @author Linweijun
     * @date 2024/1/3 9:13
     */
    public boolean isCloseConnection(){
        try {
           return databaseConnection.getConnection().isClosed();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 功能描述:
     * 重连
     *
     * @return: void
     * @author Linweijun
     * @date 2024/1/3 9:13
     */
    public void reOpen(){
        databaseConnection.openConnection();
    }

}

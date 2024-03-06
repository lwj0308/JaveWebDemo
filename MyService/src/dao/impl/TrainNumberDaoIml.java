package dao.impl;

import dao.TrainNumberDao;
import entity.Page;
import entity.TrainNumber;
import utils.DatabaseOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrainNumberDaoIml implements TrainNumberDao {
    //总页数
    int count = 0;

    @Override
    public Page<TrainNumber> find(String start, String end, String page) {
        DatabaseOperations databaseOperations = DatabaseOperations.getInstance();
        Page<TrainNumber> trainNumberPage = new Page<>();

        String url = "SELECT * FROM train_number WHERE startstationid like ? AND endstationid like ? limit ?,?;";
        int startIndex;
        if (start == null) {
            start = "";
        }
        if (end == null) {
            end = "";
        }
        if (page == null) {
            startIndex = 0;
        } else {
            startIndex = (Integer.parseInt(page) - 1) * trainNumberPage.getPageSize();
        }

        try (ResultSet resultSet = databaseOperations.executeQuery(url, "%" + start + "%", "%" + end + "%", startIndex, trainNumberPage.getPageSize());
        ) {
            while (resultSet.next()) {
                String number = resultSet.getString(1);
                String startstationid = resultSet.getString(4);
                String endstationid = resultSet.getString(5);
                String starttime = resultSet.getString(2);
                String endtime = resultSet.getString(3);
                trainNumberPage.getItem().add(new TrainNumber(number, startstationid, endstationid, starttime, endtime));
            }

        } catch (Exception e) {
            databaseOperations.closePC();
            System.out.println("error:" + e.getMessage());
        }
        trainNumberPage.setRowCount(count(start, end));
        trainNumberPage.setPageCount(trainNumberPage.getRowCount() / trainNumberPage.getPageSize() + (trainNumberPage.getRowCount() % trainNumberPage.getPageSize() != 0 ? 1 : 0));

        return trainNumberPage;
    }

    /**
     * 获取总数
     * @param start
     * @param end
     * @return
     */
    public int count(String start, String end) {
        DatabaseOperations databaseOperations = DatabaseOperations.getInstance();
        String url = "SELECT COUNT(*) FROM train_number WHERE startstationid like ? AND endstationid like ?";
        ResultSet resultSet = databaseOperations.executeQuery(url, "%" + start + "%", "%" + end + "%");
        try {
            if (resultSet.next()) {
                count = Integer.parseInt(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    @Override
    public boolean delete(String number) {
        DatabaseOperations databaseOperations = DatabaseOperations.getInstance();
        int i = databaseOperations.executeUpdate("delete from train_number where number = ? ", number);
        return i > 0;
    }

}

package dao.impl;

import dao.TrainNumberDao;
import entity.TrainNumber;
import utils.DatabaseOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainNumberDaoIml implements TrainNumberDao {
    //总页数
    int count = 0;

    @Override
    public List<TrainNumber> find(String start, String end, String page) {
        DatabaseOperations databaseOperations = DatabaseOperations.getInstance();
        String url = "SELECT * FROM train_number WHERE startstationid like ? AND endstationid like ? limit ?,5;";
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
            startIndex = (Integer.parseInt(page) - 1) * 5;

        }
        List<TrainNumber> trainNumbers = new ArrayList<>();
        try (ResultSet resultSet = databaseOperations.executeQuery(url, "%" + start + "%", "%" + end + "%", startIndex);
        ) {
            while (resultSet.next()) {
                String number = resultSet.getString(1);
                String startstationid = resultSet.getString(4);
                String endstationid = resultSet.getString(5);
                String starttime = resultSet.getString(2);
                String endtime = resultSet.getString(3);
                trainNumbers.add(new TrainNumber(number, startstationid, endstationid, starttime, endtime));
            }

        } catch (Exception e) {
            //TODO: handle exception
            databaseOperations.closePC();
            System.out.println("error:" + e.getMessage());
        }

        return trainNumbers;
    }

    @Override
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

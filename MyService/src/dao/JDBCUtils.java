package dao;

import entity.TrainNumber;
import utils.DatabaseOperations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JDBCUtils {
    public List<TrainNumber> numberFind(DatabaseOperations connection, String start, String end) {
        String url = "SELECT * FROM train_number WHERE startstationid like ? AND endstationid like ?;";

        List<TrainNumber> trainNumbers = new ArrayList<>();
        try (ResultSet resultSet = connection.executeQuery(url, "%"+start+"%", "%"+end+"%");
        ) {
            while (resultSet.next()) {
                String number = resultSet.getString(1);
                String startstationid = resultSet.getString(4);
                String endstationid = resultSet.getString(5);
                String starttime = resultSet.getString(2);
                String endtime = resultSet.getString(3);
                trainNumbers.add(new TrainNumber(number, startstationid, endstationid,starttime,endtime));
            }

        } catch (Exception e) {
            //TODO: handle exception
            connection.closePC();
            System.out.println("error:"+e.getMessage());
        }

        return trainNumbers;
    }

}

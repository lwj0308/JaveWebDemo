package dao;

import entity.TrainNumber;

import java.util.List;

public interface TrainNumberDao {
   List<TrainNumber> find (String start, String end, String page);
   int count (String start, String end);

   boolean delete(String number);
}

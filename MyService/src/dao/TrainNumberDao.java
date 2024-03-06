package dao;

import entity.Page;
import entity.TrainNumber;

import java.util.List;

public interface TrainNumberDao {
   Page<TrainNumber> find (String start, String end, String page);

   boolean delete(String number);
}

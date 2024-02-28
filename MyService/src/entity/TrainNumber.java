package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

//和表的字段一样
@Data
@AllArgsConstructor
public class TrainNumber {
    private String num;
    private String startStationid;
    private String endStationid;
    private String starttime;
    private String endtime;
}

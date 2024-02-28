package servlet;

import com.alibaba.fastjson2.JSON;
import dao.JDBCUtils;

import dto.Response;
import entity.TrainNumber;
import net_utils.HttpRequest;
import net_utils.HttpResponse;
import net_utils.HttpServlet;
import utils.DatabaseOperations;

import java.util.List;
import java.util.Map;

public class TrainNumberServlet extends HttpServlet {
    DatabaseOperations databaseOperations;
    public TrainNumberServlet(DatabaseOperations databaseOperations) {
     this.databaseOperations = databaseOperations;
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        // 处理查询车次的逻辑
        Map<String, String> params = request.getParams();
        String start = params.get("start");
        String end = params.get("end");
        JDBCUtils jdbcUtils = new JDBCUtils();
        List<TrainNumber> trainNumbers = jdbcUtils.numberFind(databaseOperations, start, end);

        Response responseDTO = new Response(200, "查询成功", trainNumbers);
        String jsonString = JSON.toJSONString(responseDTO);
        response.write("200",jsonString);
    }
}

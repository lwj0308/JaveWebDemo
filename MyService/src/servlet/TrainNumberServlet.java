package servlet;

import com.alibaba.fastjson2.JSON;

import dao.TrainNumberDao;
import dao.impl.TrainNumberDaoIml;
import dto.ResponseDTO;
import entity.TrainNumber;
import net_utils.HttpRequest;
import net_utils.HttpResponse;

import java.util.List;
import java.util.Map;

public class TrainNumberServlet extends HttpServlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        // 处理查询车次的逻辑
        Map<String, String> params = request.getParams();
        String start = params.get("start");
        String end = params.get("end");
        String page = params.get("page");
        TrainNumberDao trainNumberDao = new TrainNumberDaoIml();
        List<TrainNumber> trainNumbers = trainNumberDao.find(start,end,page);
        ResponseDTO responseDTO = new ResponseDTO(200, "查询成功", trainNumbers);
        String jsonString = JSON.toJSONString(responseDTO);
        response.write("200",jsonString);
    }
}

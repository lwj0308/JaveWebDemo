package servlet;

import com.alibaba.fastjson2.JSON;
import dao.TrainNumberDao;
import dao.impl.TrainNumberDaoIml;
import dto.ResponseDTO;
import net_utils.HttpRequest;
import net_utils.HttpResponse;

import java.util.Map;

/**
 * @author Cynicism
 * @version 1.0
 * @project JavaWeb
 * @description 查询车次条数
 * @date 2024/3/2 19:46:23
 */
public class TrainNumberPageServlet extends HttpServlet {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        Map<String, String> params = request.getParams();
        String start = params.get("start");
        String end = params.get("end");
        TrainNumberDao trainNumberDao = new TrainNumberDaoIml();
        int count = trainNumberDao.count(start, end);
        ResponseDTO responseDTO = new ResponseDTO(200, "查询成功", count);
        String jsonString = JSON.toJSONString(responseDTO);
        response.write("200",jsonString);


    }
}

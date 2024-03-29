package servlet;

import annotation.WebServlet;
import net_utils.HttpRequest;
import net_utils.HttpResponse;
import utils.DatabaseOperations;
import utils.Logutil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/loginIndex")
public class LoginServlet extends HttpServlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        Map<String, String> params = request.getParams();
        String acc = params.get("userid");
        String pwd = params.get("password");
        Logutil.getInstance().getLogger().info("GET：userid" + acc + " password:" + pwd);

        ResultSet resultSet = databaseOperations.executeQuery("select * from train_user where userid=? and password=?", acc, pwd);

        try {
            if (resultSet.next()) {
                response.write("200", "登录成功");
            } else {
                response.write("200", "登录失败");
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

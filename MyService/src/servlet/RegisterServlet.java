package servlet;

import net_utils.HttpRequest;
import net_utils.HttpResponse;
import utils.DatabaseOperations;
import utils.Logutil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class RegisterServlet extends HttpServlet {


    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        Map<String, String> params = request.getParams();
        if (params.get("userid") == null || params.get("password") == null) {
            Logutil.getInstance().getLogger().info("账号或密码为空");
            return;
        };
        String acc = params.get("userid");
        String pwd = params.get("password");
        try (ResultSet resultSet = databaseOperations.executeQuery("select * from train_user where userid=?",acc)){
            if (resultSet.next()) {
                response.write("200","账号存在");
            } else {
                int i = databaseOperations.executeUpdate("insert into train_user(userid,password) values(?,?)",acc, pwd);
                if (i>0){
                    response.write("200","注册成功");
                } else {
                    response.write("200","注册失败");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

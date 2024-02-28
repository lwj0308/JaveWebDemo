package servlet;

import net_utils.HttpRequest;
import net_utils.HttpResponse;
import net_utils.HttpServlet;
import utils.DatabaseOperations;
import utils.Logutil;

import javax.print.attribute.standard.PrinterURI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class RegisterServlet extends HttpServlet {
    DatabaseOperations databaseOperations;
    public RegisterServlet(DatabaseOperations databaseOperations) {
        this.databaseOperations = databaseOperations;
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        Map<String, String> params = request.getParams();
        if (params.get("acc") == null || params.get("pwd") == null) {
            Logutil.getInstance().getLogger().info("账号或密码为空");
            return;
        };
        String acc = params.get("acc");
        String pwd = params.get("pwd");
        ResultSet resultSet = databaseOperations.executeQuery("select * from tb_user where account=?",acc);
        try {
            if (resultSet.next()) {
                response.write("200","账号存在");
            } else {
                int i = databaseOperations.executeUpdate("insert into tb_user(account,pwd) values(?,?)", acc, pwd);
                if (i>0){
                    response.write("200","注册成功");
                } else {
                    response.write("200","注册失败");
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

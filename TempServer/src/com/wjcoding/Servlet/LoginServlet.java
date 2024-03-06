package com.wjcoding.Servlet;

import com.wjcoding.http.HttpRequest;
import com.wjcoding.http.HttpResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Cynicism
 * @version 1.0
 * @project JavaWeb
 * @description 登录
 * @date 2024/3/3 15:12:27
 */
public class LoginServlet extends HttpServlet{

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        Map<String, String> params = request.getParams();
        String acc = params.get("userid");
        String pwd = params.get("password");
        if (acc.equals("123")&& pwd.equals("123")){
            response.write(200, "登录成功");
        }


    }
}

package com.wjcoding.Servlet;

import com.wjcoding.http.HttpRequest;
import com.wjcoding.http.HttpResponse;

/**
 * @author Cynicism
 * @version 1.0
 * @project JavaWeb
 * @description 业务处理
 * @date 2024/3/3 15:06:53
 */
public abstract class HttpServlet {

    public void doService(HttpRequest request,HttpResponse response){
        if (request.getMethod().equals("GET")){
            doGet(request,response);
        } else {
            doPost(request,response);
        }
    }

    public void doGet(HttpRequest request, HttpResponse response){
        response.write(200,"get ok");
    }

    public void doPost(HttpRequest request, HttpResponse response){
        response.write(200,"post ok");
    }

}

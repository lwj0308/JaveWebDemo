package servlet;

import net_utils.HttpRequest;
import net_utils.HttpResponse;
import utils.DatabaseOperations;

public abstract class HttpServlet {
    protected DatabaseOperations databaseOperations = DatabaseOperations.getInstance();
    public void doGet(HttpRequest request, HttpResponse response){
        response.write(String.valueOf(200),"get ok");

    }

    public void doPost(HttpRequest request,HttpResponse response){
        response.write(String.valueOf(200),"post ok");
    }

    public void doService(HttpRequest request,HttpResponse response){
        //区分调用doGet/Post
        if ("GET".equals(request.getMethod())){
            doGet(request,response);
        } else if ("POST".equals(request.getMethod())){
            doPost(request,response);
        }
    }
}

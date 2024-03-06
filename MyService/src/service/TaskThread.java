package service;

import lombok.extern.java.Log;
import net_utils.HttpRequest;
import net_utils.HttpResponse;
import servlet.HttpServlet;

import java.io.File;
import java.io.IOException;

/**
 * @author Cynicism
 * @version 1.0
 * @project JavaWeb
 * @description 任务
 * @date 2024/3/6 14:50:23
 */
@Log
public class TaskThread implements Runnable{

    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    public TaskThread(HttpRequest httpRequest, HttpResponse httpResponse) {
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
    }
    @Override
    public void run() {
        String url = httpRequest.getUrl();
        log.info("请求路径：" + url+ "---请求方法：" + httpRequest.getMethod()+"---请求数据：" + httpRequest.getBody());
        String fileName = "MyService/webapps" + url;
        //是不是业务
        HttpServlet httpServlet = Service.servletMap.get(url);
        File file = new File(fileName);
        if (file.exists()) {//文件
            if (file.isFile()) {
                httpResponse.write(file);
            }
        } else if (httpServlet != null) {
            httpServlet.doService(httpRequest, httpResponse);
        } else {
            httpResponse.write("404", "<h1>404<img src='img/404.png' alt=''></img></h1>");
        }

        try {
            httpResponse.getChannel().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

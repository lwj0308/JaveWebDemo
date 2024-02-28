package service;

import lombok.extern.java.Log;
import net_utils.HttpRequest;
import net_utils.HttpResponse;
import net_utils.HttpServlet;
import servlet.LoginServlet;
import servlet.RegisterServlet;
import utils.DatabaseOperations;
import utils.Logutil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Log
public class HandleThread extends Thread {
    private final Socket accept;

    public HandleThread(Socket accept) {
        this.accept = accept;
    }

    @Override
    public void run() {
        try {
            HttpRequest httpRequest = new HttpRequest(accept.getInputStream());
            HttpResponse httpResponse = new HttpResponse(accept.getOutputStream());

            String url = httpRequest.getUrl();
            log.info("请求路径：" + url);
            log.info("请求方法：" + httpRequest.getMethod());
            log.info("请求数据：" + httpRequest.getBody());
            String fileName = "MyService/webapps" + url;
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


            accept.shutdownInput();
            accept.shutdownOutput();
            accept.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

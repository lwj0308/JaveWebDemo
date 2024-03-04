package service;

import lombok.extern.java.Log;
import net_utils.HttpRequest;
import net_utils.HttpResponse;
import servlet.HttpServlet;
import servlet.ServletFactory;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

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
            log.info("请求路径：" + url+ "---请求方法：" + httpRequest.getMethod()+"---请求数据：" + httpRequest.getBody());
            String fileName = "MyService/webapps" + url;
            //是不是业务
            HttpServlet httpServlet = ServletFactory.getInstance().getServlet(url);
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

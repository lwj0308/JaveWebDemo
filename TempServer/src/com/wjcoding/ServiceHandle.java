package com.wjcoding;

import com.wjcoding.Servlet.HttpServlet;
import com.wjcoding.Servlet.ServletFactory;
import com.wjcoding.http.HttpRequest;
import com.wjcoding.http.HttpResponse;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.File;
import java.net.Socket;

@Log
public class ServiceHandle extends Thread {
    private Socket socket;

    public ServiceHandle(Socket socket) {
        this.socket = socket;
    }

    @SneakyThrows
    @Override
    public void run() {

        HttpRequest httpRequest = new HttpRequest(socket.getInputStream());

        HttpResponse httpResponse = new HttpResponse(socket.getOutputStream());

        log.info(httpRequest.getUrl());
        HttpServlet servlet = ServletFactory.getInstance().getServlet(httpRequest.getUrl());
        File file = new File("TempServer/webapps" + httpRequest.getUrl());
        if (file.exists()) {
            httpResponse.write(file);
        } else if (servlet!=null){
           servlet.doService(httpRequest,httpResponse);
        } else {
            httpResponse.write(404,"<h1>404 NOT FOUND!</h1>");
        }

    }
}

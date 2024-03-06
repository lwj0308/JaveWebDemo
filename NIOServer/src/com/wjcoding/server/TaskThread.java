package com.wjcoding.server;

import java.io.File;
import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * @author Cynicism
 * @version 1.0
 * @project JavaWeb
 * @description 任务
 * @date 2024/3/6 09:29:59
 */

public class TaskThread implements Runnable {

    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
    private SocketChannel socketChannel;

    public TaskThread(HttpRequest httpRequest, HttpResponse httpResponse, SocketChannel socketChannel) {
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try {
            String url = httpRequest.getUrl();
            String fileName = "NIOServer/webapps" + url;
            //是不是业务
            File file = new File(fileName);
            if (file.exists()) {//文件
                httpResponse.write(file);
                System.out.println(url);
            } else {
                httpResponse.write("404", "<h1>404<img src='img/404.png' alt=''></img></h1>");
            }

            socketChannel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

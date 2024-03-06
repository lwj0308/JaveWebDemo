package com.wjcoding;

import lombok.extern.java.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Log
public class RequestHandle extends Thread {
    private ServerSocket serverSocket;

    public RequestHandle(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket accept = serverSocket.accept();
                log.info("客户端连接：" + accept.getInetAddress() + ":" + accept.getPort());
                // 创建新线程处理请求
                new ServiceHandle(accept).start();
                // 这里不关闭socket，因为一个socket对应一个浏览器，如果关闭了，浏览器就访问不到了
            } catch (IOException e) {
                return;
            }
        }

    }
}

package com.wjcoding;

import lombok.extern.java.Log;

import java.io.IOException;
import java.net.ServerSocket;

@Log
public class WebService {
    public static void main(String[] args) {
        //网络接入
        try {
            ServerSocket serverSocket = new ServerSocket(500);
            //接收请求
            new RequestHandle(serverSocket).start();
            log.info("服务器启动成功");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

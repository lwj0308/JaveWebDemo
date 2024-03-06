package com.wjcoding.server;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Cynicism
 * @version 1.0
 * @project JavaWeb
 * @description NIO
 * @date 2024/3/4 09:55:14
 */
public class NIOServer {
    public static void main(String[] args) {
        ServerSocketChannel channel = null;
        try {
            //开启通道
            channel = ServerSocketChannel.open();
            //非阻塞
            channel.configureBlocking(false);
            //监听端口
            channel.bind(new java.net.InetSocketAddress(10086));
            //直接accept会阻塞
            //1、工具人
            Selector selector = Selector.open();
            System.out.println("工具人…………");
            //2、接收任务，帮channel查看是否可以Accept
            channel.register(selector, SelectionKey.OP_ACCEPT);
            //查看哪一路有数据（Accept、Read、Write）
           new SelectorThread(selector).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

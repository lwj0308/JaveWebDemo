package com.wjcoding.server;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author Cynicism
 * @version 1.0
 * @project JavaWeb
 * @description 工具人线程
 * @date 2024/3/5 09:26:18
 */
public class SelectorThread extends Thread{
    private Selector selector;

    public SelectorThread(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            while (true) {//线程
                int n = selector.select();//n返回的任务数:有几路可以 arw
                Set<SelectionKey> selectionKeys = selector.selectedKeys();//一次有多路有数据[...A-R-W]，这玩意有缓存
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
//                    if (!next.isValid()){
//                       continue;
//                    }
                    iterator.remove();//移除当前的key，防止重复操作
                    if (next.isAcceptable()) {
                        //获取通道
                        ServerSocketChannel channel1 = (ServerSocketChannel) next.channel();
                        System.out.println("通道可以Accept了");
                        SocketChannel socketChannel = channel1.accept();
                        System.out.println("通道Accept成功");
                        //再注册，让工具人帮这个通道查看是否可以读
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);

                    } else if (next.isReadable()) {
                        System.out.println("isReadable");
                        SocketChannel socketChannel = (SocketChannel) next.channel();
                        if (!socketChannel.isConnected()) continue;//java.nio.channels.ClosedChannelException
                        HttpRequest httpRequest = new HttpRequest(socketChannel);
                        HttpResponse httpResponse = new HttpResponse(socketChannel);

                        TaskThread taskThread = new TaskThread(httpRequest, httpResponse, socketChannel);
                        ThreadPoolManager.getInstance().execute(taskThread);
                    } else if (next.isWritable()) {

                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

package service;

import lombok.extern.java.Log;
import net_utils.HttpRequest;
import net_utils.HttpResponse;
import servlet.HttpServlet;
import utils.Logutil;

import java.io.File;
import java.io.IOException;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

@Log
public class LinkThread extends Thread {
    private final Selector selector;

    public LinkThread(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {

        while (true) {
            try {
                selector.select();//轮询
                Set<SelectionKey> selectionKeys = selector.selectedKeys();//获取就绪的IO事件
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    iterator.remove();//移除当前的key，防止重复操作
                    if (next.isAcceptable()){
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) next.channel();
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }else if (next.isReadable()){
                        SocketChannel channel = (SocketChannel) next.channel();//拿到socket
                        if (!channel.isConnected()) return;
                        HttpRequest httpRequest = new HttpRequest(channel);
                        HttpResponse httpResponse = new HttpResponse(channel);
                        ThreadPoolManage.getInstance().execute(new TaskThread(httpRequest,httpResponse));
                    }
                }
            } catch (IOException e) {
              Logutil.getInstance().getLogger().warning(e.getMessage());
              break;
            }
        }
    }
}

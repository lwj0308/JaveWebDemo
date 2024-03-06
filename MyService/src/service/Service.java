package service;

import annotation.WebServlet;
import servlet.*;
import utils.DatabaseOperations;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.HashMap;
import java.util.Map;

public class Service {
   public static Map<String, HttpServlet> servletMap = new HashMap<>();
    public static void main(String[] args) {
        //通过注解反射生成servlet
        String[] classNames = {"servlet.LoginServlet",
                "servlet.RegisterServlet",
                "servlet.TrainNumberServlet"
        };

        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className);
                WebServlet annotation = clazz.getAnnotation(WebServlet.class);
                if (annotation!= null){
                    String url = annotation.value();
                    Constructor<?> constructor = clazz.getConstructors()[0];
                    HttpServlet o = (HttpServlet) constructor.newInstance();
                    servletMap.put(url,o);
                }
            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8888));
            serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
            new LinkThread(selector).start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package service;

import servlet.*;
import utils.DatabaseOperations;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

public class Service {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            new LinkThread(serverSocket).start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package service;

import com.sun.org.apache.bcel.internal.generic.NEW;
import net_utils.HttpRequest;
import net_utils.HttpResponse;
import net_utils.HttpServlet;
import servlet.LoginServlet;
import servlet.RegisterServlet;
import servlet.TrainNumberServlet;
import utils.DatabaseOperations;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Service {
    public static Map<String, HttpServlet> servletMap = new HashMap<>();
    public static DatabaseOperations databaseOperations = new DatabaseOperations();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            servletMap.put("/loginIndex", new LoginServlet(databaseOperations));
            servletMap.put("/registerIndex", new RegisterServlet(databaseOperations));
            servletMap.put("/trainquery", new TrainNumberServlet(databaseOperations));
            new LinkThread(serverSocket).start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

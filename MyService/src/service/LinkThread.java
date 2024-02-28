package service;

import net_utils.HttpRequest;
import net_utils.HttpResponse;
import utils.DatabaseOperations;
import utils.Logutil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class LinkThread extends Thread{
    private final ServerSocket serverSocket;

    public LinkThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {

        while (true) {
            try {
                Socket accept = serverSocket.accept();
                new HandleThread(accept).start();
            } catch (IOException e) {
              Logutil.getInstance().getLogger().warning(e.getMessage());
              break;
            }
        }
    }
}

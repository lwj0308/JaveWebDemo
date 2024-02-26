package service;

import net_utils.HttpRequest;
import net_utils.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Service {

    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(8888);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new Thread(() -> {
            while (true) {
                try {
                    Socket accept = serverSocket.accept();

                    new Thread(() -> {

                        try {
                            InputStream inputStream = accept.getInputStream();
                            OutputStream outputStream = accept.getOutputStream();
                            HttpRequest httpRequest = new HttpRequest(inputStream);
                            HttpResponse httpResponse = new HttpResponse(outputStream);

                            httpRequest.receive();
                            System.out.println(httpRequest);
                            String url = httpRequest.getUrl();
                            String fileName = "MyService/webapps" + url;

                            File file = new File(fileName);
                            if (file.exists()){//文件
                                System.out.println("存在："+fileName);
                                if (file.isFile()) {
                                    System.out.println("文件："+fileName);
                                    httpResponse.write(file);
                                }
                            } else if ("/loginIndex".equals(url)){//业务
                                Map<String, String> params = httpRequest.getParams();
                                String acc = params.get("acc");
                                String pwd = params.get("pwd");
                                System.out.println("acc:"+acc+",pwd:"+pwd);
                                if ("admin".equals(acc) && "123456".equals(pwd)){
                                    httpResponse.write("200","<h1><登录成功</h1>");
                                } else {
                                    httpResponse.write("200","<h1><登录失败</h1>");
                                }

                            }
                            else {
                                System.out.println("不存在："+fileName);
                                httpResponse.write("404","<h1>404<img src='img/404.png' alt=''></img></h1>");
                            }


                            accept.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    }).start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();
    }
}

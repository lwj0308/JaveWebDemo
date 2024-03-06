package com.wjcoding.server;

import jdk.nashorn.internal.ir.IfNode;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cynicism
 * @version 1.0
 * @project JavaWeb
 * @description 请求
 * @date 2024/3/5 09:33:38
 */
public class HttpRequest {
    private String method;//请求方法
    private String url;//请求地址

    private String protocol;//协议版本

    private final Map<String, String> headers = new HashMap<>();//请求头
    private final Map<String, String> params = new HashMap<>();//请求参数

    private String body;//内容
    private SocketChannel socketChannel;

    public HttpRequest(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        receive();
    }

    public void receive() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int len = 0;
        try {
            len = socketChannel.read(byteBuffer);
            if (len < 1) return;
            String requestStr = new String(byteBuffer.array(), 0,len,StandardCharsets.UTF_8);
            //请求行
            String[] split = requestStr.split("\r\n\r\n");//分成两部分
            //
            parseOne(split[0]);
            if (split.length > 1) {
                this.body = split[1];
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private void parseOne(String s) {
        String[] split = s.split("\r\n");

        for (int i = 0; i < split.length; i++) {
            if (i == 0) {
                //请求行
                String line = split[0];
                String[] line1 = line.split(" ");
                if (line1.length != 3) return;
                this.method = line1[0];
                this.url = line1[1];

                if (this.url.contains("?")) {
                    String[] split1 = url.split("\\?");
                    this.url = split1[0];
                    //get的参数 k=v&k=v
                    String[] split2 = split1[1].split("&");
                    for (String kv : split2) {
                        String[] split3 = kv.split("=");
                        this.headers.put(split3[0], split3[1]);
                    }
                }
                this.protocol = line1[2];
            } else {
                String header = split[i];
                String[] headers = header.split(": ");
                this.headers.put(headers[0], headers[1]);
            }
        }

        System.out.println("请求方法：" + method + "\n请求URL：" + url + "\n请求协议：" + protocol + "\n请求头：" + headers.toString());

    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }
}

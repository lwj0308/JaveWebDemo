package net_utils;

import lombok.Getter;
import lombok.ToString;
import utils.Logutil;

import java.io.*;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class HttpRequest {
    private String method;//请求方法
    private String url;//请求地址

    private String protocol;//协议版本

    private final Map<String, String> headers = new HashMap<>();//请求头
    private final Map<String, String> params = new HashMap<>();//请求参数

    private String body;//内容

    private final SocketChannel channel;

    public HttpRequest(SocketChannel channel) {
        this.channel = channel;
        receive();
    }

    /**
     * 接受并解析
     */
    public void receive() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int len;

        try {
            len = channel.read(byteBuffer);
            if (len<1) return;
            String requestStr = new String(byteBuffer.array(), 0, len, StandardCharsets.UTF_8);
            //请求行
            String[] split = requestStr.split("\r\n\r\n");//分成两部分
            parseUrl(split[0]);
            //body
            if (split.length > 1) {
                this.body = split[1];
                parseBody();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseBody() {
        if (body == null) {
            return;
        }
        try {
            this.body = URLDecoder.decode(this.body, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        //body kv
        String[] strings = body.split("&");

        for (String string : strings) {
            String[] split = string.split("=");
            if (split.length < 2) continue;
            params.put(split[0], split[1]);
        }
    }

    private void parseUrl(String s) {
        String[] split = s.split("\r\n");

        for (int i = 0; i < split.length; i++) {
            if (i==0){
                //请求行
                String line;
                try {
                    line = URLDecoder.decode(split[0], "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                String[] line1 = line.split(" ");
                if (line1.length!=3) return;
                this.method = line1[0];
                this.url = line1[1];

                if (this.url.contains("?")){//判断url是否带参数
                    String[] split1 = url.split("\\?");
                    this.url = split1[0];
                    //get的参数 k=v&k=v
                    String[] split2 = split1[1].split("&");
                    for (String kv : split2) {
                        String[] split3 = kv.split("=");
                        this.params.put(split3[0], split3[1]);
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


}

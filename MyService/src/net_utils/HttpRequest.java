package net_utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpRequest {
    private String method;//请求方法
    private String url;//请求地址

    private String protocol;//协议版本

    private final Map<String, String> headers = new HashMap<>();//请求头
    private final Map<String, String> params = new HashMap<>();//请求参数

    private String body;//内容

    private final InputStream inputStream;

    public HttpRequest(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * 接受并解析
     */
    public void receive() {
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        try {
            //请求行
            String s = bf.readLine();
            if (s == null) return;
            String[] split = s.split(" ");
            if (split.length != 3) {
                throw new RuntimeException("请求行格式错误");
            }
            this.method = split[0];
            this.url = split[1];
            //解析url
            paramUrl();

            this.protocol = split[2];
            //接受头部
            while (true) {
                s = bf.readLine();
                if (s == null || s.isEmpty()) {
                    break;//结束
                }
                split = s.split(": ");
                if (split.length != 2) {
                    throw new RuntimeException("请求头格式错误");
                } else {
                    headers.put(split[0], split[1]);
                }
            }

            //body
            if (headers.containsKey("Content-Length")) {
                String lengthStr = headers.get("Content-Length");
                Optional.ofNullable(lengthStr).ifPresent(it -> {
                    if (it.isEmpty() && Integer.parseInt(it) > 0) {
                        try {
                            this.body = bf.readLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析参数
     */
    private void paramUrl() {
        //解析url
        https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_10176833490733708600%22%7D&n_type=-1&p_from=-1
        if (this.url.contains("?")) {
            String[] strings = this.url.split("\\?");

            if (strings.length < 2) return;

            this.url = strings[0];

            //解析参数 context=%7B%22nid%22%3A%22news_10176833490733708600%22%7D&n_type=-1&p_from=-1
            strings = strings[1].split("&");

            for (String string : strings) {
                String[] split = string.split("=");
                if (split.length < 2) continue ;
                params.put(split[0],split[1]);
            }

        }


    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getProtocol() {
        return protocol;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", protocol='" + protocol + '\'' +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                ", inputStream=" + inputStream +
                '}';
    }
}

package net_utils;

import utils.Logutil;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
        receive();
        paramUrl();
    }

    /**
     * 接受并解析
     */
    public void receive() {
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        try {
            //请求行
            String s = bf.readLine();

            if (s == null) return;//没有请求行
            //解析请求行
            String[] split = s.split(" ");
            if (split.length != 3) {
                throw new RuntimeException("请求行格式错误");
            }
            this.method = split[0];
            this.url = split[1];
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
            String lengthStr = headers.get("Content-Length");
            if ( lengthStr!=null&& !lengthStr.isEmpty() ) {
                int length = Integer.parseInt(lengthStr);
                if (length > 0) {
                    try {
                        Logutil.getInstance().getLogger().warning("body------:"+ length);
                        char[] bytes = new char[length];
                        StringBuilder builder = new StringBuilder();
                        int len;
                        Logutil.getInstance().getLogger().warning("解析前------:"+ this.url);
                        while ((len = bf.read(bytes)) != -1){
                            Logutil.getInstance().getLogger().warning("解析中------:"+ len);
                            builder.append(bytes, 0, len);
                        }
                        len = bf.read(bytes);
                        String string = new String(bytes, 0, len);
                        Logutil.getInstance().getLogger().warning("body------:"+ string);
//                        builder.append(string);
                        this.body = builder.toString();

                        Logutil.getInstance().getLogger().warning("解析过后------:"+ this.url);
                        Logutil.getInstance().getLogger().warning("解析过后body------:"+ this.body);
                    } catch (IOException e) {
                       Logutil.getInstance().getLogger().warning(e.getMessage());
                    }
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析参数
     */
    private void paramUrl() {
        Logutil.getInstance().getLogger().warning("paramUrl------:"+ this.url);
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
                if (split.length < 2) continue;
                params.put(split[0], split[1]);
            }

        }

        if (body == null) {
            return;
        }
        //body kv
        String[] strings = body.split("&");

        for (String string : strings) {
            String[] split = string.split("=");
            if (split.length < 2) continue;
            params.put(split[0], split[1]);
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

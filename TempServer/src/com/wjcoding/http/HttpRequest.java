package com.wjcoding.http;

import lombok.Cleanup;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * HTTP请求
 */
@Log
@Data
public class HttpRequest {
    private String method;
    private String url;
    private String protocol;

    private Map<String, String> headers = new HashMap<>();
    private String body;
    private InputStream is;
    private Map<String,String> params = new HashMap<>();

    public HttpRequest(InputStream is) {
        this.is = is;
        receive();
        parseParams();
    }

    @SneakyThrows
    public void receive() {

        BufferedReader bf = new BufferedReader(new InputStreamReader(is));

        String line = bf.readLine();

        if (line == null || line.isEmpty()) return;

        String[] arr = line.split(" ");
        if (arr.length != 3) {
            throw new IOException("Invalid http request line");
        }
        method = arr[0];
        url = arr[1];
        protocol = arr[2];

        log.info("method = " + method + ", url = " + url + ", protocol = " + protocol);

        //请求头
        while (true) {
            line = bf.readLine();

            if (line == null || line.isEmpty()) break;

            arr = line.split(": ");
            if (arr.length != 2) {
                headers.put(arr[0], "");
            } else {
                headers.put(arr[0], arr[1]);
            }
        }
        //请求体
        //body = bf.lines().collect(Collectors.joining(System.lineSeparator()));
        String contentLength = headers.get("Content-Length");
        int len;
        if (contentLength == null || contentLength.isEmpty()) {
            len = 0;
        } else {
            len = Integer.parseInt(contentLength);
        }

        if (len>0){
            char[] body = new char[len];
            bf.read(body, 0, len);
            this.body = new String(body);
        }

        log.info("headers = " + headers);
        log.info("body = "+ body);

    }

    public void parseParams(){
        String[] split = this.url.split("\\?");
        if (split.length>=2){
            this.url = split[0];

            String[] kvs = split[1].split("&");
            for (String kv : kvs) {
                String[] kav = kv.split("=");
                if (kav.length<2) return;
                params.put(kav[0],kav[1]);
            }

        }



        if (body== null || body.isEmpty()){
            return;
        }

        String[] kvs = body.split("&");
        for (String kv : kvs) {
            String[] kav = kv.split("=");
            if (kav.length<2) return;
            params.put(kav[0],kav[1]);
        }
    }
}

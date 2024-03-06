package com.wjcoding.http;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private String stateLine = "HTTP/1.1 200 OK";

    private Map<String,String> headers = new HashMap<>();
    private String body;

    private OutputStream out;

    public HttpResponse(OutputStream out) {
        this.out = out;

        headers.put("Content-Type","text/html;charset=utf-8");
        headers.put("Content-Length","0");
        headers.put("Server","Lin/1.1");
        headers.put("Date",new Date().toString());
    }

    public void write(File file){
        headers.put("Content-Length", file.length() + "");

        String fileName = file.getName();
        //扩展名
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

        // 设置Content-Type
        switch (fileExtension) {
            case "html":
                headers.put("Content-Type", "text/html;charset=utf-8");
                break;
            case "css":
                headers.put("Content-Type", "text/css;charset=utf-8");
                break;
            case "js":
                headers.put("Content-Type", "application/javascript;charset=utf-8");
                break;
            case "png":
                headers.put("Content-Type", "image/png");
                break;
            case "jpg":
                headers.put("Content-Type", "image/jpeg");
                break;
            default:
                headers.put("Content-Type", "application/octet-stream");
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(stateLine).append("\r\n");

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            stringBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }

        stringBuilder.append("\r\n");

        byte[] bytes = stringBuilder.toString().getBytes();
        try {
            out.write(bytes);
            out.flush();

            //读取文件内容并写入输出流
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes1 = new byte[1024];
            while (inputStream.read(bytes1) != -1) {
                out.write(bytes1);
                out.flush();
            }

            inputStream.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void write(int  code , String data){
        if (code==200){
            stateLine ="HTTP/1.1 200 OK";
        } else if (code==404){
            stateLine ="HTTP/1.1 404 NotFound";
        }

        headers.put("Content-Type","text/html;charset=utf-8");
        if (data==null){
            headers.put("Content-Length","0");
        } else {
            headers.put("Content-Length",data.getBytes().length+"");
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(stateLine).append("\r\n");

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            stringBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }

        stringBuilder.append("\r\n");

        byte[] bytes = stringBuilder.toString().getBytes();
        try {
            out.write(bytes);
            out.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (data == null) return;
        try {
            out.write(data.getBytes());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

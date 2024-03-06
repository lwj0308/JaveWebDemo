package net_utils;

import lombok.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@Data
public class HttpResponse {
    private String stateLine = "HTTP/1.1 200 OK";

    private final Map<String, String> headers = new HashMap<>();

    private String content;

    private final SocketChannel channel;

    public HttpResponse(SocketChannel channel) {
        headers.put("Content-Type", "text/html");
        headers.put("Date", new Date() + "");
        headers.put("Server", "JavaServer/1.1");
        headers.put("Content-Length", "0");
        this.channel = channel;
    }

    /**
     * 输出文件
     *
     * @param file
     */
    public void write(File file) {
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
            case "ico":
                headers.put("Content-Type", "image/x-icon");
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

        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        try {
            channel.write(wrap);

            //读取文件内容并写入输出流
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes1 = new byte[1024];
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            int len;
            while ((len = inputStream.read(bytes1)) != -1) {
                allocate.put(bytes1, 0, len);
                allocate.flip();
                channel.write(allocate);
                allocate.clear();
            }

            inputStream.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 处理业务
     *
     * @param code
     * @param data
     */
    public void write(String code, String data) {

        StringBuilder stringBuilder = new StringBuilder();

        if (Objects.equals(code, "404")) {
            stateLine = "HTTP1/1 404 Not Found";

        } else if (Objects.equals(code, "200")) {
            stateLine = "HTTP/1.1 200 OK";
        }

        headers.put("Content-Type", "text/html;charset=utf-8");//内容类型
        byte[] bytes = data.getBytes();
        headers.put("Content-Length", bytes.length + "");

        //开始拼接
        stringBuilder.append(stateLine).append("\r\n");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            stringBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }

        stringBuilder.append("\r\n");

        try {
            ByteBuffer wrap = ByteBuffer.wrap(stringBuilder.toString().getBytes());
            channel.write(wrap);
            wrap = ByteBuffer.wrap(bytes);
            channel.write(wrap);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //请求方法---url -- 协议版本
    //请求头
    //空行
    //body
}

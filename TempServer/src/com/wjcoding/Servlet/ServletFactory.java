package com.wjcoding.Servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Cynicism
 * @version 1.0
 * @project JavaWeb
 * @description 生产servlet
 * @date 2024/3/3 15:21:48
 */
public class ServletFactory {

    private Map<String, HttpServlet> servletMap = new HashMap<>();


    private ServletFactory() {
        servletMap.put("/loginIndex", new LoginServlet());
    }

    public HttpServlet getServlet(String url) {
        return servletMap.get(url);
    }

    private static class Builder{
        private final static ServletFactory INSTANCE = new ServletFactory();
    }

    public static ServletFactory getInstance(){
        return Builder.INSTANCE;
    }


}

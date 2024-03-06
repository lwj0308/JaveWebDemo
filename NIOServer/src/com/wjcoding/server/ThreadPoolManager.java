package com.wjcoding.server;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Cynicism
 * @version 1.0
 * @project JavaWeb
 * @description 线程池管理者
 * @date 2024/3/6 10:11:16
 */
public class ThreadPoolManager {
   private static ThreadPoolExecutor poolExecutor;
    private ThreadPoolManager() {//配置文件
        poolExecutor = new ThreadPoolExecutor(5,10,
                10, TimeUnit.SECONDS,new ArrayBlockingQueue<>(20),
                Executors.defaultThreadFactory(),new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static ThreadPoolManager getInstance(){
        return Builder.instance;
    }

    public void execute(Runnable runnable){
        poolExecutor.execute(runnable);
    }
    private static class Builder {
        private static final ThreadPoolManager instance = new ThreadPoolManager();
    }
}

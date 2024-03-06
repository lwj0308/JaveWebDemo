package service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Cynicism
 * @version 1.0
 * @project JavaWeb
 * @description
 * @date 2024/3/6 14:47:05
 */
public class ThreadPoolManage {

    private final ThreadPoolExecutor poolExecutor;

    private ThreadPoolManage() {
        poolExecutor = new ThreadPoolExecutor(10, 30, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    }

    public void execute(Runnable task) {
        poolExecutor.execute(task);
    }

    private static class Builder{
        private static final ThreadPoolManage INSTANCE = new ThreadPoolManage();
    }

    public static ThreadPoolManage getInstance() {
        return Builder.INSTANCE;
    }
}

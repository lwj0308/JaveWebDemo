package com.wjcoding.server;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Cynicism
 * @version 1.0
 * @project JavaWeb
 * @description 测试
 * @date 2024/3/5 16:35:40
 */
public class Test {
    /**
     * <p>
     * 使用execute方法执行任务，通过Runnable接口创建线程类(匿名内部类方式创建线程类)
     * （1）定义runnable接口的实现类，并重写该接口的run()方法，
     * （2）创建 Runnable实现类的实例，
     * </P>
     */
    @org.junit.Test
    public void createThreadPool1() {

//        int pcount = Runtime.getRuntime().availableProcessors();
        //最大线程数控制
        int maxthreadNum = 5;
        ExecutorService executor = new ThreadPoolExecutor(2, maxthreadNum, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 5; i++) {
            final int index = i;
            //匿名内部类方式创建
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    //业务处理
                    System.out.println(Thread.currentThread().getName() + " " + index);
                }
            });
        }
    }


    /**
     * <p>
     * 使用execute方法执行任务，通过Runnable接口创建线程类(自定义创建线程类)
     * （1）定义runnable接口的实现类，并重写该接口的run()方法，
     * （2）创建 Runnable实现类的实例，
     * </P>
     */

    @org.junit.Test
    public void createThreadPool2() {

        int pcount = Runtime.getRuntime().availableProcessors();//java.lang.IllegalArgumentException
        //最大线程数控制
        int maxthreadNum = 5;
        ExecutorService executor = new ThreadPoolExecutor(2, maxthreadNum, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 5; i++) {
            final int index = i;
            executor.execute(new RunnableTask(index));
        }
    }

    static class RunnableTask implements Runnable {
        private int i;

        public RunnableTask(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            //业务处理
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }

    /**
     * <p>
     * 使用invokeAll方法批量执行任务，通过Callable接口创建线程类(匿名内部类方式创建线程类)
     * invokeAll的作用是：等待所有的任务执行完成后统一返回。
     * （1）定义runnable接口的实现类，并重写该接口的run()方法，
     * （2）创建 Runnable实现类的实例，
     * </P>
     */
    @org.junit.Test
    public void createThreadPool3() {

        ExecutorService executor = new ThreadPoolExecutor(4, 4, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

        List<Callable<Object>> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tasks.add(() -> {
                System.out.println(Thread.currentThread().getName());
                return null;
            });
        }
        try {
            List<Future<Object>> futureList = executor.invokeAll(tasks);
            // 获取全部并发任务的运行结果
            for (Future f : futureList) {
                // 获取任务的返回值，并输出到控制台
                System.out.println("result：" + f.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // 关闭线程池
        executor.shutdown();
    }

    /**
     * <p>
     * 使用submit方法执行任务，通过Callable接口创建线程类(匿名内部类方式创建线程类)
     * </P>
     */
    @org.junit.Test
    public void createThreadPool4() {
        int pcount = Runtime.getRuntime().availableProcessors();
        System.out.println(pcount);

        ExecutorService executor = new ThreadPoolExecutor(4, 4, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

        List<Future<Callable>> futureList = new ArrayList<>(10);

        for (int i = 0; i < 5; i++) {
            final int index = i;
            Future future = executor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " " + index);
                return index;
            });
            futureList.add(future);
        }
        try {
            // 获取全部并发任务的运行结果
            for (Future f : futureList) {
                // 获取任务的返回值，并输出到控制台
                System.out.println("result：" + f.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // 关闭线程池
        executor.shutdown();
    }

    /**
     * <p>
     * 使用submit方法执行任务，通过Callable接口创建线程类(自定义方式创建线程类)
     * </P>
     */
    @org.junit.Test
    public void createThreadPool5() {

        ExecutorService executor = new ThreadPoolExecutor(4, 4, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

        List<Future<Callable>> futureList = new ArrayList<>(10);

        for (int i = 0; i < 5; i++) {
            final int index = i;
            Future future = executor.submit(new CallableTask1(index));
            futureList.add(future);
        }
        try {
            // 获取全部并发任务的运行结果
            for (Future f : futureList) {
                // 获取任务的返回值，并输出到控制台
                System.out.println("result：" + f.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // 关闭线程池
        executor.shutdown();
    }

    static class CallableTask1 implements Callable<Integer> {
        Integer i;

        public CallableTask1(Integer i) {
            this.i = i;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getName() + " " + i);
            return i;
        }
    }

    @org.junit.Test
    public void test(){
        try {
            Class<?> person = Class.forName("com.wjcoding.server.model.Person");
            Field age = person.getDeclaredField("age");
//            System.out.println(age);
            Class<?>[] parameterTypes = person.getConstructors()[0].getParameterTypes();
            for (Class<?> parameterType : parameterTypes) {
                System.out.println("parameterType："+parameterType);
            }
            TypeVariable<? extends Constructor<?>>[] typeParameters = person.getConstructors()[0].getTypeParameters();
            for (TypeVariable<? extends Constructor<?>> typeParameter : typeParameters) {
                System.out.println("typeParameter："+typeParameter);
            }

            Type[] genericParameterTypes = person.getConstructors()[0].getGenericParameterTypes();
            for (Type genericParameterType : genericParameterTypes) {
                System.out.println("genericParameterType："+genericParameterType);
            }
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

}

package thread_demo;

import java.util.Random;

public class ThreadDemo2 {
    public static int currentValue = 0;
    public static final int MAX_VALUE = 50;
    public static Random random = new Random();

    public static void main(String[] args) {
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            MyThread myThread = new MyThread((i + 1));
            threads[i] = myThread;
            myThread.start();
        }
        try {
            // 等待所有线程完成
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static class MyThread extends Thread {
        private final int i;

        public MyThread(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                while (currentValue < MAX_VALUE) {
                    synchronized (ThreadDemo.class) {
                        if (currentValue < MAX_VALUE) {
                            int add = Math.min(currentValue + i, MAX_VALUE) - currentValue;
                            currentValue += add;
                            System.out.println("线程" + i + " add " + add + " currentValue = " + currentValue);
                        }

                    }

                    Thread.sleep(1000);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

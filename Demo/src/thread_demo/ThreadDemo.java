package thread_demo;

public class ThreadDemo {
    public static int currentValue = 0;
    public static final int MAX_VALUE = 50;

    public static void main(String[] args) {
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            MyThread myThread = new MyThread();
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
        System.out.println("Final value: "+ currentValue);
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            try {

                while (currentValue < MAX_VALUE) {
                    synchronized (ThreadDemo.class) {
                        if (currentValue < MAX_VALUE) {
                            currentValue++;
                            System.out.println("线程" + Thread.currentThread().getName() + " currentValue = " + currentValue);
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


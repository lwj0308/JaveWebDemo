package thread_demo;

public class ThreadDemo3 {
    public static int value = 50;

    public static void main(String[] args) {
        new Thread(new GetValue()).start();
        new Thread(new SetValue()).start();
    }

    static class GetValue implements Runnable {
        public void run() {

            while (true) {
                synchronized (ThreadDemo3.class) {
                    if (value > 0) {
                        System.out.println("我来取水了，当前值为: " + value);
                        value--;
                        System.out.println("取完值为: " + value);
                    }

                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class SetValue implements Runnable {
        public void run() {

            while (value < 50) {
                synchronized (ThreadDemo3.class) {

                    if (value < 50) {
                        System.out.println("我来加水，当前值为: " + value);
                        value++;
                        System.out.println("加完值为: " + value);
                    }

                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }


}

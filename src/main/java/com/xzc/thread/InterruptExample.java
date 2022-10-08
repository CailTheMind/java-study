package com.xzc.thread;

/**
 * @author xzc
 */
public class InterruptExample {


    private static class MyThread1 extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                System.out.println("Thread run");
            } catch (InterruptedException e) {
                System.out.println("11111");
                e.printStackTrace();
            }
        }
    }

    private static class MyThread2 extends Thread {

        @Override
        public void run() {
            while (!interrupted()) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("BBBB");
            }
            System.out.println("Thread end");
        }
    }

    public static void main(String[] args) {
//        Thread thread1 = new MyThread1();
//        thread1.start();
//        thread1.interrupt();

        Thread thread2 = new MyThread2();
        thread2.start();
        thread2.interrupt();
    }

}

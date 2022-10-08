package com.xzc.thread;

/**
 * @author xzc
 */
public class MyRunner implements Runnable{

    @Override
    public void run() {
        System.out.println("111");
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new MyRunner());
        thread.start();
    }
}

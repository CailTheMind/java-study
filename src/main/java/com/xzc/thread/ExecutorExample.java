package com.xzc.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author xzc
 */
public class ExecutorExample {


    private static class Thread1 extends Thread {
        @Override
        public void run() {
            setName("111");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static class Thread2 extends Thread {
        @Override
        public void run() {
            setName("222");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(new Thread1());
//        executorService.execute(new Thread2());

//        Future<?> future = executorService.submit(new Thread1());
//        future.cancel(true);
//        executorService.shutdown();
        executorService.shutdownNow();
    }
}

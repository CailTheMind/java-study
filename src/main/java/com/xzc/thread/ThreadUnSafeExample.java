package com.xzc.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xzc
 */
public class ThreadUnSafeExample {
    private int inc = 0;

    public void add() {
        inc++;
    }

    public int get() {
        return inc;
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadUnSafeExample example = new ThreadUnSafeExample();
        final int threadSize = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < threadSize; i++) {
            executorService.execute(()->{
                example.add();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println(example.get());
    }

}

package com.xzc.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedExample {

    public void func1() {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                System.out.print(" " + i);
            }
        }
    }

    public synchronized void func2() {
        for (int i = 0; i < 10; i++) {
            System.out.print(" " + i);
        }
    }

    public void func3() {
        synchronized (SynchronizedExample.class) {
            for (int i = 0; i < 10; i++) {
                System.out.print(" " + i);
            }
        }
    }

    public synchronized static void func4() {
        for (int i = 0; i < 10; i++) {
            System.out.print(" " + i);
        }
    }


    public static void main(String[] args) {
        SynchronizedExample e1 = new SynchronizedExample();
        SynchronizedExample e2 = new SynchronizedExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
//        executorService.execute(()-> e1.func1());
//        executorService.execute(()-> e1.func1());
//        executorService.execute(()-> e2.func1());

//        executorService.execute(()-> e1.func2());
//        executorService.execute(()-> e2.func2());

//        executorService.execute(()-> e1.func3());
//        executorService.execute(()-> e2.func3());

        executorService.execute(()-> SynchronizedExample.func4());
        executorService.execute(()-> SynchronizedExample.func4());
        executorService.shutdown();
    }
}

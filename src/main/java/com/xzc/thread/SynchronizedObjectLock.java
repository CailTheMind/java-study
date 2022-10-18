package com.xzc.thread;

/**
 * 对象锁
 * synchronized 关键字
 *
 * @author xzc
 */
public class SynchronizedObjectLock implements Runnable {

    static SynchronizedObjectLock instance = new SynchronizedObjectLock();
    static SynchronizedObjectLock instance1 = new SynchronizedObjectLock();

    Object block1 = new Object();
    Object block2 = new Object();

    @Override
    public void run() {
//        synchronized (this) {
//            System.out.println("我是线程" + Thread.currentThread().getName());
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(Thread.currentThread().getName() + "结束");
//        }

//        synchronized (block1) {
//            System.out.println("block1锁,我是线程" + Thread.currentThread().getName());
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("block1锁," + Thread.currentThread().getName() + "结束");
//        }
//        synchronized (block2) {
//            System.out.println("block2锁,我是线程" + Thread.currentThread().getName());
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("block2锁," + Thread.currentThread().getName() + "结束");
//        }

        method();
    }

    /**
     * 方法锁 锁对象默认this
     */
//    public synchronized void method() {
//        System.out.println("我是线程" + Thread.currentThread().getName());
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(Thread.currentThread().getName() + "结束");
//    }

    /**
     * synchronized 在静态方法上，默认锁就是当前类所在的class类，所以无论哪个线程访问它，需要的锁都只有一把
     */
    public static synchronized void method() {
        System.out.println("我是线程" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "结束");
    }


    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance1);

        t1.start();
        t2.start();
    }
}

package com.xzc.thread;

public class FinalReferenceEscapeDemo {

    private final int a;
    private FinalReferenceEscapeDemo referenceDemo;

    public FinalReferenceEscapeDemo() {
        a = 1;
        referenceDemo = this;
    }

    public void writer() {
        new FinalReferenceEscapeDemo();
    }

    public void reader() {
        if (referenceDemo != null) {
            System.out.println(referenceDemo.a);
            int temp = referenceDemo.a;
        }
    }

    @Override
    public String toString() {
        return referenceDemo.a + "";
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            FinalReferenceEscapeDemo demo = new FinalReferenceEscapeDemo();
            Thread t1 = new Thread(() -> demo.writer());
            Thread t2 = new Thread(() -> demo.reader());
            t2.start();
            t1.start();
        }
    }

}

package pres.cocoadel.learning.concurrency.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(1);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " start...");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " end...");
            }, "thread - " + i);
            thread.start();
        }
        Thread.sleep(1000);
    }
}

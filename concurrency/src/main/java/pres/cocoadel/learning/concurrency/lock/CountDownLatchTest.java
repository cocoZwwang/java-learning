package pres.cocoadel.learning.concurrency.lock;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " start...");
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " end...");
            },"thread - " + i);
            thread.start();
        }
        Thread.sleep(1000);
        countDownLatch.countDown();
    }
}

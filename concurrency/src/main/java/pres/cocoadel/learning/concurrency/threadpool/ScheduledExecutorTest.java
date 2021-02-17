package pres.cocoadel.learning.concurrency.threadpool;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorTest {
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    public static void main(String[] args) {
        executor.schedule(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("now time is: " + new Date(System.currentTimeMillis()));

        },1,TimeUnit.SECONDS);
    }
}

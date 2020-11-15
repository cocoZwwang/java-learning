package pres.cocoadel.learning.concurrency.base.join;

import java.util.concurrent.TimeUnit;

public class JoinTest extends Thread{

    private final Object lock;

    public JoinTest(Object lock) {
        this.lock = lock == null ? this : lock;
    }

    public static void main(String[] args) {
        /*会相互竞争锁资源，形成锁状态*/
//        JoinTest joinTest = new JoinTest(new byte[0]);

        /*也会形成相互竞争锁的状态，但是Thread.join方法是通过thread对象的wait方法实现的，而wait方法会释放当前的锁对象，因此不会形成死锁*/
        JoinTest joinTest = new JoinTest(null);
        joinTest.start();

        System.out.println("test thread started");
        try {
            synchronized (joinTest.lock) {
                joinTest.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main thread end");

    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
            synchronized (lock) {
                System.out.println("thread name: " + Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

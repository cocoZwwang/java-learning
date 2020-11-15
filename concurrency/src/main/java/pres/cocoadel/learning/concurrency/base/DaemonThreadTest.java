package pres.cocoadel.learning.concurrency.base;

import java.util.concurrent.TimeUnit;

/**
 * 如果JVM发现目前活跃的线程只有守护线程，那么JVM会直接退出
 * 因此下面例子中可能会出现run方法没有被执行，程序就已经结束了
 */
public class DaemonThreadTest {
    public static void main(String[] args) {
        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello！I am a thread task!");
        };
        Thread thread = new Thread(task);
        thread.setName("test-thread-1");
        //设置为守护线程，如果是false（默认值），那么run方法一定会被执行完，JVM才会关闭。
        thread.setDaemon(true);
        thread.start();
    }
}

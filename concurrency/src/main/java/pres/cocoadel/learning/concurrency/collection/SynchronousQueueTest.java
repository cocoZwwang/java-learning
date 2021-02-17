package pres.cocoadel.learning.concurrency.collection;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueTest {
    public static void main(String[] args) throws InterruptedException {
        final Executor threadPool = Executors.newCachedThreadPool();

        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();
        threadPool.execute(() -> putInteger(synchronousQueue,1));
        threadPool.execute(() -> putInteger(synchronousQueue,2));
        threadPool.execute(() -> putInteger(synchronousQueue,3));
        threadPool.execute(() -> putInteger(synchronousQueue,4));
        threadPool.execute(() -> putInteger(synchronousQueue,5));

        TimeUnit.SECONDS.sleep(1);

        threadPool.execute(() -> takeInteger(synchronousQueue));
        threadPool.execute(() -> takeInteger(synchronousQueue));
        threadPool.execute(() -> takeInteger(synchronousQueue));
        threadPool.execute(() -> takeInteger(synchronousQueue));
        threadPool.execute(() -> takeInteger(synchronousQueue));
    }

    private static void putInteger(SynchronousQueue<Integer> synchronousQueue,int i){
        try {
            System.out.println(String.format("start put %s in queue....",i));
            synchronousQueue.put(i);
            System.out.println(String.format("end put %s in queue....",i));
        } catch (InterruptedException e) {
            throw  new RuntimeException(e);
        }
    }

    private static void takeInteger(SynchronousQueue<Integer> synchronousQueue){
        try {
            int i = 0;
            System.out.println(String.format("start take %s in queue....",(i = synchronousQueue.take())));
            System.out.println(String.format("end take %s in queue....",i));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

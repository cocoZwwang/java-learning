package pres.cocoadel.learning.concurrency.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁 + condition 测试
 */
public class ReadWriteLockAndConditionTest {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    private void doWrite() {
        lock.writeLock().lock();
        try {

        }finally {
            lock.writeLock().unlock();
        }
    }
}

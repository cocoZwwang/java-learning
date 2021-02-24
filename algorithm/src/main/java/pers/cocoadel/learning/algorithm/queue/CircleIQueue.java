package pers.cocoadel.learning.algorithm.queue;

/**
 * 循环队列（环形缓冲器）
 * @param <T>
 */
public interface CircleIQueue<T> extends IQueue<T> {

    T front();

    T rear();

    boolean enQueue(T t);

    boolean deQueue();
}

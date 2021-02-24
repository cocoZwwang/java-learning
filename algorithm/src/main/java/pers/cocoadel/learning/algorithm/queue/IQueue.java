package pers.cocoadel.learning.algorithm.queue;

public interface IQueue<T> {

    void offer(T t);

    T poll();

    T peek();

    boolean isEmpty();

    boolean isFull();

    int size();
}

package pers.cocoadel.learning.algorithm.queue;

/**
 * 循环双端队列
 *
 * @param <T>
 */
public interface CircleDeque<T> {

    boolean insertFront(T value);

    boolean deleteFront();

    boolean insertLast(T value);

    boolean deleteLast();

    T front();

    T rear();

    boolean isEmpty();

    boolean isFull();
}

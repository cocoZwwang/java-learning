package pers.cocoadel.learning.algorithm.stack;

/**
 * 最小栈
 * @param <T>
 */
public interface MinStack<T extends Comparable<T>> extends IStack<T> {

    T getMin();
}

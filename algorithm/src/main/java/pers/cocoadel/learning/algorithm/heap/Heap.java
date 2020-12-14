package pers.cocoadel.learning.algorithm.heap;

import java.util.NoSuchElementException;

public interface Heap<T> {
    /**
     * 获取堆顶元素，没有就抛出异常
     */
    T peek() throws NoSuchElementException;

    /**
     * 删除堆顶元素，并且被删除的元素，没有就抛出异常
     */
    T poll() throws NoSuchElementException;

    /**
     * 删除数组下标为i的元素，并且返回被删除的元素，没有就抛出异常
     */
    T delete(int i) throws NoSuchElementException;

    /**
     * 插入元素，如果堆满了，就抛出异常
     */
    void insert(T t) throws NoSuchElementException;

    /**
     * 堆是否满了
     */
    boolean isFull();

    /**
     * 堆是否为空
     */
    boolean isEmpty();

    /**
     * 目前堆元素个数
     */
    int size();
}

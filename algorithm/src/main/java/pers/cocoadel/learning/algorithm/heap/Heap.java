package pers.cocoadel.learning.algorithm.heap;

public interface Heap<T> {
    /**
     * 获取堆顶元素，没有则返回空。
     */
    T peek();

    /**
     * 删除堆顶元素，并且被删除的元素，没有则返回空。
     */
    T poll();

    /**
     * 删除数组下标为i的元素，并且返回被删除的元素，如果没有则返回空。
     */
    T delete(int i);

    /**
     * 插入元素，如果堆满了，返回 false
     * 如果插入成功则返回 true
     */
    boolean insert(T t);

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

    void printHeap();
}

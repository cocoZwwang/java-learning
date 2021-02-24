package pers.cocoadel.learning.algorithm.stack;

/**
 * 基于数组实现的固定容量的栈
 */
public class ArrayStack implements IStack<Integer> {
    private final int capacity;

    private final int[] array;

    private int rear;

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        array = new int[capacity];
    }

    @Override
    public void push(Integer value) {
        if (isFull()) {
            return;
        }

        array[rear++] = value;
    }

    @Override
    public Integer pop() {
        if (isEmpty()) {
            return null;
        }
        return array[--rear];
    }

    @Override
    public Integer peek() {
        if (isEmpty()) {
            return null;
        }
        return array[rear - 1];
    }

    @Override
    public int size() {
        return rear;
    }

    @Override
    public boolean isEmpty() {
        return rear == 0;
    }

    @Override
    public boolean isFull() {
        return rear == capacity;
    }
}

package pers.cocoadel.learning.algorithm.queue;


import pers.cocoadel.learning.algorithm.heap.Heap;

/**
 * 基于数组的循环双端队列
 */
public class ArrayCircleDeque2 implements CircleDeque<Integer>, CircleIQueue<Integer> {
    private final int capacity;
    private final int[] array;
    private int front;
    private int rear;

    public ArrayCircleDeque2(int capacity) {
        //需要冗余一个标志位
        //标记位不存储数据
        this.capacity = capacity + 1;
        array = new int[this.capacity];
        front = 0;
        rear = 0;
    }

    @Override
    public boolean insertFront(Integer value) {
        if (isFull()) {
            return false;
        }
        int index = incrementIndex(front);
        array[index] = value;
        front = index;
        return true;
    }

    @Override
    public boolean deleteFront() {
        return removeFirst() != null;
    }

    private Integer removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Integer res = array[front];
        front = decrementIndex(front);
        return res;
    }

    @Override
    public boolean insertLast(Integer value) {
        if (isFull()) {
            return false;
        }
        array[rear] = value;
        rear = decrementIndex(rear);
        return true;
    }

    @Override
    public boolean deleteLast() {
        return removeLast() != null;
    }

    private Integer removeLast(){
        if (isEmpty()) {
            return null;
        }
        Integer res = array[rear];
        rear = incrementIndex(rear);
        return res;
    }

    @Override
    public Integer front() {
        if (isEmpty()) {
            return null;
        }
        return array[front];
    }

    @Override
    public Integer rear() {
        if (isEmpty()) {
            return null;
        }

        return array[incrementIndex(rear)];
    }

    @Override
    public boolean enQueue(Integer integer) {
        return insertLast(integer);
    }

    @Override
    public boolean deQueue() {
        return deleteFront();
    }

    @Override
    public void offer(Integer integer) {
        enQueue(integer);
    }

    @Override
    public Integer poll() {
        return removeFirst();
    }

    @Override
    public Integer peek() {
        return front();
    }

    @Override
    public boolean isEmpty() {
        return front == rear;
    }

    @Override
    public boolean isFull() {
        return front == decrementIndex(rear);
    }

    @Override
    public int size() {
        return Math.abs(rear - front);
    }

    private int incrementIndex(int index) {
        return (index + 1) % capacity;
    }

    private int decrementIndex(int index) {
        return (index - 1 + capacity) % capacity;
    }
}

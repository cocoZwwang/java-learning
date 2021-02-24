package pers.cocoadel.learning.algorithm.queue;

public class ArrayQueue implements IQueue<Integer> {
    private int[] array;

    private int capacity;

    private int front;

    private int rear;

    public ArrayQueue(int capacity) {
        this.capacity = capacity;
        array = new int[capacity];
        front = -1;
        rear = -1;
    }

    @Override
    public void offer(Integer integer) {
        if (isFull()) {
            return;
        }
        array[++rear] = integer;
    }

    @Override
    public Integer poll() {
        if (isEmpty()) {
            return null;
        }
        return array[++front];
    }

    @Override
    public Integer peek() {
        if (isEmpty()) {
            return null;
        }
        return array[front + 1];
    }

    @Override
    public boolean isEmpty() {
        return front == rear;
    }

    @Override
    public boolean isFull(){
        return rear == capacity - 1;
    }

    @Override
    public int size() {
        return rear - front;
    }
}

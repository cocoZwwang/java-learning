package pers.cocoadel.learning.algorithm.queue;


public class ArrayCircleQueue implements CircleIQueue<Integer> {

    private final int[] array;
    private final int capacity;
    private int count;
    private int front;

    public ArrayCircleQueue(int capacity) {
        this.capacity = capacity;
        array = new int[capacity];
        front = 0;
        count = 0;
    }

    @Override
    public boolean enQueue(Integer value) {
        if (isFull()) {
            return false;
        }
        int tail = (front + count) % capacity;
        array[tail] = value;
        count++;
        return true;
    }

    @Override
    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        front = (front + 1) % capacity;
        count--;
        return true;
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
        int tail = (front + count - 1) % capacity;
        return array[tail];
    }

    @Override
    public void offer(Integer integer) {
        enQueue(integer);
    }

    @Override
    public Integer poll() {
        if (isEmpty()) {
            return null;
        }
        Integer result = array[front];
        front = (front + 1) % capacity;
        count--;
        return result;
    }

    @Override
    public Integer peek() {
        return front();
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public boolean isFull() {
        return count == capacity;
    }

    @Override
    public int size() {
        return count;
    }
}

package pers.cocoadel.learning.algorithm.queue;

/**
 * 基于数组的循环双端队列的实现
 */
public class ArrayCircleDeque implements CircleDeque<Integer>, CircleIQueue<Integer> {
    //队列容量
    private int capacity;
    private int[] array;
    //当前队列头部下标
    private int front;
    //当前队列尾部的下一个位置的下标
    private int rear;
    //当前队列元素个数
    private int count;

    public ArrayCircleDeque(int capacity) {
        this.capacity = capacity;
        array = new int[capacity];
        front = -1;
        rear = -1;
        count = 0;
    }

    private int index(int index) {
        return index >= 0 ? index % capacity : (index + capacity) % capacity;
    }

    @Override
    public boolean insertFront(Integer value) {
        if(isFull()){
            return false;
        }
        int index = index(front + 1);
        array[index] = value;
        count ++;
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
        int res = array[index(front)];
        front = index(front - 1);
        count--;
        return res;
    }

    @Override
    public boolean insertLast(Integer value) {
        if (isFull()) {
            return false;
        }
        array[index(rear)] = value;
        rear = index(rear - 1);
        count++;
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
        rear = index(rear + 1);
        count--;
        return array[rear];
    }

    @Override
    public Integer front() {
        if (isEmpty()) {
            return null;
        }
        return array[index(front)];
    }

    @Override
    public Integer rear() {
        if (isEmpty()) {
            return null;
        }
        return array[index(rear + 1)];
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

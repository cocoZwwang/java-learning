package pers.cocoadel.learning.algorithm.stack;


import pers.cocoadel.learning.algorithm.queue.ArrayCircleQueue;
import pers.cocoadel.learning.algorithm.queue.IQueue;

/**
 * 利用队列来实现栈
 */
public class MyQueueStack implements IStack<Integer> {
    private final IQueue<Integer> queue;

    public MyQueueStack(int capacity) {
        this.queue = new ArrayCircleQueue(capacity);
    }

    @Override
    public void push(Integer value) {
        queue.offer(value);
    }

    @Override
    public Integer pop() {
        for (int i = 0; i < queue.size() - 1; i++) {
            queue.offer(queue.poll());
        }
        return queue.isEmpty() ? null : queue.poll();
    }

    @Override
    public Integer peek() {
        Integer res = pop();
        if (res != null) {
            queue.offer(res);
        }
        return res;
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

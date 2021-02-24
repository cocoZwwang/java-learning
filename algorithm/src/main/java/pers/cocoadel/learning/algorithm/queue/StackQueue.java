package pers.cocoadel.learning.algorithm.queue;

import pers.cocoadel.learning.algorithm.stack.IStack;
import pers.cocoadel.learning.algorithm.stack.MyArrayStack;


/**
 * 使用栈来实现队列
 */
public class StackQueue implements  IQueue<Integer> {
    private final IStack<Integer> pushStack;

    private final IStack<Integer> popStack;

    public StackQueue() {
        pushStack = new MyArrayStack();
        popStack = new MyArrayStack();
    }

    @Override
    public void offer(Integer integer) {
        pushStack.push(integer);
    }

    private void moveElement() {
        if (popStack.size() > 0) {
            return;
        }
        while (pushStack.size() > 0) {
            popStack.push(pushStack.pop());
        }
    }

    @Override
    public Integer poll() {
        moveElement();
        return popStack.size() > 0 ? popStack.pop() : null;
    }

    @Override
    public Integer peek() {
        moveElement();
        return popStack.size() > 0 ? popStack.peek() : null;
    }

    @Override
    public boolean isEmpty() {
        moveElement();
        return popStack.isEmpty();
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public int size() {
        moveElement();
        return popStack.size();
    }
}

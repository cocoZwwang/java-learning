package pers.cocoadel.learning.algorithm.stack;


/**
 * 最小栈
 */
public class MyMinStack implements MinStack<Integer> {
    private final IStack<Integer> minStack = new MyArrayStack();

    private final IStack<Integer> stack = new MyQueueStack(1000);

    public MyMinStack(){
        minStack.push(Integer.MAX_VALUE);
    }

    @Override
    public void push(Integer integer) {
        stack.push(integer);
        minStack.push(Math.min(minStack.peek(), integer));
    }

    @Override
    public Integer pop() {
        if (stack.isEmpty()) {
            return null;
        }
        minStack.pop();
        return stack.pop();
    }

    @Override
    public Integer peek() {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.peek();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Integer getMin() {
        return minStack.peek();
    }
}

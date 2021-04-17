package pers.cocoadel.learning.algorithm.stack;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeSet;

import static com.google.common.base.Preconditions.checkArgument;

public class MinStackTest {
    public static void main(String[] args) {
        MinStack<Integer> stack = new MyMinStack();

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        checkArgument(stack.pop() == 5);
        checkArgument(stack.pop() == 4);
        checkArgument(stack.pop() == 3);
        checkArgument(stack.getMin() == 1);

        stack.push(-1);
        stack.push(7);

        checkArgument(stack.peek() == 7);
        checkArgument(stack.pop() == 7);
        checkArgument(stack.getMin() == -1);
        checkArgument(stack.pop() == -1);
        checkArgument(stack.pop() == 2);
        checkArgument(stack.pop() == 1);

        System.out.println("测试通过！！！！");
        System.currentTimeMillis();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

package pers.cocoadel.learning.algorithm.stack;

/**
 * 基于链表实现的栈
 */
public class LinkedStack implements IStack<Integer> {

    private int size;

    private final Node head;

    private final Node tail;

    public LinkedStack() {
        head = new Node(-1);
        tail = new Node(-1);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    @Override
    public void push(Integer value) {
        Node node = new Node(value);
        Node prev = tail.prev;
        prev.next = node;
        node.prev = prev;
        node.next = tail;
        tail.prev = node;
        size++;
    }

    @Override
    public Integer pop() {
        if (tail.prev == head) {
            return null;
        }
        Node node = tail.prev;
        Node prev = node.prev;
        prev.next = tail;
        tail.prev = prev;
        size --;
        return node.value;
    }

    @Override
    public Integer peek() {
        if (tail.prev == head) {
            return null;
        }
        return tail.prev.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    private static class Node{

        Node(int value) {
            this.value = value;
        }

        int value;

        Node prev;

        Node next;
    }
}

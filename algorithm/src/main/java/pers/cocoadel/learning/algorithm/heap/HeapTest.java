package pers.cocoadel.learning.algorithm.heap;


import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

public class HeapTest {
    public static void main(String[] args) {
//        Heap<Integer> maxHeap = new BinaryHeap<>(10, (n1, n2) -> n2 - n1);
        // maxHeap 默认是大顶堆
//        Heap<Integer> maxHeap = new ArrayBinaryHeap<>(10, (n1, n2) -> n2 - n1);
        Heap<Integer> maxHeap = new EnlargeableBinaryHeap<>();
        // PriorityQueue 默认是小顶堆
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        pushData(maxHeap, priorityQueue);
        maxHeap.printHeap();


        while (priorityQueue.size() > 0) {
            int v1 = maxHeap.poll();
            int v2 = priorityQueue.poll();

            System.out.println("v1: " + v1 + " v2:" + v2);
            checkArgument(v1 == v2);
        }

        System.out.println();

        try {
            checkArgument(maxHeap.peek() == null);
            checkArgument(priorityQueue.peek() == null);
            checkArgument(maxHeap.poll() == null);
            checkArgument(priorityQueue.poll() == null);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        try {
            checkArgument(maxHeap.poll() == null);
            checkArgument(priorityQueue.poll() == null);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }


        System.out.println();
        pushData(maxHeap, priorityQueue);
        maxHeap.delete(6);
        maxHeap.delete(1);
        for (int v = 1; v <= 8; v++) {
            int value = maxHeap.poll();
            System.out.print(value + "  ");
        }

        System.out.println("\n============ 测试通过 ====================");
    }

    private static void pushData(Heap<Integer> maxHeap, PriorityQueue<Integer> priorityQueue) {
        maxHeap.insert(10);
        maxHeap.insert(4);
        maxHeap.insert(9);
        maxHeap.insert(1);
        maxHeap.insert(7);
        maxHeap.insert(5);
        maxHeap.insert(3);
        maxHeap.insert(2);
        maxHeap.insert(6);
        maxHeap.insert(8);

        priorityQueue.add(10);
        priorityQueue.add(4);
        priorityQueue.add(9);
        priorityQueue.add(1);
        priorityQueue.add(7);
        priorityQueue.add(5);
        priorityQueue.add(3);
        priorityQueue.add(2);
        priorityQueue.add(6);
        priorityQueue.add(8);
    }
}

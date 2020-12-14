package pers.cocoadel.learning.algorithm.heap;

public class HeapTest {
    public static void main(String[] args) {
        BinaryHeap maxHeap = new BinaryHeap(10);
        try {
            maxHeap.delete(0);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
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
        try {
            maxHeap.insert(11);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }


        maxHeap.printHeap();
        maxHeap.delete(0);
        maxHeap.printHeap();
        maxHeap.delete(0);
        maxHeap.printHeap();
    }
}

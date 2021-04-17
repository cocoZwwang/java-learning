package pers.cocoadel.learning.algorithm.sort.list;

import pers.cocoadel.learning.algorithm.sort.QuickSort;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ListSortTest {

    public static void main(String[] args) {
        Random random = new Random();
        Node root = new Node(10);
        Node curr = root;
        for(int i = 0; i < 50; i++){
            curr.next = getNode(random.nextInt(100));
            curr = curr.next;
        }

        ListSort quickSort = new QuickListSort();
        Node node = quickSort.sort(root);
        print(node);
    }

    private static void print(Node node) {
        List<Integer> list = new LinkedList<>();
        while (node != null) {
            list.add((Integer) node.val);
            node = node.next;
        }
        System.out.println(list);
    }

    private static Node getNode(int i) {
        Node node = new Node(i);
        return node;
    }
}

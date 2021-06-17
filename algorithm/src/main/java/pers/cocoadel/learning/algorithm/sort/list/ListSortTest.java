package pers.cocoadel.learning.algorithm.sort.list;

import pers.cocoadel.learning.algorithm.sort.QuickSort;

import java.util.*;

public class ListSortTest {

    public static void main(String[] args) {
        Random random = new Random();
        //一共 50 个节点
        Node root = new Node(10);
        Node curr = root;
        for(int i = 0; i < 49; i++){
            curr.next = getNode(random.nextInt(100));
            curr = curr.next;
        }
        List<Integer> list = toList(root);
//        ListSort quickSort = new QuickListSort();
//        ListSort quickSort = new LinkedQuickSort();
        ListSort quickSort = new NodeQuickSort();
        Node root2 = clone(root);
        Node node = quickSort.sort(root);
        List<Integer> sortedList = toList(node);
        System.out.println(sortedList);

        list.sort(Integer::compareTo);
        System.out.println(list);

//        Node KNode = quickSort.findKthNode(root2,51);
//        System.out.println(51 + " - Node is null: "  + (KNode == null));
        int k = random.nextInt(50);
        Node KNode = quickSort.findKthNode(root2,k);
        System.out.println(k + " - Node.val : "  + KNode.val + " == " + list.get(k - 1));
    }

    private static List<Integer> toList(Node node){
        List<Integer> list = new LinkedList<>();
        while (node != null) {
            list.add((Integer) node.val);
            node = node.next;
        }
        return list;
    }

    private static Node getNode(int i) {
        Node node = new Node(i);
        return node;
    }

    private static Node clone(Node root){
        Node head = new Node(null);
        Node curr = head;
        while (root != null){
            Node node =  new Node(root.val);
            curr.next = node;
            curr = node;

            root = root.next;
        }
        return  head.next;
    }
}

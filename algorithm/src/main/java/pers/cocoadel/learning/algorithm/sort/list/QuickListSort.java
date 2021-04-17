package pers.cocoadel.learning.algorithm.sort.list;


public class QuickListSort implements ListSort {

    @Override
    public Node sort(Node root) {
        if (root == null || root.next == null) {
            return root;
        }
        Node curr = root;
        while (curr.next != null) {
            curr = curr.next;
        }
        Node dummyHeadNode = new Node(null);
        dummyHeadNode.next = root;
        quickSort(dummyHeadNode, curr);
        return dummyHeadNode.next;
    }

    private Node quickSort(Node dummyHead, Node tail) {
        Node root = dummyHead.next;
        if (root == tail || root == null) {
            return tail;
        }

        Node pivot = pivotNode(root);
        Node pivotTail = pivot;
        Node largeHead = null;
        Node largeTail = null;
        Node smallHead = null;
        Node smallTail = null;
        Node curr = pivot.next;
        pivot.next = null;

        while (curr != null) {
            Node next = curr.next;
            int c = CompareTo(curr, pivot);
            if (c < 0) {
                smallHead = smallHead == null ? curr : smallHead;
                if (smallTail != null) {
                    smallTail.next = curr;
                    curr.next = null;
                }
                smallTail = curr;
            } else if (c > 0) {
                largeHead = largeHead == null ? curr : largeHead;
                if (largeTail != null) {
                    largeTail.next = curr;
                    curr.next = null;
                }
                largeTail = curr;
            } else {
                pivotTail.next = curr;
                pivotTail = curr;
                curr.next = null;
            }
            curr = next;
        }

        dummyHead.next = smallHead;
        smallTail = quickSort(dummyHead, smallTail);
        smallHead = dummyHead.next;

        dummyHead.next = largeHead;
        largeTail = quickSort(dummyHead, largeTail);
        largeHead = dummyHead.next;

        dummyHead.next = smallHead == null ? pivot : smallHead;
        if (smallTail != null) {
            smallTail.next = pivot;
        }
        pivotTail.next = largeHead;
        return largeTail != null ? largeTail : pivotTail;
    }

    private Node pivotNode(Node root) {
        return root;
    }

    private int CompareTo(Node n1, Node n2) {
        Comparable comparable = (Comparable) n1.val;
        return comparable.compareTo(n2.val);
    }
}

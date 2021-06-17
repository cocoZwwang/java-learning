package pers.cocoadel.learning.algorithm.sort.list;


/**
 * 单向链表快排，只能使用 next 指针，不能使用 pre 指针，不使用额外的存储
 */
public class NodeQuickSort implements ListSort {
    @Override
    public Node sort(Node root) {
        Node head = new Node(null);
        head.next = root;
        quickSort(head);
        return head.next;
    }

    private Node quickSort(Node head) {
        if (head.next != null) {
            //枢纽元
            Node pivot = head.next;
            //分区虚拟节点
            Node smaller = new Node(null);
            Node larger = new Node(null);

            Node curr = pivot.next;
            pivot.next = null;

            //分区尾部节点
            Node lastPivot = pivot;

            //遍历比较
            while (curr != null) {
                Node next = curr.next;
                int c = compareTo(curr, pivot);
                if (c < 0) {
                    append(smaller, curr);
                } else if (c == 0) {
                    append(lastPivot, curr);
                    lastPivot = curr;
                } else {
                    append(larger, curr);
                }
                curr = next;
            }
            //递归
            Node lsn = quickSort(smaller);
            Node lgn = quickSort(larger);
            //合并
            if (lsn != null) {
                head.next = smaller.next;
                lsn.next = pivot;
            } else {
                head.next = pivot;
            }
            smaller.next = null;
            lastPivot.next = larger.next;
            return lgn == null ? lastPivot : lgn;
        }
        return null;
    }

    private void append(Node pre, Node node) {
        node.next = pre.next;
        pre.next = node;
    }

    private int compareTo(Node n1, Node n2) {
        return ((Comparable) n1.val).compareTo(n2.val);
    }

    @Override
    public Node findKthNode(Node root, int k) {
        Node head = new Node(null);
        head.next = root;
        return findKtNode(head,0,k);
    }

    private Node findKtNode(Node head, int len, int k) {
        if (head.next == null) {
            return null;
        }
        Node pivot = head.next;
        Node curr = pivot.next;
        pivot.next = null;

        Node smaller = new Node(null);
        Node larger = new Node(null);

        int smallSize = 0;
        int pivotSize = 1;

        while (curr != null) {
            Node next = curr.next;
            int c = compareTo(curr, pivot);
            if (c == 0) {
                append(pivot, curr);
                pivotSize++;
            } else if (c < 0) {
                append(smaller, curr);
                smallSize++;
            } else {
                append(larger, curr);
            }
            curr = next;
        }
        if (smallSize + len > k - 1) {
            return findKtNode(smaller,len, k);
        }else if(len + smallSize + pivotSize <= k - 1){
            return findKtNode(larger,len + smallSize + pivotSize,k);
        }
        return pivot;
    }

}

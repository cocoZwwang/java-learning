package pers.cocoadel.learning.algorithm.sort.list;

/**
 * 双向链表的节点
 */
public class LinkedQuickSort implements ListSort{
    @Override
    public Node sort(Node root) {
        Chain chain = new Chain(root);
        quickSort(chain);
        chain.tail.pre.next = null;
        return chain.first();
    }

    private void quickSort(Chain chain) {
        if (chain.size > 1) {
            Chain smallerChain = new Chain();
            Chain sameChain = new Chain();
            Chain lagerChain = new Chain();

            Node pivot = chain.first();
            while(chain.size > 0){
                Node curr = chain.removeFirst();
                int c = compareTo(curr, pivot);
                if (c == 0) {
                    sameChain.add(curr);
                } else if (c < 0) {
                    smallerChain.add(curr);
                }else{
                    lagerChain.add(curr);
                }
            }
            quickSort(smallerChain);
            quickSort(lagerChain);

            chain.clear();
            chain.addChain(smallerChain);
            chain.addChain(sameChain);
            chain.addChain(lagerChain);
        }
    }

    private int compareTo(Node n1, Node n2){
        return ((Comparable) n1.val).compareTo(n2.val);
    }

    static  class  Chain {
        private final Node head;
        private final Node tail;
        private int size = 0;

        Chain() {
            head = new Node(null);
            tail = new Node(null);
            head.next = tail;
            tail.pre = head;
        }

        Chain(Node node){
            this();
            addChain(node);
        }

        private void add(Node node) {
            node.pre = tail.pre;
            node.next = tail;
            tail.pre.next = node;
            tail.pre = node;
            size++;
        }

        private void addChain(Chain chain) {
            if (chain.size > 0) {
                Node root = chain.first();
                chain.tail.pre.next = null;
                addChain(root);
            }
        }

        private void addChain(Node root){
            Node curr = root;
            size = root == null ? 0 : 1;
            while (curr != null && curr.next != null) {
                curr = curr.next;
                size++;
            }
            if(size > 0){
                root.pre = tail.pre;
                tail.pre.next = root;
                curr.next = tail;
                tail.pre = curr;
            }
        }

        private Node first() {
            return head.next;
        }

        private Node removeFirst(){
            if(size > 0){
                Node node = head.next;
                head.next = node.next;
                node.next.pre = head;
                node.next = null;
                node.pre = null;
                size--;
                return node;
            }
            return null;
        }

        private void clear(){
            head.next = tail;
            tail.pre = head;
            size = 0;
        }
    }
}

package pers.cocoadel.learning.algorithm.sort.list;

public interface ListSort {

    Node sort(Node root);

    default Node findKthNode(Node root, int k){
        return null;
    }
}

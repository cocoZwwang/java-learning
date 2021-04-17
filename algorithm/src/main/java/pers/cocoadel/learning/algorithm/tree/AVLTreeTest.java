package pers.cocoadel.learning.algorithm.tree;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

public class AVLTreeTest {
    public static void main(String[] args) {
        BinarySearchTree<Integer,Integer> tree = new AVLTree<>();
        Random random = new Random();
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < 10000; i++) {
            int v = random.nextInt(10000);
            tree.put(v, v);
            set.add(v);
        }

        SortedSet<Integer> sortedSet = set.tailSet(0);;
        Iterator<Integer> iterator = tree.iterator();

        for (Integer i : sortedSet) {
            int v1 = i;
            int v2 = iterator.next();
            System.out.println("v1: " + v1 + " v2: " + v2);
            checkArgument(v1 == v2);
        }

        AVLTree<Integer,Integer> avlTree = (AVLTree<Integer, Integer>) tree;
        System.out.println("test avl: " + avlTree.testAVLTree());
    }
}

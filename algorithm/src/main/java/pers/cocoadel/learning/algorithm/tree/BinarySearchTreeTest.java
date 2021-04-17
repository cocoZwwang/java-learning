package pers.cocoadel.learning.algorithm.tree;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

public class BinarySearchTreeTest {
    public static void main(String[] args) {
        BinarySearchTree<Integer, Integer> tree = new DefaultBinarySearchTree<>();
        //test isEmpty()
        checkArgument(tree.isEmpty());

        System.out.println(3 / 2 + "    " + (-3 / 2));
        tree.put(2, 2);
        tree.put(1, 1);
        tree.put(4, 4);
        tree.put(3, 3);
        tree.put(7, 7);
        tree.put(5, 5);
        tree.put(6, 6);
        tree.put(8, 8);

        Iterator<Integer> iterator = tree.iterator();
        iterator.forEachRemaining(i -> System.out.print(i + " "));
        System.out.println();

        //test size
        checkArgument(tree.size() == 8);
        //test isEmpty()
        checkArgument(!tree.isEmpty());

        // test get
        checkArgument(tree.get(7) == 7);
        checkArgument(tree.get(1) == 1);
        checkArgument(tree.get(5) == 5);
        checkArgument(tree.get(10) == null);

        //test contains
        checkArgument(tree.contains(1));
        checkArgument(tree.contains(4));
        checkArgument(tree.contains(8));
        checkArgument(!tree.contains(9));

        //test minimum
        checkArgument(tree.minimum() == 1);

        //test maximum
        checkArgument(tree.maximum() == 8);


        tree.put(10, 10);
        //test floor
        checkArgument(tree.floor(9) == 8);

        //test ceil
        checkArgument(tree.ceiling(9) == 10);

        // test rank
        int rank = tree.rank(5);
//        System.out.println("rank: " + rank);
        checkArgument(rank == 5);

        // test select
        int key = tree.select(6);
//        System.out.println("key: " + key);
        checkArgument(key == 6);

        //test delete
        tree.delete(6);
        checkArgument(tree.get(6) == null);
        int size = tree.size();
//        System.out.println("size: " + size);
        checkArgument(size == 8);
        checkArgument(tree.get(1) == 1);
        tree.delete(1);
        checkArgument(tree.get(1) == null);
        checkArgument(tree.size() == 7);
        tree.delete(9);
        checkArgument(tree.size() == 7);
        tree.delete(10);
        checkArgument(tree.size() == 6);

        System.out.println("测试通过");

        testLower(new DefaultBinarySearchTree<>());

        testHigher(new DefaultBinarySearchTree<>());
    }


    private static void testLower(BinarySearchTree<Integer, Integer> binarySearchTree) {
        if (binarySearchTree instanceof DefaultBinarySearchTree) {
            DefaultBinarySearchTree<Integer,Integer> defaultBinarySearchTree =
                    (DefaultBinarySearchTree<Integer, Integer>) binarySearchTree;
            defaultBinarySearchTree.put(2,2);
            defaultBinarySearchTree.put(4,4);
            defaultBinarySearchTree.put(6,6);
            defaultBinarySearchTree.put(8,8);

            Integer key = defaultBinarySearchTree.lower(5);
            checkArgument(key == 4);

            key = defaultBinarySearchTree.lower(8);
            checkArgument(key == 6);

            key = defaultBinarySearchTree.lower(6);
            checkArgument(key == 4);

            key = defaultBinarySearchTree.lower(2);
            checkArgument(key == null);

            System.out.println("testLower 通过");

        }
    }

    private static void testHigher(BinarySearchTree<Integer, Integer> binarySearchTree) {
        if (binarySearchTree instanceof DefaultBinarySearchTree) {
            DefaultBinarySearchTree<Integer,Integer> defaultBinarySearchTree =
                    (DefaultBinarySearchTree<Integer, Integer>) binarySearchTree;
            defaultBinarySearchTree.put(2,2);
            defaultBinarySearchTree.put(4,4);
            defaultBinarySearchTree.put(6,6);
            defaultBinarySearchTree.put(8,8);

            Integer key = defaultBinarySearchTree.higher(5);
            checkArgument(key == 6);

            key = defaultBinarySearchTree.higher(8);
            checkArgument(key == null);

            key = defaultBinarySearchTree.higher(6);
            checkArgument(key == 8);

            key = defaultBinarySearchTree.higher(2);
            checkArgument(key == 4);

            System.out.println("testHigher 通过");

        }
    }

}

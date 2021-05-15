package pers.cocoadel.learning.algorithm.tree;

import java.util.Iterator;

public interface ISet<T> {

    void makeEmpty();

    boolean isEmpty();

    boolean contains(T t);

    T findMin();

    T findMax();

    void insert(T t);

    void remove(T t);

    Iterator<T> iterator();
}

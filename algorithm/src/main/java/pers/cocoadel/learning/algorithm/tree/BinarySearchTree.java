package pers.cocoadel.learning.algorithm.tree;

import java.util.List;

public interface BinarySearchTree<K, T> extends Iterable<K>{
    /**
     * 将「键-值」对存入二叉搜索树中，如果 key 在二叉搜索树中已经存在，则覆盖 value
     */
    void put(K key, T value);

    /**
     * 二叉搜索树是否为空
     */
    boolean isEmpty();

    /**
     * 二叉搜索树中键值对的数量
     */
    int size();

    /**
     * 获取键 key 对应的值（若 key 不存在返回空）
     */
    T get(K key);

    /**
     * 键 key 是否存在于二叉搜索树中
     */
    boolean contains(K key);

    /**
     * 从二叉搜索树中删除 key 以及对应的 value
     */
    void delete(K key);

    /**
     * 二叉搜索树中最小的 key
     */
    K minimum();

    /**
     * 二叉搜索树中最大的 key
     */
    K maximum();

    /**
     * 小于等于 key 的最大 key
     */
    K floor(K key);

    /**
     * 大于等于 key 的最大 key
     */
    K ceiling(K key);

    /**
     * 返回小于 key 的最大的 K
     */
    K lower(K key);

    /**
     * 返回大于 key 的最小的 K
     */
    K higher(K key);

    /**
     * 小于 key 的键的个数
     */
    int rank(K key);

    /**
     * 排名为 k 的 key
     */
    K select(int k);

    List<T> values();
}

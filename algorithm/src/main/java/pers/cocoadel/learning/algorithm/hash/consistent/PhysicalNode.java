package pers.cocoadel.learning.algorithm.hash.consistent;

/**
 * fun hashKey(): String
 * fun put(key: String, value: String): String?
 * fun get(key: String): String?
 * fun size(): Int
 * fun clear()
 * fun remove(key: String): String?
 * 物理节点
 */
public interface PhysicalNode {
    String hashKey();

    void put(String key, String value);

    String get(String key);

    int size();

    void clear();

    String remove(String key);
}

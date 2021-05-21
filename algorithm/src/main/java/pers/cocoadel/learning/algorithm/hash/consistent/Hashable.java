package pers.cocoadel.learning.algorithm.hash.consistent;

public interface Hashable {
    int hash32(String raw);

    long hash64(String raw);
}

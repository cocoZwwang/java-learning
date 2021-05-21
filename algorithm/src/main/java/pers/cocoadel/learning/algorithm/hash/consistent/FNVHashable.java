package pers.cocoadel.learning.algorithm.hash.consistent;

public class FNVHashable implements Hashable{

    private final static int p = 16777619;

    @Override
    public int hash32(String raw) {
        int hash = (int)2166136261L;
        for(int i=0;i<raw.length();i++)
            hash = (hash ^ raw.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return hash;
    }

    @Override
    public long hash64(String raw) {
        return hash32(raw);
    }
}

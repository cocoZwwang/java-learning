package pers.cocoadel.learning.algorithm.hash.consistent;

import cn.hutool.core.util.HashUtil;


public class CityHashable implements Hashable{

    @Override
    public int hash32(String raw) {
        byte[] data = raw.getBytes();
        return HashUtil.cityHash32(data);
    }

    @Override
    public long hash64(String raw) {
        byte[] data = raw.getBytes();
        return HashUtil.cityHash64(data);
    }
}

package pers.cocoadel.learning.algorithm.hash.consistent;

import java.util.HashMap;
import java.util.Map;

/**
 * 常规服务节点
 */
public class NormalPhysicalNode implements PhysicalNode {
    /**
     * 服务名称
     */
    private String name;
    /**
     * 服务地址
     */
    private String host;
    /**
     *  服务端口
     */
    private int port;

    public NormalPhysicalNode(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }

    private Map<String,String> caches = new HashMap<>();

    @Override
    public String hashKey() {
        return String.format("%s:%s:%s", name, host, port);
    }

    @Override
    public void put(String key, String value) {
        caches.put(key,value);
    }

    @Override
    public String get(String key) {
        return caches.get(key);
    }

    @Override
    public int size() {
        return caches.size();
    }

    @Override
    public void clear() {
        caches.clear();
    }

    @Override
    public String remove(String key) {
        return caches.remove(key);
    }


}

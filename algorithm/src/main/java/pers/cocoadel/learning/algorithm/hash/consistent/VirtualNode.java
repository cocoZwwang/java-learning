package pers.cocoadel.learning.algorithm.hash.consistent;

/**
 *
 */
public class VirtualNode<T extends PhysicalNode> implements PhysicalNode{
    /**
     * 物理节点
     */
    private final T physicalNode;
    /**
     * 虚拟节点 id
     */
    private final int replica;

    public VirtualNode(T physicalNode, int replica) {
        this.physicalNode = physicalNode;
        this.replica = replica;
    }

    @Override
    public String hashKey() {
        return physicalNode.hashKey() + "#" + replica;
    }

    @Override
    public void put(String key, String value) {

    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public String remove(String key) {
        return null;
    }

    public T getPhysicalNode(){
        return physicalNode;
    }

    public int getReplica(){
        return replica;
    }
}

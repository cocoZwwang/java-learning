package pers.cocoadel.learning.algorithm.hash.consistent;

import java.util.*;

public class ConsistentHash<T extends PhysicalNode> {
    private final Hashable hashable;

    private final TreeMap<Long, VirtualNode<T>> ring = new TreeMap<>();

    private final LinkedHashMap<String, T> physicalNodes = new LinkedHashMap<>();

    public ConsistentHash(Hashable hashable) {
        this.hashable = hashable;
    }

    public void add(T node) {
        add(node, 1);
    }

    /**
     * 添加节点
     */
    public void add(T node, int replica) {
        physicalNodes.put(node.hashKey(), node);
        int existingReplicas = (int) ring
                .values()
                .stream()
                .filter(vn -> vn.getPhysicalNode().hashKey().equals(node.hashKey()))
                .count();

        for (int i = 1; i < replica + 1; i++) {
            VirtualNode<T> vn = new VirtualNode<>(node, existingReplicas + i);
            ring.put(hashable.hash64(vn.hashKey()), vn);
        }
    }

    /**
     * 删除节点
     */
    public void remove(T node) {
        //删除物理节点
        physicalNodes.remove(node.hashKey());
        //删除虚拟节点
        Set<Map.Entry<Long, VirtualNode<T>>> entries = ring.entrySet();
        Iterator<Map.Entry<Long, VirtualNode<T>>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, VirtualNode<T>> entry = iterator.next();
            VirtualNode<T> vn = entry.getValue();
            if (vn.getPhysicalNode().hashKey().equals(node.hashKey())) {
                iterator.remove();
            }
        }
    }

    /**
     * 获得所有虚拟节点
     */
    public Collection<VirtualNode<T>> getAllVirtualNode() {
        return ring.values();
    }

    /**
     * 获取物理节点的数量
     */
    public int getPhysicalNodeNum() {
        return physicalNodes.size();
    }

    /**
     * 返回每个节点的存储数量
     */
    Double getLoadStdDeviation() {
        List<Double> list = new LinkedList<>();
        physicalNodes.forEach((key, node) -> {
            list.add(node.size() * 1d);
        });
        return Utils.stdDeviation(list);
    }

    /**
     * 根据 key 获取物理节点
     */
    T getNode(String key) {
        if (ring.isEmpty()) {
            return null;
        }

        SortedMap<Long, VirtualNode<T>> sortedMap = ring.tailMap(hashable.hash64(key));
        if (sortedMap.isEmpty()) {
            return ring.firstEntry().getValue().getPhysicalNode();
        }
        return sortedMap.get(sortedMap.firstKey()).getPhysicalNode();
    }


}

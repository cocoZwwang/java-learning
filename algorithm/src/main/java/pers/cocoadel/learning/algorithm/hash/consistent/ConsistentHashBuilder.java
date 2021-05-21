package pers.cocoadel.learning.algorithm.hash.consistent;

import java.util.Collection;

public class ConsistentHashBuilder<T extends  PhysicalNode> {

    private Hashable hashable;

    private Collection<T> nodes;

    private int replica = 1;

    public ConsistentHashBuilder<T> hashable(Hashable hashable) {
        this.hashable = hashable;
        return this;
    }

    public ConsistentHashBuilder<T> nodes(Collection<T> nodes) {
        this.nodes = nodes;
        return this;
    }

    public ConsistentHashBuilder<T> replica(int replica) {
        this.replica = replica;
        return this;
    }

    public ConsistentHash<T> build() {
        ConsistentHash<T> consistentHash = new ConsistentHash<>(hashable);
        for (T node : nodes) {
            consistentHash.add(node,replica);
        }
        return consistentHash;
    }
}

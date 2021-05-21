package pers.cocoadel.learning.algorithm.hash.consistent;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ConsistentHashTest {

    public static void main(String[] args) {
        // 准备100万KV数据
        int dataSize = 1000_000;
        List<Pair<String, String>> data = new ArrayList<>(dataSize);
        for (int i = 0; i < dataSize; i++) {
            data.add(new Pair<>(Utils.randomString(10), "value"));
        }

        // 测试单个服务器的副本数从1到350的性能
        for(int replica = 1; replica < 350;replica++){
            ConsistentHashBuilder<NormalPhysicalNode> builder = new ConsistentHashBuilder<>();
            int nodeSize = 10;
            List<NormalPhysicalNode> nodes = new ArrayList<>(nodeSize);
            for(int phyNodeId = 1; phyNodeId <= nodeSize; phyNodeId++){
                NormalPhysicalNode npn =
                        new NormalPhysicalNode("node:" + phyNodeId,"192.168.0." + phyNodeId,80);
                nodes.add(npn);
            }
            ConsistentHash<NormalPhysicalNode> consistentHash = builder
                    .hashable(new CityHashable())
                    .nodes(nodes)
                    .replica(replica)
                    .build();

            //开始时间
            long startTime = System.currentTimeMillis();
            for (Pair<String, String> pair : data) {
                String key = pair.getKey();
                consistentHash.getNode(key).put(key, pair.getValue());
            }

            long duration = System.currentTimeMillis() - startTime;
            long physicalNodeNum = consistentHash.getPhysicalNodeNum();
            int virtualNodeNum = consistentHash.getAllVirtualNode().size();
            int cacheSize = data.size();
            Double loadStdDeviation = consistentHash.getLoadStdDeviation();
            System.out.printf("物理节点数：%s 副本倍数：%s 虚拟节点数：%s 缓存数：%s 耗时：%s ms 标准差：%s\n",
                    physicalNodeNum, replica, virtualNodeNum, cacheSize, duration, loadStdDeviation);
        }

    }
}

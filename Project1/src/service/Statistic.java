package service;

import service.impl.NodeImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public interface Statistic {
    public static PriorityQueue<NodeImpl> statistics(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        Map<Byte, Integer> map = new HashMap<Byte, Integer>();
        for (byte b : bytes) {
            Byte aByte = b;
            if (map.containsKey(aByte)) {
                map.put(aByte, map.get(aByte) + 1);
            } else {
                map.put(aByte, 1);
            }
        }

        //将统计结果遍历放入优先队列排序
        Byte[] keys = map.keySet().toArray(new Byte[0]);
        PriorityQueue<NodeImpl> queue = new PriorityQueue<>();

        for (Byte b : keys) {
            NodeImpl node = new NodeImpl(b, map.get(b));
            queue.add(node);
        }

        return queue;
    }
}

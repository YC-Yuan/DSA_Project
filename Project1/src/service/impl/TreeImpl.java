package service.impl;

import service.Statistic;
import service.Tree;

import java.io.Serializable;
import java.util.HashMap;
import java.util.PriorityQueue;

public class TreeImpl implements Tree, Serializable {
    public NodeImpl root;
    public NodeImpl scan;

    public TreeImpl(byte[] bytes) {
        PriorityQueue<NodeImpl> queue = Statistic.statistics(bytes);
        if (queue == null) root = null;
        else {
            while (queue.size() >= 2) {
                NodeImpl nodeLeft = queue.poll();
                NodeImpl nodeRight = queue.poll();
                assert nodeRight != null;
                NodeImpl parentNode = new NodeImpl(null, nodeLeft.f + nodeRight.f);
                parentNode.left = nodeLeft;
                parentNode.right = nodeRight;
                nodeLeft.parent = parentNode;
                nodeRight.parent = parentNode;
                queue.add(parentNode);
            }
            root = queue.poll();
            scan = root;
        }
    }

    @Override
    public HashMap<Byte, String> useTree() {
        if (root == null) return new HashMap<>();
        HashMap<Byte, String> map = new HashMap<>();
        useLeaf(root, map);
        return map;
    }

    private static void useLeaf(NodeImpl leaf, HashMap<Byte, String> map) {
        if (leaf.isLeaf()) {
            map.put(leaf.b, leaf.getCode());
        } else {
            useLeaf(leaf.left, map);
            useLeaf(leaf.right, map);
        }
    }

    @Override
    public Byte scan(char code) {
        if (!(code =='0') && !(code=='1')) {
            System.out.println("Wrong code:" + code + ",fail to scan tree");
        } else {
            if (code =='0') {
                scan = scan.left;
                if (scan.isLeaf()) {//找到了叶子
                    Byte aByte = scan.b;
                    scan = root;
                    return aByte;
                }
            } else {
                scan = scan.right;
                if (scan.isLeaf()) {
                    Byte aByte = scan.b;
                    scan = root;
                    return aByte;
                }
            }
        }
        return null;
    }
}

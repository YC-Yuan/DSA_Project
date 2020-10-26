package util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Tree implements Serializable {
    private Node root;

    //封装，给出统计返回map
    public static HashMap<Byte, String> getMap(byte[] bytes) {
        return Tree.useTree(Tree.buildTree(Statistic.statistics(bytes)));
    }

    //封装，给出树
    public static Tree getTree(byte[] bytes){
        return Tree.buildTree(Statistic.statistics(bytes));
    }

    //根据统计结果种树
    public static Tree buildTree(PriorityQueue<Tree.Node> queue) {
        if (queue == null) return null;
        while (queue.size() >= 2) {
            Node nodeLeft = queue.poll();
            Node nodeRight = queue.poll();
            assert nodeRight != null;
            Node parentNode = new Node(null, nodeLeft.f + nodeRight.f);
            parentNode.left = nodeLeft;
            parentNode.right = nodeRight;
            nodeLeft.parent = parentNode;
            nodeRight.parent = parentNode;
            queue.add(parentNode);
        }
        Tree tree = new Tree();
        tree.root = queue.poll();
        return tree;
    }

    //将树转换成HashMap
    public static HashMap<Byte, String> useTree(Tree tree) {
        if (tree == null) return new HashMap<>();
        HashMap<Byte, String> map = new HashMap<>();
        useLeaf(tree.root, map);
        return map;
    }

    //递归函数，作为使用树的工具
    private static void useLeaf(Node leaf, HashMap<Byte, String> map) {
        if (leaf.isLeaf()) {
            map.put(leaf.c, leaf.getCode());
        } else {
            useLeaf(leaf.left, map);
            useLeaf(leaf.right, map);
        }
    }

    public static class Node implements Comparable<Node>, Serializable {
        private final Byte c;
        private final int f;
        private Node parent = null;
        private Node left = null;
        private Node right = null;

        public Node(Byte c, int f) {
            this.c = c;
            this.f = f;
        }

        @Override
        public int compareTo(Node o) {
            return f - o.f;//频率大的排后面
        }

        public String getCode() {
            Node node = this;
            StringBuilder code = new StringBuilder();
            while (!node.isRoot()) {
                code.insert(0, (node.isLeftChild() ? "0" : "1"));
                node = node.parent;
            }
            return code.toString();
        }

        public boolean isLeaf() {
            return left == null;
        }

        public boolean isRoot() {
            return parent == null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }
    }
}

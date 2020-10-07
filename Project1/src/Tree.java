import java.io.Serializable;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Tree implements Serializable {
    private Node root;

    public static Tree buildTree(PriorityQueue<Tree.Node> queue) {
        long start = System.currentTimeMillis();
        if (queue == null) return null;
        while (queue.size() >= 2) {
            Node nodeLeft = queue.poll();
            Node nodeRight = queue.poll();
            assert nodeRight != null;
            Node parentNode = new Node('\0', nodeLeft.f + nodeRight.f);
            parentNode.left = nodeLeft;
            parentNode.right = nodeRight;
            nodeLeft.parent = parentNode;
            nodeRight.parent = parentNode;
            queue.add(parentNode);
        }
        Tree tree = new Tree();
        tree.root = queue.poll();
        System.out.println("buildTree running time:" + (System.currentTimeMillis() - start) + "mills");
        return tree;
    }

    public static HashMap<Character, Integer> useTree(Tree tree) {
        long start = System.currentTimeMillis();
        if (tree == null) return new HashMap<>();
        HashMap<Character, Integer> map = new HashMap<>();
        useLeaf(tree.root, map);
        System.out.println("useTree running time:" + (System.currentTimeMillis() - start) + "mills");
        return map;
    }

    private static void useLeaf(Node leaf, HashMap<Character, Integer> map) {
        if (leaf.isLeaf()) {
            //System.out.println("leaf using c:"+leaf.c+" f:"+leaf.f);
            map.put(leaf.c, leaf.getCode());
        } else {
            useLeaf(leaf.left, map);
            useLeaf(leaf.right, map);
        }
    }

    public static class Node implements Comparable<Node>, Serializable {
        private Character c = '\0';
        private int f = 0;
        private Node parent = null;
        private Node left = null;
        private Node right = null;

        public Node(char c, int f) {
            this.c = c;
            this.f = f;
        }

        @Override
        public int compareTo(Node o) {
            return f - o.f;//频率大的排后面
        }

        public Integer getCode() {
            Node node = this;
            String code = "";
            while (!node.isRoot()) {
                code = node.isLeftChild() ? "0" : "1" + code;
                node = node.parent;
            }
            return Integer.getInteger(code);
        }

        public boolean isLeaf() {
            return c != '\0';
        }

        public boolean isRoot() {
            return parent == null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }
    }
}

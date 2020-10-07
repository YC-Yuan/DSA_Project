import java.util.HashMap;
import java.util.PriorityQueue;

public class Tree {
    private Node root;

    public static Tree buildTree(PriorityQueue<Tree.Node> queue) {
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
        return tree;
    }

    public static HashMap<Character, byte[]> useTree(Tree tree) {
        HashMap<Character, byte[]> map = new HashMap<>();
        useLeaf(tree.root, map);
        return map;
    }

    private static void useLeaf(Node leaf, HashMap<Character, byte[]> map) {
        if (leaf.isLeaf()) map.put(leaf.c, leaf.getCode());
        else {
            useLeaf(leaf.left, map);
            useLeaf(leaf.right, map);
        }
    }

    public static class Node implements Comparable<Node> {
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

        public byte[] getCode() {
            Node node = this;
            byte[] bytes = new byte[this.getCodeLength()];
            for (int i = bytes.length - 1; i >= 0; i--) {
                bytes[i] = (byte) (node.isLeftChild() ? 0 : 1);
            }
            return bytes;
        }

        public int getCodeLength() {
            int count = 0;
            Node node = this;
            while (!node.isRoot()) {
                count++;
                node = node.parent;
            }
            return count;
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

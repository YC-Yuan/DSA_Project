package service.impl;

import service.Node;

import java.io.Serializable;

public class NodeImpl implements Node,Comparable<NodeImpl>,Serializable {
    public final Byte b;
    public final int f;
    public NodeImpl parent = null;
    public NodeImpl left = null;
    public NodeImpl right = null;

    public NodeImpl(Byte b, int f) {
        this.b=b;
        this.f=f;
    }

    @Override
    public int compareTo(NodeImpl o) {
        return f - o.f;//频率大的排后面
    }

    //左走0右走1
    public String getCode() {
        NodeImpl node = this;
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

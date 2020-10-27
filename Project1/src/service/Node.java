package service;

import service.impl.NodeImpl;

public interface Node {
    //排序函数
    public int compareTo(NodeImpl o);

    //返回对应的编码(非叶子返回空)
    public String getCode();

    //是否是叶子结点
    public boolean isLeaf();

    //是否是根节点
    public boolean isRoot();

    //是否是左节点
    public boolean isLeftChild();
}

import java.util.HashSet;
import java.util.LinkedList;

public class AcAuto {
    //字符中类，26个字母
    private static final int NUM = 26;
    public Node root = new Node(null);

    /*内部静态类，用于表示AC自动机的每个结点，在每个结点中我们并没有存储该结点对应的字符*/
    public static class Node {
        public Node fail;//自动机fail指针
        public Node[] next = new Node[NUM];//孩子们
        public String content;

        Node(String content) {
            this.content = content;
        }

        public boolean isWord() {
            return content != null;
        }
    }

    public static int getIndexOfChar(char c) {
        return c - 97;
    }

    /*由目标字符串构建Trie树*/
    void buildTrieTree(String targetStr) {
        Node curr = root;
        for (int i = 0; i < targetStr.length(); i++) {
            int index = getIndexOfChar(targetStr.charAt(i));
            if (curr.next[index] == null) {
                curr.next[index] = new Node(null);
            }
            curr = curr.next[index];
        }
        curr.content=targetStr;
    }

    /*由Trie树构建AC自动机，本质是一个自动机，相当于构建KMP算法的next数组*/
    void buildFail() {
        LinkedList<Node> queue = new LinkedList<Node>();
        for (Node x : root.next) {
            if (x != null) {
                //根结点的所有孩子结点的fail都指向根结点
                x.fail = root;
                queue.addLast(x);
                //所有根结点的孩子结点入列
            }
        }
        while (!queue.isEmpty()) {
            Node p = queue.removeFirst();//p为考虑的父对象
            for (int i = 0; i < NUM; i++) {
                if (p.next[i] != null) {
                    queue.addLast(p.next[i]);//把p的孩子们加入，之后换成p，实际上需要添加fail指针的是p的孩子
                    Node failTo = p.fail;//从p的fail往下找，对第一层来说就是root
                    while (true) {
                        if (failTo == null) {//如果爬到了根节点，用了根节点的failTo才会为空
                            p.next[i].fail = root;
                            break;
                        }
                        if (failTo.next[i] != null) {//在父亲之fail的孩子中发现了这个节点，则设置fail
                            p.next[i].fail = failTo.next[i];
                            break;
                        } else {//
                            failTo = failTo.fail;
                        }
                    }
                }
            }
        }
    }

    public HashSet<String> getSubStrings(String key) {
        HashSet<String> set = new HashSet<>();
        Node curr = root;
        int i = 0;
        while (i < key.length()) {
            int index = getIndexOfChar(key.charAt(i));
            if (curr.next[index] != null) {//可以爬，则向前爬并设置set
                curr = curr.next[index];
                i++;
                if (curr.isWord()) {
                    set.add(curr.content);
                }
            } else {//错了，不能爬，回溯fail指针
                //如果在root出错，则不回溯直接+1
                if (curr == root) {
                    i++;
                } else {
                    curr = curr.fail;
                    if (curr.isWord()) {//如果爬到的目标是子串，则加入
                        set.add(curr.content);
                    }
                }
            }
        }
        //遇到末尾为连续串时，将跳出循环而漏过fail指向的字串
        if (curr.content != null) {
            curr = curr.fail;
        }
        while (curr.content != null) {
            set.add(curr.content);
            curr = curr.fail;
        }
        return set;
    }
}
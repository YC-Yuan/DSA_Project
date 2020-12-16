import com.sun.scenario.animation.shared.ClipEnvelope;

import java.util.*;
import java.util.Map.Entry;

public class AcAuto {
    //字符中类，26个字母
    private static final int NUM = 26;
    private final Node root = new Node('A');

    public HashSet<String> getKeySet(String targetStr) {
        Node target = root;
        for (int i = 0; i < targetStr.length(); i++) {
            target = target.next[getIndexOfChar(targetStr.charAt(i))];
        }
        return target.keys;
    }

    /*内部静态类，用于表示AC自动机的每个结点，在每个结点中我们并没有存储该结点对应的字符*/
    private static class Node {
        Node fail;//自动机fail指针
        Node[] next = new Node[NUM];//孩子们
        char c = 0;
        Node parent = null;
        HashSet<String> keys = new HashSet<>();//对应的查找结果

        Node(char c) {
            this.c = c;
            if (c == 'A') this.fail = null;//root指向fail
        }

    }

    public String getContent(Node node) {
        if (node == root) return "ROOT";
        StringBuilder str = new StringBuilder();
        while (node != root) {
            str.insert(0, node.c);
            node = node.parent;
        }
        return str.toString();
    }

    private static int getIndexOfChar(char c) {
        return c - 97;
    }

    private static char getCharOfIndex(int index) {
        return (char) (index + 97);
    }


    /*由目标字符串构建Trie树*/
    void buildTrieTree(String targetStr) {
        Node curr = root;
        for (int i = 0; i < targetStr.length(); i++) {
            int index = getIndexOfChar(targetStr.charAt(i));
            if (curr.next[index] == null) {
                curr.next[index] = new Node(getCharOfIndex(index));
                curr.next[index].parent = curr;
            }
            curr = curr.next[index];
        }
    }

    /*由Trie树构建AC自动机，本质是一个自动机，相当于构建KMP算法的next数组*/
    void buildFail() {
        LinkedList<Node> queue = new LinkedList<Node>();
        for (Node x : root.next) {
            if (x != null) {
                /*根结点的所有孩子结点的fail都指向根结点*/
                x.fail = root;
                queue.addLast(x);
                /*所有根结点的孩子结点入列*/
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

    /*自动机已构建完毕，输入帖子key值*/
    public void putIntoSet(String key) {
        /*String detectString = "wipgdtkhwatajeekc";
        if (key.equals(detectString)) {
            System.out.println("putting the key:" + key);
        }*/
        //更改此节点set
        Node curr = root;
        int i = 0;
        while (i < key.length()) {
            int index = getIndexOfChar(key.charAt(i));
            if (curr.next[index] != null) {//可以爬，则向前爬并设置set
                curr = curr.next[index];
                i++;
                if (curr != root) {
                    curr.keys.add(key);
                    //if (key.equals(detectString)) System.out.println(getContent(curr));
                }
            } else {//错了，不能爬，回溯fail指针
                curr = curr.fail;
                if (curr.c != 'A') {//对非root设置set
                    curr.keys.add(key);
                    //if (key.equals(detectString)) System.out.println(getContent(curr));
                } /*else {
                    curr=root;
                    i++;
                 }*/
            }
        }
        //遇到末尾为连续串时，将跳出循环而漏过fail指向的字串
        if (curr.c != 'A') {
            curr = curr.fail;
        }
        while (curr.c != 'A') {
            curr.keys.add(key);
            //if (key.equals(detectString)) System.out.println(getContent(curr));
            curr = curr.fail;
        }
    }
}
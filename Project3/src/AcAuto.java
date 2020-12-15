import java.util.*;
import java.util.Map.Entry;

public class AcAuto {
    //字符中类，26个字母
    private static final int NUM = 26;
    private final Node root=new Node();

    public HashSet<String> getKeySet(String targetStr){
        Node target=root;
        for (int i=0;i<targetStr.length();i++){
            target=target.next[getIndexOfChar(targetStr.charAt(i))];
        }
        return target.keys;
    }

    /*内部静态类，用于表示AC自动机的每个结点，在每个结点中我们并没有存储该结点对应的字符*/
    private static class Node {
        Node fail;//自动机fail指针
        Node[] next = new Node[NUM];//孩子们
        HashSet<String> keys=new HashSet<>();//对应的查找结果
    }

    private int getIndexOfChar(char c) {
        return c - 97;
    }


    /*由目标字符串构建Trie树*/
    private void buildTrieTree(String targetStr) {
        Node curr = root;
        for (int i = 0; i < targetStr.length(); i++) {
            int index = getIndexOfChar(targetStr.charAt(i));
            if (curr.next[index] == null) {
                curr.next[index] = new Node();
            }
            curr = curr.next[index];
        }
    }

    /*由Trie树构建AC自动机，本质是一个自动机，相当于构建KMP算法的next数组*/
    private void buildFail() {
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
            /*确定出列结点的所有孩子结点的fail的指向*/
            Node p = queue.removeFirst();
            for (int i = 0; i < NUM; i++) {
                if (p.next[i] != null) {
                    /*孩子结点入列*/
                    queue.addLast(p.next[i]);
                    /*从p.fail开始找起*/
                    Node failTo = p.fail;
                    while (true) {
                        /*说明找到了根结点还没有找到*/
                        if (failTo == null) {
                            p.next[i].fail = root;
                            break;
                        }
                        /*说明有公共前缀*/
                        if (failTo.next[i] != null) {
                            p.next[i].fail = failTo.next[i];
                            break;
                        } else {
                            /*继续向上寻找*/
                            failTo = failTo.fail;
                        }
                    }
                }
            }
        }
    }

    /*自动机已构建完毕，输入帖子key值*/
    public void putIntoSet(String key) {
        Node curr = root;
        int i = 0;
        while (i < key.length()) {
            /*文本串中的字符*/
            int index = getIndexOfChar(key.charAt(i));
            /*文本串中的字符和AC自动机中的字符进行比较*/
            if (curr.next[index] != null) {
                /*若相等，自动机进入下一状态*/
                curr = curr.next[index];
                //更改此节点set
                curr.keys.add(key);
                i++;
            } else {
                /*若不等，找到下一个应该比较的状态*/
                curr = curr.fail;
                /*到根结点还未找到，说明文本串中以ch作为结束的字符片段不是任何目标字符串的前缀，
                 * 状态机重置，比较下一个字符*/
                if (curr == null) {
                    curr = root;
                    i++;
                }
            }
        }
    }

    public static void main(String[] args) {
        AcAuto acAuto = new AcAuto();

        //需要查询的contain输入
        acAuto.buildTrieTree("fd");

        //fail指针构建
        acAuto.buildFail();

        //需要放入的帖子key
        acAuto.putIntoSet("hellofdu");
        acAuto.putIntoSet("hellozhangjiang");
        acAuto.putIntoSet("beautifulfdu");

        //查询contain中字符串
        System.out.println(Arrays.toString(acAuto.getKeySet("fd").toArray()));

    }
}
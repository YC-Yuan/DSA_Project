import java.util.*;

public class Main {

    public static TreeMap<String, Long> map = new TreeMap<>();


    public static void main(String[] args) {

        Scanner cin = new Scanner(System.in);

        //工具常量区
        String input;
        String method, content;
        String key;
        long value;
        int index;
        SortedMap<String, Long> sortedMap;
        HashSet<String> keySet;

        String number = cin.nextLine();
        int num = Integer.parseInt(number);

        //存储输入和自动机
        String[] inputStrings = new String[num];
        AcAuto acAuto = new AcAuto();
        //或者改成分散式储存？
        Byte[] methodIndexes = new Byte[num];
        String[] keys = new String[num];
        long[] addValues = new long[num];
        byte methodIndex;

        //读入阶段，存入queue并构建自动机
        for (int i = 0; i < num; i++) {
            //存放
            input = cin.nextLine();
            //inputStrings[i] = input;
            //分别处理input

            index = input.indexOf(' ');
            method = input.substring(0, index++);
            content = input.substring(index);

            methodIndex = getMethodIndex(method);
            methodIndexes[i] = methodIndex;

            switch (methodIndex) {
                case 0://P
                case 1://A
                case 4://AB
                    index = content.indexOf(' ');
                    key = content.substring(0, index++);
                    value = Long.parseLong(content.substring(index));
                    keys[i] = key;
                    addValues[i] = value;
                    break;
                case 2://Q
                case 3://D
                case 5://QB
                case 6://DB
                    keys[i] = content;
                    break;
                case 7://AC
                    index = content.indexOf(' ');
                    key = content.substring(0, index++);
                    value = Long.parseLong(content.substring(index));
                    keys[i] = key;
                    addValues[i] = value;
                    acAuto.buildTrieTree(key);//构建key
                    break;
                case 8://QC
                case 9://DC
                    keys[i] = content;
                    acAuto.buildTrieTree(content);//构建key
                    break;
            }
        }

        acAuto.buildFail();

        for (int i = 0; i < num; i++) {//自动机构建完毕，取用存储的命令
            /*input = inputStrings[i];
            index = input.indexOf(' ');
            method = input.substring(0, index++);
            content = input.substring(index);*/
            methodIndex = methodIndexes[i];
            key = keys[i];
            value = addValues[i];
            switch (methodIndex) {
                case 0:
                    acAuto.putIntoSet(key);
                    map.put(key, value);
                    break;
                case 1:
                    value += map.get(key);
                    map.replace(key, value);
                    break;
                case 2:
                    System.out.println(map.get(key));
                    break;
                case 3:
                    map.replace(key, 0L);
                    break;
                case 4:
                    sortedMap = getSortedMap(key);
                    for (String aKey : sortedMap.keySet()
                    ) {
                        map.replace(aKey, map.get(aKey) + value);
                    }
                    break;
                case 5:
                    sortedMap = getSortedMap(key);
                    long sum = 0;
                    for (String aKey : sortedMap.keySet()
                    ) {
                        sum += map.get(aKey);
                    }
                    System.out.println(sum);
                    break;
                case 6:
                    sortedMap = getSortedMap(key);
                    for (String aKey : sortedMap.keySet()
                    ) {
                        map.replace(aKey, 0L);
                    }
                    break;
                case 7:
                    keySet = acAuto.getKeySet(key);
                    for (String aKey : keySet) {
                        map.replace(aKey, value + map.get(aKey));
                    }
                    break;
                case 8:
                    keySet = acAuto.getKeySet(key);
                    long sumContain = 0;
                    for (String aKey : keySet) {
                        sumContain += map.get(aKey);
                    }
                    System.out.println(sumContain);
                    break;
                case 9:
                    keySet = acAuto.getKeySet(key);
                    for (String aKey : keySet) {
                        map.replace(aKey, 0L);
                    }
                    break;
            }
            /*switch (method) {
                case "PUT":
                    index = content.indexOf(' ');
                    key = content.substring(0, index++);
                    value = Long.parseLong(content.substring(index));
                    acAuto.putIntoSet(key);
                    map.put(key, value);
                    break;
                case "ADD":
                    index = content.indexOf(' ');
                    key = content.substring(0, index++);
                    value = Long.parseLong(content.substring(index));
                    value += map.get(key);
                    map.replace(key, value);
                    break;
                case "QUERY":
                    System.out.println(map.get(content));
                    break;
                case "DEL":
                    map.replace(content, 0L);
                    break;
                case "ADDBEGINWITH":
                    index = content.indexOf(" ");
                    key = content.substring(0, index++);
                    value = Long.parseLong(content.substring(index));
                    sortedMap = getSortedMap(key);
                    for (String aKey : sortedMap.keySet()
                    ) {
                        map.replace(aKey, map.get(aKey) + value);
                    }
                    break;
                case "QUERYBEGINWITH":
                    sortedMap = getSortedMap(content);
                    long sum = 0;
                    for (String aKey : sortedMap.keySet()
                    ) {
                        sum += map.get(aKey);
                    }
                    System.out.println(sum);
                    break;
                case "DELBEGINWITH":
                    sortedMap = getSortedMap(content);
                    for (String aKey : sortedMap.keySet()
                    ) {
                        map.replace(aKey, 0L);
                    }
                    break;
                case "ADDCONTAIN":
                    index = content.indexOf(" ");
                    key = content.substring(0, index++);
                    value = Long.parseLong(content.substring(index));
                    keySet = acAuto.getKeySet(key);
                    for (String aKey : keySet) {
                        map.replace(aKey, value + map.get(aKey));
                    }
                    break;
                case "QUERYCONTAIN":
                    keySet = acAuto.getKeySet(content);
                    long sumContain = 0;
                    for (String aKey : keySet) {
                        sumContain += map.get(aKey);
                    }
                    System.out.println(sumContain);
                    break;
                case "DELCONTAIN":
                    keySet = acAuto.getKeySet(content);
                    for (String aKey : keySet) {
                        map.replace(aKey, 0L);
                    }
                    break;
            }*/
        }
    }

    public static byte getMethodIndex(String str) {
        switch (str) {
            case "PUT":
                return 0;
            case "ADD":
                return 1;
            case "QUERY":
                return 2;
            case "DEL":
                return 3;
            case "ADDBEGINWITH":
                return 4;
            case "QUERYBEGINWITH":
                return 5;
            case "DELBEGINWITH":
                return 6;
            case "ADDCONTAIN":
                return 7;
            case "QUERYCONTAIN":
                return 8;
            case "DELCONTAIN":
                return 9;
            default:
                return 88;
        }
    }


    public static SortedMap<String, Long> getSortedMap(String key) {
        return map.subMap(key, key.substring(0, key.length() - 1) + (char) (key.charAt(key.length() - 1) + 1));
    }

    public static class AcAuto {
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
            HashSet<String> keys = new HashSet<>();//对应的查找结果

            Node(char c) {
                this.c = c;
                if (c == 'A') this.fail = null;//root指向fail
            }
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
                    }
                } else {//错了，不能爬，回溯fail指针
                    curr = curr.fail;
                    if (curr.c != 'A') {//对非root设置set
                        curr.keys.add(key);
                    }
                }
            }
            //遇到末尾为连续串时，将跳出循环而漏过fail指向的字串
            if (curr.c != 'A') {
                curr = curr.fail;
            }
            while (curr.c != 'A') {
                curr.keys.add(key);
                curr = curr.fail;
            }
        }
    }
}

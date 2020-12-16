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

        int num = cin.nextInt();
        cin.nextLine();

        //存储输入和自动机
        HashMap<String, HashSet<String>> next = new HashMap<>();//存储子串→对应键的关系
        AcAuto acAuto = new AcAuto();
        //或者改成分散式储存？
        Byte[] methodIndexes = new Byte[num];//模式类型
        String[] keys = new String[num];//查询key
        long[] addValues = new long[num];//增加值(如果有的话)
        byte methodIndex;

        //读入阶段，存入queue并构建自动机
        for (int i = 0; i < num; i++) {
            //存放
            input = cin.nextLine();
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
                    next.put(key, new HashSet<>());
                    break;
                case 8://QC
                case 9://DC
                    keys[i] = content;
                    next.put(content, new HashSet<>());
                    acAuto.buildTrieTree(content);//构建key
                    break;
            }
        }

        acAuto.buildFail();

        for (int i = 0; i < num; i++) {//自动机构建完毕，取用存储的命令
            methodIndex = methodIndexes[i];
            key = keys[i];
            value = addValues[i];
            switch (methodIndex) {
                case 0:
                    for (String subString : acAuto.getSubStrings(key)) {
                        next.get(subString).add(key);
                    }
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
                    keySet = next.get(key);
                    for (String aKey : keySet) {
                        map.replace(aKey, value + map.get(aKey));
                    }
                    break;
                case 8:
                    keySet = next.get(key);
                    long sumContain = 0;
                    for (String aKey : keySet) {
                        sumContain += map.get(aKey);
                    }
                    System.out.println(sumContain);
                    break;
                case 9:
                    keySet = next.get(key);
                    for (String aKey : keySet) {
                        map.replace(aKey, 0L);
                    }
                    break;
            }
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
            curr.content = targetStr;
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
            Node curr = root,temp;
            int i = 0;
            while (i < key.length()) {
                int index = getIndexOfChar(key.charAt(i));//获取当前考虑的字符
                if (curr.next[index] != null) {//可以爬，则向前爬并设置set
                    curr = curr.next[index];
                    temp=curr;
                    while(temp!=root){//判断成功的情况下，把fail上一路都加进去
                        if (temp.isWord()) set.add(temp.content);
                        temp=temp.fail;
                    }
                    if (curr.isWord()) {
                        set.add(curr.content);
                    }
                    i++;
                } else {//匹配失败
                    if (curr==root) i++;//从root匹配失败需要更新目标
                    else {//在中段匹配失败，则向fail指针搜索
                        curr=curr.fail;
                        if (curr.isWord()) set.add(curr.content);
                    }
                }
            }
            return set;
        }
    }
}

import java.util.*;

public class NoSQL_6 {
    static TreeMap<String, Long> sql = new TreeMap<>();//大表

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        Set<String> map;//?
        Set<String> target = new HashSet<>();//需要contain的东西
        HashMap<String, HashSet<String>> cache = new HashMap<>();//缓存
        HashSet<String> result;//结果
        int loopNum;
        String[] command;//读入指令
        loopNum = cin.nextInt();
        cin.nextLine();
        int[] commandHead = new int[loopNum];
        String[][] commands = new String[loopNum][2];
        for (int i = 0; i < loopNum; i++) {
            String[] commandTemp = mySplit(cin.nextLine()," ");
            switch (commandTemp[0]) {
                case "PUT":
                    commandHead[i] = 1;
                    commands[i][0] = commandTemp[1];
                    commands[i][1] = commandTemp[2];
                    break;
                case "QUERY":
                    commandHead[i] = 2;
                    commands[i][0] = commandTemp[1];
                    break;
                case "ADD":
                    commandHead[i] = 3;
                    commands[i][0] = commandTemp[1];
                    commands[i][1] = commandTemp[2];
                    break;
                case "DEL":
                    commandHead[i] = 4;
                    commands[i][0] = commandTemp[1];
                    break;
                case "ADDBEGINWITH":
                    commandHead[i] = 5;
                    commands[i][0] = commandTemp[1];
                    commands[i][1] = commandTemp[2];
                    break;
                case "QUERYBEGINWITH":
                    commandHead[i] = 6;
                    commands[i][0] = commandTemp[1];
                    break;
                case "DELBEGINWITH":
                    commandHead[i] = 7;
                    commands[i][0] = commandTemp[1];
                    break;
                case "ADDCONTAIN":
                    commandHead[i] = 8;
                    target.add(commandTemp[1]);
                    commands[i][0] = commandTemp[1];
                    commands[i][1] = commandTemp[2];
                    break;
                case "QUERYCONTAIN":
                    commandHead[i] = 9;
                    target.add(commandTemp[1]);
                    commands[i][0] = commandTemp[1];
                    break;
                case "DELCONTAIN":
                    commandHead[i] = 10;
                    target.add(commandTemp[1]);
                    commands[i][0] = commandTemp[1];
                    break;
            }
        }

        ArrayList<String> a= new ArrayList<>();//把contain对象放进a里，并创建对应缓存集合
        for (String s : target) {
            cache.put(s, new HashSet<>());
            a.add(s);
        }
        AhoCorasickAutomation aca = new AhoCorasickAutomation(a);//根据输入内容构建自动机

        for (int i = 0; i < loopNum; i++) {
            command = commands[i];
            switch (commandHead[i]) {
                case 1://PUT command[0]为key
                    sql.put(command[0], Long.parseLong(command[1]));
                    result = aca.find(command[0]);//此处为缓存，放到子串对应的key集合中
                    for (String s : result) {
                            cache.get(s).add(command[0]);
                    }
                    break;
                case 2://ADD
                    System.out.println(sql.get(command[0]));
                    break;
                case 3://QU
                    Long val = sql.get(command[0]) + Long.parseLong(command[1]);
                    sql.put(command[0], val);
                    break;
                case 4://DEL
                    sql.put(command[0], 0L);
                    break;
                case 5://ADDBINGIN
                    map = startsWith(command[0]).keySet();
                    for (String key : map) {
                        sql.put(key, sql.get(key) + Long.parseLong(command[1]));
                    }
                    break;
                case 6://QUBINGIN
                    map = startsWith(command[0]).keySet();
                    Long temp = 0L;
                    for (String key : map) {
                        temp += sql.get(key);
                    }
                    System.out.println(temp);
                    break;
                case 7://DELBINGIN
                    map = startsWith(command[0]).keySet();
                    for (String key : map) {
                        sql.put(key, 0L);
                    }
                    break;
                case 8://ADDCONTAIN
                    result = cache.get(command[0]);
                    for (String key : result) {
                        sql.put(key, sql.get(key) + Long.parseLong(command[1]));
                    }
                    break;
                case 9://QUCONTAIN
                    Long temp1 = 0L;
                    result = cache.get(command[0]);
                    for (String key : result) {
                        temp1 += sql.get(key);
                    }
                    System.out.println(temp1);
                    break;
                case 10://DELCONTAIN
                    result = cache.get(command[0]);
                    for (String key : result) {
                        sql.put(key, 0L);
                    }
                    break;
            }
        }
    }

    public static SortedMap<String, Long> startsWith(String prefix) {
        return sql.subMap(prefix, prefix.substring(0, prefix.length() - 1) + (char) (prefix.charAt(prefix.length() - 1) + 1));
    }

    public static String[] mySplit(String str, String delim) {
        List<String> stringList = new ArrayList<>();
        while (true) {
            int k = str.indexOf(delim);
            if (k < 0) {
                stringList.add(str);
                break;
            }
            String s = str.substring(0, k);
            stringList.add(s);
            str = str.substring(k + 1);
        }
        String[] res = new String[stringList.size()];
        int i = 0;
        for (String s : stringList) {
            res[i] = s;
            i++;
        }
        return res;
    }

    static class AhoCorasickAutomation {
        private static final int ASCII = 26;
        private final Node root;
        private final List<String> target;

        private static class Node{
            String str;
            Node[] table = new Node[ASCII];
            Node fail;

            public boolean isWord(){
                return str != null;
            }
        }


        public AhoCorasickAutomation(List<String> target){
            root = new Node();
            this.target = target;
            buildTrieTree();
            build_AC_FromTrie();
        }


        private void buildTrieTree(){
            for(String targetStr : target){
                Node curr = root;
                for(int i = 0; i < targetStr.length(); i++){
                    int ch = targetStr.charAt(i)-'a';
                    if(curr.table[ch] == null){
                        curr.table[ch] = new Node();
                    }
                    curr = curr.table[ch];
                }
                curr.str = targetStr;
            }
        }

        private void build_AC_FromTrie(){
            LinkedList<Node> queue = new LinkedList<>();

            for(int i = 0; i < ASCII; i++){
                if(root.table[i] != null){
                    root.table[i].fail = root;
                    queue.addLast(root.table[i]);
                }
            }

            while(!queue.isEmpty()){
                Node p = queue.removeFirst();
                for(int i = 0; i < ASCII; i++){
                    if(p.table[i] != null){
                        queue.addLast(p.table[i]);
                        Node failTo = p.fail;
                        while(true){
                            if(failTo == null){
                                p.table[i].fail = root;
                                break;
                            }
                            if(failTo.table[i] != null){
                                p.table[i].fail = failTo.table[i];
                                break;
                            }else{
                                failTo = failTo.fail;
                            }
                        }
                    }
                }
            }
        }


        public HashSet<String> find(String text){

            HashSet<String> result = new HashSet<>();
            int n = text.length();
            Node curr = root,temp;
            int index;
            for(int i = 0; i < n; ++i)//主串从i=0开始
            {
                index = text.charAt(i)-'a';//子节点下标
                while(curr.table[index] == null && curr != root)
                {//p不为root，且 子节点为空（找不到那个i对应的字符）
                    curr = curr.fail;    //失败指针发挥作用的地方
                }
                curr = curr.table[index];
                if(curr == null)
                    curr = root;   //如果没有匹配的，从root开始重新匹配
                temp = curr;
                while(temp != root)//打印出可以匹配的模式串
                {
                    if(temp.isWord())
                    {
                        result.add(temp.str);
                    }
                    temp = temp.fail;
                }
            }
            return result;
        }

    }
}


import java.util.*;

public class MainWithAcAuto {

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

        //读入阶段，存入queue并构建自动机
        for (int i = 0; i < num; i++) {
            //存放
            input = cin.nextLine();
            inputStrings[i] = input;
            //如果是CONTAIN Key则需要构建自动机
            if (input.indexOf('C') != -1) {//说明是CONTAIN
                if (input.charAt(0) == 'A') {//参数为Key，Value
                    acAuto.buildTrieTree(input.substring(input.indexOf(' ') + 1, input.lastIndexOf(' ')));
                } else {//参数为Key
                    acAuto.buildTrieTree(input.substring(input.lastIndexOf(' ') + 1));
                }
            }
        }

        acAuto.buildFail();

        for (int i = 0; i < num; i++) {//自动机构建完毕，取用存储的命令
            input = inputStrings[i];
            index = input.indexOf(' ');
            method = input.substring(0, index++);
            content = input.substring(index);
            switch (method) {
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
//                    System.out.println("queryBegin:" + content);
                    for (String aKey : sortedMap.keySet()
                    ) {
//                        System.out.println("aKey = " + aKey + " num:" + map.get(aKey));
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
                    //System.out.println("queryContain:" + content);
                    for (String aKey : keySet) {
                        //System.out.println("aKey = " + aKey + " num:" + map.get(aKey));
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
            }
        }
        //System.out.println(Arrays.toString(acAuto.getKeySet("om").toArray()));
    }


    public static SortedMap<String, Long> getSortedMap(String key) {
        return map.subMap(key, key.substring(0, key.length() - 1) + (char) (key.charAt(key.length() - 1) + 1));
    }
}
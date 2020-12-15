import java.util.*;

public class MainTest {

    public static TreeMap<String,Long> map = new TreeMap<>();

    public static void main(String[] args) {

        Scanner cin = new Scanner(System.in);

        //工具常量区
        String input;
        String method, content;
        String key;
        long value;
        int index;
        SortedMap<String,Long> sortedMap;
        HashSet<String> keySet;

        String number = cin.nextLine();
        int num = Integer.parseInt(number);

        //存储输入和自动机
        String[] inputStrings = new String[num];

        //读入阶段
        for (int i = 0; i < num; i++) {
            //存放
            input = cin.nextLine();
            inputStrings[i] = input;
        }

        for (int i = 0; i < num; i++) {//自动机构建完毕，取用存储的命令
            input = inputStrings[i];
            index = input.indexOf(' ');
            method = input.substring(0,index++);
            content = input.substring(index);
            switch (method) {
                case "PUT":
                    index = content.indexOf(' ');
                    key = content.substring(0,index++);
                    value = Long.parseLong(content.substring(index));
                    map.put(key,value);
                    break;
                case "ADD":
                    index = content.indexOf(' ');
                    key = content.substring(0,index++);
                    value = Long.parseLong(content.substring(index));
                    value += map.get(key);
                    map.replace(key,value);
                    break;
                case "QUERY":
                    System.out.println(map.get(content));
                    break;
                case "DEL":
                    map.replace(content,0L);
                    break;
                case "ADDBEGINWITH":
                    index = content.indexOf(" ");
                    key = content.substring(0,index++);
                    value = Long.parseLong(content.substring(index));
                    sortedMap = getSortedMap(key);
                    for (String aKey : sortedMap.keySet()
                    ) {
                        map.replace(aKey,map.get(aKey) + value);
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
                        map.replace(aKey,0L);
                    }
                    break;
                case "ADDCONTAIN":
                    //暴力方法
                    index = content.indexOf(" ");
                    key = content.substring(0,index++);
                    for (String aKey : map.keySet()
                    ) {
                        if (aKey.contains(key)) {
                            value = Long.parseLong(content.substring(index));
                            value += map.get(aKey);
                            map.replace(aKey,value);
                        }
                    }
                    break;
                case "QUERYCONTAIN":
                    //暴力方法
                    long sumContain = 0;
                    for (String aKey : map.keySet()
                    ) {
                        if (aKey.contains(content)) {
                            sumContain += map.get(aKey);
                        }
                    }
                    System.out.println(sumContain);
                    break;
                case "DELCONTAIN":
                    //暴力方法
                    for (String aKey : map.keySet()) {
                        if (aKey.contains(content)) map.replace(aKey,0L);
                    }
                    break;
            }
        }
    }


    public static SortedMap<String,Long> getSortedMap(String key) {
        return map.subMap(key,key.substring(0,key.length() - 1) + (char) (key.charAt(key.length() - 1) + 1));
    }
}
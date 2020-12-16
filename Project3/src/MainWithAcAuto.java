import java.io.*;
import java.nio.Buffer;
import java.util.*;

public class MainWithAcAuto {

    public static TreeMap<String,Long> map = new TreeMap<>();


    public static void main(String[] args) throws IOException {

        File inFile = new File("src/test.txt");
        BufferedReader reader = new BufferedReader(new FileReader(inFile));

        File file = new File("src/compare1.txt");
        FileWriter writer = new FileWriter(file);

        //工具常量区
        String input;
        String method, content;
        String key;
        long value;
        int index;
        SortedMap<String,Long> sortedMap;
        HashSet<String> keySet;

        String number = reader.readLine();
        int num = Integer.parseInt(number);

        //存储输入和自动机
        HashMap<String,HashSet<String>> table = new HashMap<>();//存储子串→对应键的关系
        AcAuto acAuto = new AcAuto();
        //或者改成分散式储存？
        Byte[] methodIndexes = new Byte[num];//模式类型
        String[] keys = new String[num];//查询key
        long[] addValues = new long[num];//增加值(如果有的话)
        byte methodIndex;

        //读入阶段，存入queue并构建自动机
        for (int i = 0; i < num; i++) {
            //存放
            input = reader.readLine();
            //分别处理input

            index = input.indexOf(' ');
            method = input.substring(0,index++);
            content = input.substring(index);

            methodIndex = getMethodIndex(method);
            methodIndexes[i] = methodIndex;

            switch (methodIndex) {
                case 0://P
                case 1://A
                case 4://AB
                    index = content.indexOf(' ');
                    key = content.substring(0,index++);
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
                    key = content.substring(0,index++);
                    value = Long.parseLong(content.substring(index));
                    keys[i] = key;
                    addValues[i] = value;
                    acAuto.buildTrieTree(key);//构建key
                    table.put(key,new HashSet<>());
                    break;
                case 8://QC
                case 9://DC
                    keys[i] = content;
                    table.put(content,new HashSet<>());
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
                case 0://P
                    for (String subString : acAuto.getSubStrings(key)) {
                        table.get(subString).add(key);
                    }
                    map.put(key,value);
                    break;
                case 1://A
                    value += map.get(key);
                    map.replace(key,value);
                    break;
                case 2://Q
                    writer.write(map.get(key)+"\n");
                    break;
                case 3://D
                    map.replace(key,0L);
                    break;
                case 4://AB
                    sortedMap = getSortedMap(key);
                    for (String aKey : sortedMap.keySet()
                    ) {
                        map.replace(aKey,map.get(aKey) + value);
                    }
                    break;
                case 5://QB
                    sortedMap = getSortedMap(key);
                    long sum = 0;
                    for (String aKey : sortedMap.keySet()
                    ) {
                        sum += map.get(aKey);
                    }
                    writer.write(sum+"\n");
                    break;
                case 6://DB
                    sortedMap = getSortedMap(key);
                    for (String aKey : sortedMap.keySet()
                    ) {
                        map.replace(aKey,0L);
                    }
                    break;
                case 7://AC
                    keySet = table.get(key);
                    for (String aKey : keySet) {
                        map.replace(aKey,value + map.get(aKey));
                    }
                    break;
                case 8://QC
                    keySet = table.get(key);
                    long sumContain = 0;
                    for (String aKey : keySet) {
                        sumContain += map.get(aKey);
                    }
                    writer.write(sumContain+"\n");
                    break;
                case 9://DC
                    keySet = table.get(key);
                    for (String aKey : keySet) {
                        map.replace(aKey,0L);
                    }
                    break;
            }
        }
        writer.flush();
        writer.close();
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


    public static SortedMap<String,Long> getSortedMap(String key) {
        return map.subMap(key,key.substring(0,key.length() - 1) + (char) (key.charAt(key.length() - 1) + 1));
    }
}
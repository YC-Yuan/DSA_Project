import java.io.*;
import java.nio.Buffer;
import java.util.*;

public class MainWithAcAuto {

    public static TreeMap<String,Long> map = new TreeMap<>();


    public static void main(String[] args) throws IOException {

        //Scanner cin = new Scanner(System.in);

        File inFile = new File("src/test.txt");
        BufferedReader reader = new BufferedReader(new FileReader(inFile));

        File file=new File("src/compare1.txt");
        FileWriter writer=new FileWriter(file);

        //工具常量区
        String input;
        String method, content;
        String key;
        long value;
        int index;
        SortedMap<String,Long> sortedMap;
        HashSet<String> keySet;

        //String number = cin.nextLine();
        String number=reader.readLine();

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
            input=reader.readLine();
            //input = cin.nextLine();
            //inputStrings[i] = input;
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
                    map.put(key,value);
                    break;
                case 1:
                    value += map.get(key);
                    map.replace(key,value);
                    break;
                case 2:
                    writer.write(map.get(key)+"\n");
                    //System.out.println(map.get(key));
                    break;
                case 3:
                    map.replace(key,0L);
                    break;
                case 4:
                    sortedMap = getSortedMap(key);
                    for (String aKey : sortedMap.keySet()
                    ) {
                        map.replace(aKey,map.get(aKey) + value);
                    }
                    break;
                case 5:
                    sortedMap = getSortedMap(key);
                    long sum = 0;
                    for (String aKey : sortedMap.keySet()
                    ) {
                        sum += map.get(aKey);
                    }
                    writer.write(sum+"\n");
                    //System.out.println(sum);
                    break;
                case 6:
                    sortedMap = getSortedMap(key);
                    for (String aKey : sortedMap.keySet()
                    ) {
                        map.replace(aKey,0L);
                    }
                    break;
                case 7:
                    keySet = acAuto.getKeySet(key);
                    for (String aKey : keySet) {
                        map.replace(aKey,value + map.get(aKey));
                    }
                    break;
                case 8:
                    keySet = acAuto.getKeySet(key);
                    long sumContain = 0;
                    for (String aKey : keySet) {
                        sumContain += map.get(aKey);
                    }
                    //System.out.println(sumContain);
                    writer.write(sumContain+"\n");
                    break;
                case 9:
                    keySet = acAuto.getKeySet(key);
                    for (String aKey : keySet) {
                        map.replace(aKey,0L);
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
        writer.flush();
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
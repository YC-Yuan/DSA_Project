import java.util.*;

public class Main {

    public static TreeMap<String, Long> map = new TreeMap<>();


    public static void main(String[] args) {

        Scanner cin = new Scanner(System.in);

        String input;

        String method, content;
        String key;
        long value;

        int index;

        String number = cin.nextLine();
        int num = Integer.parseInt(number);

        SortedMap<String, Long> sortedMap;


        while (num > 0) {
            input = cin.nextLine();
            index = input.indexOf(" ");
            method = input.substring(0, index++);
            content = input.substring(index);
            switch (method) {
                case "PUT":
                    put(content);
                    break;
                case "ADD":
                    add(content);
                    break;
                case "QUERY":
                    query(content);
                    break;
                case "DEL":
                    del(content);
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
                    for (String aKey : map.keySet()
                    ) {
                        if (aKey.contains(key)) {
                            value = Long.parseLong(content.substring(index));
                            value += map.get(aKey);
                            map.replace(aKey, value);
                        }
                    }
                    break;
                case "QUERYCONTAIN":
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
                    for (String aKey : map.keySet()) {
                        if (aKey.contains(content)) map.replace(aKey, 0L);
                    }
                    break;
            }
            num--;
        }
    }

    public static void add(String content) {
        int index = content.indexOf(" ");
        String key = content.substring(0, index++);
        long value = Long.parseLong(content.substring(index));
        value += map.get(key);
        map.replace(key, value);
    }

    public static void put(String content) {
        int index = content.indexOf(" ");
        String key = content.substring(0, index++);
        long value = Long.parseLong(content.substring(index));
        map.put(key, value);
    }

    public static void query(String content) {
        System.out.println(map.get(content));
    }

    public static void del(String content) {
        map.replace(content, 0L);
    }


    public static SortedMap<String, Long> getSortedMap(String key) {
        return map.subMap(key, key.substring(0, key.length() - 1) + (char) (key.charAt(key.length() - 1) + 1));
    }
}
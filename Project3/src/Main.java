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
                    index = content.indexOf(" ");
                    key = content.substring(0, index++);
                    value = Long.parseLong(content.substring(index));
                    map.put(key, value);
                    break;
                case "ADD":
                    index = content.indexOf(" ");
                    key = content.substring(0, index++);
                    value = Long.parseLong(content.substring(index));
                    value += map.get(key);
                    map.put(key, value);
                    break;
                case "QUERY":
                    System.out.println(map.get(content));
                    break;
                case "DEL":
                    map.put(content, 0L);
                    break;
                case "ADDBEGINWITH":
                    index = content.indexOf(" ");
                    key = content.substring(0, index++);
                    value = Long.parseLong(content.substring(index));

                    sortedMap = getSortedMap(key);
                    for (String aKey : sortedMap.keySet()
                    ) {
                        map.put(aKey, map.get(aKey) + value);
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
                        map.put(aKey, 0L);
                    }
                    break;
            }
            num--;
        }
    }

    public static SortedMap<String, Long> getSortedMap(String key) {
        if (key.charAt(key.length() - 1) == 'z') {
            return map.tailMap(key);
        } else {
            if (key.length() == 1) return map.subMap(key, Character.toString((char) (key.charAt(0) + 1)));
            else {
                String newKey = key.substring(0, key.length() - 2) + (char) (key.charAt(key.length() - 1) + 1);
                return map.subMap(key, newKey);
            }
        }
    }
}
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);

        HashMap<String,Long> hashmap = new HashMap<>(10000);

        String method;
        String content;
        String key;
        long value;

        cin.nextLine();

        while (cin.hasNext()) {
            String str = cin.nextLine();

            method = str.substring(0,str.indexOf(" "));
            content = str.substring(str.indexOf(" ") + 1);

            switch (method) {
                case "PUT":
                    key=content.substring(0,content.indexOf(" "));
                    value= Integer.parseInt(content.substring(content.indexOf(" ")+1));
                    hashmap.put(key,value);
                    break;
                case "ADD":
                    key=content.substring(0,content.indexOf(" "));
                    value=Integer.parseInt(content.substring(content.indexOf(" ")+1));
                    value+=hashmap.get(key);
                    hashmap.put(key,value);
                    break;
                case "QUERY":
                    System.out.println(hashmap.get(content));
                    break;
                case "DEL":
                    hashmap.remove(content);
                    hashmap.put(content,(long) 0);
            }
        }
    }
}
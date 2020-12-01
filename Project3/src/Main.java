import java.util.*;

public class Main {
    public static void main(String args[]) {
        Scanner cin = new Scanner(System.in);

        String input;

        String method, content;
        String key;
        long value;

        HashMap<String,Long> map=new HashMap<>();

        int index;

        cin.nextLine();


        while (cin.hasNext()) {
            input=cin.nextLine();
            index=input.indexOf(" ");
            method=input.substring(0,index++);
            content=input.substring(index);

            switch (method){
                case "PUT":
                    index=content.indexOf(" ");
                    key=content.substring(0,index++);
                    value=Long.parseLong(content.substring(index));
                    map.put(key,value);
                    break;
                case "ADD":
                    index=content.indexOf(" ");
                    key=content.substring(0,index++);
                    value=Long.parseLong(content.substring(index));
                    value+=map.get(key);
                    map.put(key,value);
                    break;
                case "QUERY":
                    System.out.println(map.get(content));
                    break;
                case "DEL":
                    map.put(content,(long)0);
                    break;

            }
        }
    }
}
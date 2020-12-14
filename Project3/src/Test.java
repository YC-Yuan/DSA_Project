import java.util.SortedMap;
import java.util.TreeMap;

public class Test {
    public static void main(String[]args){
        TreeMap<String,Long> map=Main.map;

        map.put("fzzzz",0L);
        map.put("fzzza",0L);
        map.put("fzzzzz",0L);
        map.put("fuck",0L);
        map.put("fdjiaj",0L);
        map.put("fdqokok",0L);

        System.out.println((char)('Z' + 1));

        System.out.println(Main.getSortedMap("fuckalkq"));

    }
}

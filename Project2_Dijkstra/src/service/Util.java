package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Util {
    public static void main(String[] args) {
        ShowTime showTime = new ShowTime();
        String multiString = "芳华路\t张华浜\t春申路\t中华艺术宫\t上海马戏城\t海伦路\t芦恒路\t中华艺术宫\t江杨北路\t殷高东路\t提篮桥\t彭浦新村\t罗山路\t耀华路\t迪士尼";
        for (int i = 0; i < 1000000; i++) {
            //split(multiString);

/*            StringTokenizer stringTokenizer = new StringTokenizer(multiString);
            ArrayList<String> list = new ArrayList<>();
            while (stringTokenizer.hasMoreTokens()) {
                list.add(stringTokenizer.nextToken());
            }
            list.toArray();*/

            splitByIndexOf(multiString);
        }



        System.out.println(Arrays.toString(splitByIndexOf(multiString)));
        showTime.printTime("cost：");
    }

    public static String[] splitByTokenizer(String multiString) {
        StringTokenizer stringTokenizer = new StringTokenizer(multiString);
        ArrayList<String> list = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            list.add(stringTokenizer.nextToken());
        }
        return list.toArray(new String[0]);
    }

    public static String[] splitByIndexOf(String multiString) {
        ArrayList<String> list = new ArrayList<>();
        int index = multiString.indexOf("\t");
        while (index > -1) {
            list.add(multiString.substring(0,index));
            multiString=multiString.substring(index+1);
            index=multiString.indexOf("\t");
        }
        list.add(multiString);
        return list.toArray(new String[0]);
    }
}
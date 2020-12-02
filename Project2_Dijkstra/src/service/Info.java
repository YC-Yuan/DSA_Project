package service;

import org.apache.commons.compress.utils.FlushShieldFilterOutputStream;

import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Info {
    public static int mapSize = 325;
    public static HashMap<String,Integer> map = new HashMap<>();
    public static Station[] stations = new Station[mapSize];

    public static int [][] timeTable=new int[mapSize][mapSize];
    public static int [][] changeTable=new int[mapSize][mapSize];
    public static StringBuilder [][] pathTable=new StringBuilder[mapSize][mapSize];

    public static StringBuilder infoPath;
    public static int infoDistance;
    public static int infoChange;

    public static int multiTime;
    public static int multiChange;
    public static String multiStr;


    public static void query(String multiName){

        String[] stationNames = multiName.split("\\s+");

        int start;
        int end;
        int time = 0;
        int change=0;
        StringBuilder str=new StringBuilder();
        for (int i = 0; i < stationNames.length - 1; i++) {//两站两站寻找
            start = Info.map.get(stationNames[i]);
            end = Info.map.get(stationNames[i + 1]);
            if (pathTable[start][end]!=null){//已经算过了，直接拿来用
                time+=timeTable[start][end];
                change+=changeTable[start][end];
                str.append(pathTable[start][end]);
            }
            else{
                Dijkstra.run(start,end);
                time+=infoDistance;
                change+=infoChange;
                str.append(infoPath).append("|");
            }
            /*Dijkstra.run(start,end);
            time+=infoDistance;
            change+=infoChange;
            str.append(infoPath);*/
        }
        multiStr=str.toString();
        multiTime=time;
        multiChange=change;
    }
}

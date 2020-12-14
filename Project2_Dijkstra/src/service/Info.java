package service;

import java.util.HashMap;

public class Info {

    public static int mapSize = 324;
    public static HashMap<String,Integer> map = new HashMap<>();
    public static Station[] stations = new Station[mapSize];

    public static int[][] timeTable = new int[mapSize][mapSize];
    public static int[][] changeTable = new int[mapSize][mapSize];
    public static Path[][] pathTable = new Path[mapSize][mapSize];
    public static String[][] strTable = new String[mapSize][mapSize];

    public static Path infoPath;
    public static int infoLoadTime;
    public static int infoDistance;
    public static int infoChange;

    public static int multiTime;
    public static int multiChange;
    public static String multiStr;

    public static void query(String multiName) {
        String[] stationNames =Util.splitByTokenizer(multiName);

        int start;
        int end;
        int time = 0;
        int change = 0;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < stationNames.length - 1; i++) {//两站两站寻找
            start = Info.map.get(stationNames[i]);
            end = Info.map.get(stationNames[i + 1]);
            if (pathTable[start][end] != null) {//已经算过了，直接拿来用
                time += timeTable[start][end];
                change += changeTable[start][end];
                //不仅有可能算过，还有可能已经转化过，更要直接拿来用
                if (strTable[start][end] != null) {
                    str.append(strTable[start][end]).append("|");
                }
                else {
                    //要是真的没算过，就算一下然后存起来
                    StringBuilder tmpString = pathTable[start][end].toStringBuilder();
                    str.append(tmpString).append("|");
                    strTable[start][end] = tmpString.toString();
                }
            }
            else {//现算现用
                Dijkstra.run(start,end);
                time += infoDistance;
                change += infoChange;
                str.append(infoPath.toStringBuilder()).append("|");
            }
        }
        multiStr = str.toString();
        multiTime = time;
        multiChange = change;
    }
}

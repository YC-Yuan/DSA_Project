package service;

import java.util.HashMap;
import java.util.Vector;

public class StationInfo {
    public static int mapSize = 325;
    public static HashMap<String,Integer> map = new HashMap<>();
    public static Station[] stations = new Station[mapSize];

    public static String infoPath;
    public static int infoDistance;
    public static int infoChange;

    public static void updateMultiDistance(String multiPath) {
        String[] stationNames = multiPath.split("\\s+");
        int start;
        int end;
        double time = 0;
        for (int i = 0; i < stationNames.length - 1; i++) {
            start = StationInfo.map.get(stationNames[i]);
            end = StationInfo.map.get(stationNames[i + 1]);
            time += stations[start].paths[end].length;
        }
        System.out.println("time = " + time);
        infoDistance = (int)time;
        infoChange=(int)Math.round(time*100)%100;
    }
    public static void updateMultiPath(String multiPath) {
        String[] stationNames = multiPath.split("\\s+");
        if (stationNames.length <= 1) {
            infoPath = "站点数量错误";
            return;
        }
        int start = 0;
        int end = 0;

        Vector<Station> path = null;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < stationNames.length - 1; i++) {//多次中转，第i+1个中转对
            start = StationInfo.map.get(stationNames[i]);
            end = StationInfo.map.get(stationNames[i + 1]);
            path = stations[start].paths[end].path;
            for (int j = 0; j < path.size() - 1; j++) {//每次配对，输出路径
                result.append(path.get(j).name).append("-");//"这一站-"
                result.append(Path.getLineName(path.get(j),path.get(j + 1))).append("-");//"Line X-"
            }

        }
        result.append(stations[start].paths[end].path.get(path.size() - 1).name);

        infoPath = result.toString();
    }
}

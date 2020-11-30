import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public class StationInfo {
    public static int mapSize = 325;
    public static HashMap<String,Integer> map = new HashMap<>();
    public static Station[] stations = new Station[mapSize];

    public static String infoPath;
    public static int infoDistance;

    public static void printInfoPath() {//展示现在的路径
        System.out.println(infoPath);
    }

    ;

    public static void printInfoDistance() {//展示现在的时间
        System.out.println("用时：" + infoDistance + "分钟");
    }

    public static void getDistance(String startName,String endName) {//根据两站点修改信息
        int start = StationInfo.map.get(startName);
        int end = StationInfo.map.get(endName);
        infoDistance = (int) stations[start].paths[end].length;
    }

    public static void getMultiDistance(String multiPath) {
        String[] stationNames = multiPath.split("\\s+");
        if (stationNames.length <= 1) {
            infoPath = "至少要输入两个站点";
            return;
        }
        int start;
        int end;
        int time = 0;
        //StringBuilder result = new StringBuilder(stationNames[0]);
        for (int i = 0; i < stationNames.length - 1; i++) {
            start = StationInfo.map.get(stationNames[i]);
            end = StationInfo.map.get(stationNames[i + 1]);
            //System.out.println(stationNames[i] + "to" + stationNames[i + 1] + ",cost" + stations[start].paths[end].length);
            time += stations[start].paths[end].length;
            //result.append("->").append(stationNames[i + 1]);
        }
        //result.append(",用时").append(time).append("分钟");
        infoDistance = time;
    }

    public static void getPath(String startName,String endName) {
        int start = StationInfo.map.get(startName);
        int end = StationInfo.map.get(endName);

        Vector<Station> path = stations[start].paths[end].path;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < path.size() - 1; i++) {
            result.append(path.get(i).name).append("-");
            result.append(Path.getLineName(path.get(i),path.get(i + 1))).append("-");
        }
        result.append(path.get(path.size() - 1).name);
        infoPath = result.toString();
    }

    public static String getMultiPath(String multiPath) {
        String[] stationNames = multiPath.split("\\s+");
        if (stationNames.length <= 1) return "站点数量错误";
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

        return result.toString();
    }
}

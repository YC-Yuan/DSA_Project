import java.util.HashMap;
import java.util.Vector;

public class StationInfo {
    public static int mapSize=325;
    public static HashMap<String, Integer> map = new HashMap<>();
    public static Station[] stations = new Station[mapSize];

    public static String getDistance(int start,int end){
        Path path = stations[start].paths[end];
        return ("from "+start+" to "+end+" length:" + (int)path.length);
    }

    public static String getPath(int start,int end){
        Vector<Station> path=stations[start].paths[end].path;
        StringBuilder result= new StringBuilder();
        for (int i=0;i<path.size()-1;i++){
            result.append(path.get(i).name).append("-");
            result.append(Path.getLineName(path.get(i),path.get(i+1))).append("-");
        }
        result.append(path.get(path.size()-1).name);
        return result.toString();
    }
}

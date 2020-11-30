import java.util.HashSet;

public class Station {
    public String name;
    public Path[] paths;//到各个站点的路径

    public HashSet<String> line;

    public Station(String name,String line) {
        this.name = name;
        this.line = new HashSet<String>();
        this.line.add(line);
        this.paths = new Path[StationInfo.mapSize];
        for (int i = 0; i < StationInfo.mapSize; i++) {
            this.paths[i] = new Path(this);
        }
    }
}
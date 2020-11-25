import java.util.Vector;

public class Station {
    public String name;
    public Path[] paths;//到各个站点的路径

    public Vector<String> line;

    public Station(String name,String line) {
        this.name = name;
        this.line = new Vector<String>();
        this.line.add(line);
        this.paths = new Path[StationInfo.mapSize];
        for (int i = 0; i < StationInfo.mapSize; i++) {
            this.paths[i] = new Path(this);
        }
    }
}
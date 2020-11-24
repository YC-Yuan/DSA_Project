import java.util.Vector;

public class Station {
    public String name;
    public Path[] paths;//到312个站点的路径

    public Vector<String> line;

    public Station(String name,String line) {
        this.name = name;
        this.line = new Vector<String>();
        this.line.add(line);
        this.paths = new Path[312];
        for (int i = 0; i < 312; i++) {
            this.paths[i] = new Path(this);
        }
    }
}
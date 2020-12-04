package service;

import java.util.HashSet;
import java.util.Vector;

public class Path {
    public Vector<Station> path;

    public Path(Vector<Station> path){
        this.path=path;
    }

    public StringBuilder toStringBuilder() {
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < path.size() - 1; j++) {//每次配对，输出路径
            result.append(path.get(j).name).append("-Line");//"这一站-"
            result.append(getLineName(path.get(j),path.get(j + 1))).append("-");//"Line X-"
        }
        result.append(path.get(path.size() - 1).name);
        return result;
    }

    public static Integer getLineName(Station start,Station end) {
        HashSet<Integer> lineA = new HashSet<>(start.line);
        for (Integer line : end.line) {
            if (lineA.contains(line)) return line;
        }
        return 0;
    }
}

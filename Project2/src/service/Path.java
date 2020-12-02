package service;

import java.util.HashSet;
import java.util.Vector;

public class Path {
    public double length = 10000;
    public Vector<Station> path = new Vector<>();

    public Path(Station self) {
        path.add(self);
    }

    public void init(Station first,double length) {
        if (path.size() == 1) {
            this.length = length;
            path.add(first);
        }

    }

    public static void updatePath(Path old,Path newA,Path newB) {
        if (newA.length + newB.length < old.length) {
            old.length = newA.length + newB.length;
            //A和B路径的最小节点数为2(起点终点)
            old.path = new Vector<>();
            for (int i = 0; i < newA.path.size() - 1; i++) {//跳过A末复制路径
                old.path.add(newA.path.get(i));
            }
            //如果A末与B初线路（取决于与前一节点的共同部分）不同，则添加B的首节点（AB共同节点，换乘站）
            if (!isSameLine(newA,newB)) {
                old.path.add(newB.path.get(0));
                //length的小数部分记录换乘
                old.length += 0.01;
            }
            //不论是否发生换乘，后面的路径都需要添加
            for (int i = 1; i < newB.path.size(); i++) {
                old.path.add(newB.path.get(i));
            }

        }
    }

    public static boolean isSameLine(Path a,Path b) {

        HashSet<String> tail = getPathLineTail(a);
        HashSet<String> head = getPathLineHead(b);

        tail.retainAll(head);//取交集
        boolean result = tail.isEmpty();

        return !result;//是否有相同元素(线路)
    }

    public static HashSet<String> getPathLineTail(Path a) {//找到一个线路下车时应乘的线
        if (a.path.size() == 1) return new HashSet<>(a.path.get(0).line);
        else {
            Vector<Station> path = a.path;
            HashSet<String> currentSet = new HashSet<>(path.get(path.size() - 1).line);
            HashSet<String> saveSet;
            for (int i = path.size() - 2; i >= 0; i--) {
                saveSet = new HashSet<>(currentSet);
                currentSet.retainAll(path.get(i).line);
                if (currentSet.isEmpty()) return saveSet;
            }
            return currentSet;
        }
    }

    public static HashSet<String> getPathLineHead(Path a) {//找到一个线路上车时对应先
        if (a.path.size() == 1) return new HashSet<>(a.path.get(0).line);
        else {
            Vector<Station> path = a.path;
            HashSet<String> currentSet = new HashSet<>(path.get(0).line);
            HashSet<String> saveSet;
            for (int i = 1; i < path.size(); i++) {
                saveSet = new HashSet<>(currentSet);
                currentSet.retainAll(path.get(i).line);
                if (currentSet.isEmpty()) return saveSet;
            }
            return currentSet;
        }
    }

    public static String getLineName(Station start,Station end) {
        HashSet<String> lineA = new HashSet<>(start.line);

        for (String line : end.line) {
            if (lineA.contains(line)) return line;
        }
        return "line wrong";
    }
}

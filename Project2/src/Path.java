import java.util.HashSet;
import java.util.Vector;

public class Path {
    public int length = Integer.MAX_VALUE;
    public Vector<Station> path = new Vector<>();

    public Path(Station self) {
        this.path.add(self);
    }

    public static void updatePath(Path old, Path newA, Path newB) {
        if (newA.length==Integer.MAX_VALUE||newB.length==Integer.MAX_VALUE) return;
        if (newA.length + newB.length < old.length) {//找到了更小的做法
//            System.out.println("------merge newA and newB------");
//            for (Station station:newA.path
//                 ) {
//                System.out.print(station.name+station.line);
//            }
//            System.out.println();
//            for (Station station:newB.path
//                 ) {
//                System.out.print(station.name+station.line);
//            }
//            System.out.println();


            old.length = newA.length + newB.length;
            //A和B路径的最小节点数为2(起点终点)
            old.path = new Vector<>();
            for (int i = 0; i < newA.path.size() - 1; i++) {//跳过A末复制路径
                old.path.add(newA.path.get(i));
            }
            //如果A末与B初线路（取决于与前一节点的共同部分）不同，则添加B的首节点（AB共同节点，换乘站）
            if (!isSameLine(newA, newB)) old.path.add(newB.path.get(0));
            //不论是否发生换乘，后面的路径都需要添加
            for (int i = 1; i < newB.path.size(); i++) {
                old.path.add(newB.path.get(i));
            }

//            for (Station station:old.path
//            ) {
//                System.out.print(station.name+station.line);
//            }
//            System.out.println();

        }
    }

    public static boolean isSameLine(Path a, Path b) {
        Station pre = a.path.get(a.path.size() - 2);//倒数第二个
        Station cur = a.path.get(a.path.size() - 1);//最后一个
        Station next = b.path.get(1);//第二个

        HashSet<String> lineA = new HashSet<>(pre.line);
        HashSet<String> lineB = new HashSet<>(next.line);

        for (String line : cur.line
        ) {
            if (lineA.contains(line) && lineB.contains(line)) return true;
        }
        return false;
    }

    public  static String getLineName(Station start,Station end){
        HashSet<String> lineA=new HashSet<>(start.line);
        for (String line:end.line){
            if (lineA.contains(line)) return line;
        }
        return "line wrong";
    }
}

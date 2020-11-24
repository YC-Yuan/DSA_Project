import java.util.Vector;

public class Path {
    public int length = Integer.MAX_VALUE;
    public Vector<Station> path = new Vector<>();

    public Path(Station self) {
        this.path.add(self);
    }

    public static void updatePath(Path old,Path newA,Path newB) {
        if (newA.length + newB.length < old.length) {//找到了更小的做法
            old.length = newA.length + newB.length;
            //A和B路径的最小节点数为2(起点终点)
            old.path = new Vector<>();
            for (int i = 0; i < newA.length - 1; i++) {//跳过A末复制路径
                old.path.add(newA.path.get(i));
            }
            //如果A末与B初线路（取决于与前一节点的共同部分）不同，则添加B的首节点（AB共同节点，换乘站）
            if (!isSameLine(newA,newB)) old.path.add(newB.path.get(0));
            //不论是否发生换乘，后面的路径都需要添加
            for (int i = 1; i < newB.length; i++) {
                old.path.add(newB.path.get(i));
            }
        }
    }

    public static boolean isSameLine(Path a,Path b) {
        return true;
    }
}

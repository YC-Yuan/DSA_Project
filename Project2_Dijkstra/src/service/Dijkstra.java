package service;

import org.apache.commons.collections4.IterableMap;
import org.apache.poi.hslf.dev.TextStyleListing;
import org.junit.Test;

import javax.jnlp.SingleInstanceListener;
import java.util.HashSet;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Vector;

public class Dijkstra {
    public static void run(int startIndex,int endIndex) {

        //初始化，所有站点距离标记为10000，起点站标记为0
        for (Station station : Info.stations
        ) {
            station.distance = 1000000;
            station.isConsidered = false;
            //path完全在运算时获取，无需init
        }
        Info.stations[startIndex].path = new Vector<>();
        Info.stations[startIndex].path.add(Info.stations[startIndex]);
        Info.stations[startIndex].distance = 0;

        Info.stations[startIndex].currentLine = Info.stations[startIndex].line;//从自己出发，爱乘啥乘啥

        //根据startIndex运行dijkstra
        PriorityQueue<Station> queue = new PriorityQueue<>();
        queue.add(Info.stations[startIndex]);
        Station current, neighbor;

        while (!queue.isEmpty()) {
            do {
                current = queue.poll();
            } while (Objects.requireNonNull(current).isConsidered);
            current.isConsidered = true;

            //对于正在考虑的节点：
            //1.对所有相邻节点做更新
            for (int i = 0; i < current.neighborStation.size(); i++) {//对所有邻居
                neighbor = current.neighborStation.get(i);
                if (current.distance + current.neighborTime.get(i) < neighbor.distance) {
                    neighbor.distance = current.distance + current.neighborTime.get(i);//更新时间

                    neighbor.path = new Vector<>(current.path);//先复制之前的路

                    HashSet<Integer> tmpLine = new HashSet<>(current.currentLine);
                    tmpLine.retainAll(neighbor.line);//没换乘，但是收束了
                    neighbor.currentLine = tmpLine;//记录收束结果，如果换成会记为空，但没事，之后覆盖
                    if (tmpLine.isEmpty()) {//换乘了的情况，新节点肯定不是中间节点
                        neighbor.distance += 1;
                        neighbor.currentLine = new HashSet<>(neighbor.line);
                        neighbor.currentLine.retainAll(current.line);
                    }
                    else {//不换乘，删除中间节点
                        //判断current是否为中间节点，可以删除
                        /*if (neighbor.name.equals("宜山路")||neighbor.name.equals("漕溪路")) {
                            System.out.println("----------");
                            System.out.println("current:"+current.name+" neighbor:"+neighbor.name);
                            for (Station st : neighbor.path
                            ) {
                                System.out.print(st.name + " " + st.line + "|");
                            }
                            System.out.println();
                            System.out.print("currentLine"+current.currentLine);
                            System.out.println("updateLine:"+neighbor.currentLine);
                        }*/
                        if (neighbor.path.size() > 1) neighbor.path.remove(neighbor.path.size() - 1);
                        //不论如何，不换乘的新节点一定是中间节点了
                    }

                    //需要判断是否换乘了
                    /*if (isLineChanged(current,neighbor)) {//换乘了，+1
                        neighbor.distance += 1;
                        neighbor.currentLine = neighbor.line;
                        neighbor.isMiddleStation = false;
                    }
                    else {//没换乘，当前站在线路中间，则要从路径中删除
                        neighbor.isMiddleStation = true;//不换乘肯定在中间
                        if (current.isMiddleStation) {
                            neighbor.path.remove(neighbor.path.size() - 1);
                        }
                        //不换乘，收束当前线路的站点
                        neighbor.currentLine = new HashSet<>(current.currentLine);
                        neighbor.currentLine.retainAll(neighbor.line);
                    }*/

                    //不论换不换乘，总要加入新的站
                    neighbor.path.add(neighbor);
                }
                //2.如果相邻节点没被考虑过，就放进队列
                if (!neighbor.isConsidered) queue.add(neighbor);
            }
        }

        //这时候获得了start到所有点的最短路径和距离，更新结果与缓存表
        int tmpDistance;
        for (int i = 0; i < Info.mapSize; i++) {
            tmpDistance = Math.round(Info.stations[i].distance);
            Info.changeTable[startIndex][i] = tmpDistance % 100;
            Info.timeTable[startIndex][i] = tmpDistance / 100;
            Info.pathTable[startIndex][i] = new Path(Info.stations[i].path);
        }
        Info.infoChange = Info.changeTable[startIndex][endIndex];
        Info.infoDistance = Info.timeTable[startIndex][endIndex];
        Info.infoPath = Info.pathTable[startIndex][endIndex];
    }


    //考虑从前一个点正在走的路径，到下一个点是否有换乘
    public static boolean isLineChanged(Station current,Station next) {
        HashSet<Integer> currentLine;
        currentLine = new HashSet<>(current.currentLine);
        //判断currentLine与next有无交集
        currentLine.retainAll(next.line);
        return currentLine.isEmpty();
    }
}

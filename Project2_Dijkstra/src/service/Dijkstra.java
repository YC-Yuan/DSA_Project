package service;

import org.apache.commons.collections4.IterableMap;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Vector;

public class Dijkstra {
    public static void run(int startIndex,int endIndex) {

        //初始化，所有站点距离标记为10000，起点站标记为0
        for (Station station : Info.stations
        ) {
            station.distance = 10000;
            station.path.add(station);
            station.isConsidered = false;
        }
        Info.stations[startIndex].path = new Vector<>();
        Info.stations[startIndex].path.add(Info.stations[startIndex]);
        Info.stations[startIndex].distance = 0;

        //根据startIndex运行dijkstra
        PriorityQueue<Station> queue = new PriorityQueue<>();
        queue.add(Info.stations[startIndex]);
        Station current, neighbor;
        while (!queue.isEmpty()) {
            current = queue.poll();
            current.isConsidered = true;
            //对于正在考虑的节点：
            //1.对所有相邻节点做更新
            for (int i = 0; i < current.neighborStation.size(); i++) {
                neighbor = current.neighborStation.get(i);
                if (current.distance + current.neighborTime.get(i) < neighbor.distance) {
                    neighbor.distance = current.distance + current.neighborTime.get(i);//更新时间
                    neighbor.path = new Vector<>(current.path);//先复制之前的路

                    //需要判断是否换乘了
                    if (isLineChanged(current,neighbor)) {//换乘了，+0.01
                        neighbor.distance += 0.01;
                    }
                    else {//没换乘,单站就无所谓了，如果是多个站需要删掉中间点
                        if (neighbor.path.size() > 1) neighbor.path.remove(neighbor.path.size() - 1);
                    }
                    //不论换不换乘，总要加入新的站
                    neighbor.path.add(neighbor);


                }
                //2.如果相邻节点没被考虑过，就放进队列
                if (!neighbor.isConsidered) queue.add(neighbor);
            }
        }

        //这时候获得了start到所有点的最短路径和距离，更新结果与缓存表
        int tmpDistance=Math.round(Info.stations[endIndex].distance * 100);

        Info.infoDistance=tmpDistance / 100;
        Info.infoChange=tmpDistance % 100;
        Info.infoPath=toStringBuilder(Info.stations[endIndex].path);

        /*for (int i = 0; i < Info.mapSize; i++) {
            tmpDistance = Math.round(Info.stations[i].distance * 100);
            Info.changeTable[startIndex][i] = tmpDistance % 100;
            Info.timeTable[startIndex][i] = tmpDistance / 100;
            Info.pathTable[startIndex][i] = toStringBuilder(Info.stations[i].path);
        }
        Info.infoChange = Info.changeTable[startIndex][endIndex];
        Info.infoDistance = Info.timeTable[startIndex][endIndex];
        Info.infoPath = Info.pathTable[startIndex][endIndex];*/
    }


    //考虑从前一个点正在走的路径，到下一个点是否有换乘
    public static boolean isLineChanged(Station current,Station next) {

        HashSet<String> currentLine;
        if (current.path.size() == 1) currentLine = new HashSet<>(current.line);
        else {
            Vector<Station> path = current.path;
            HashSet<String> currentSet = new HashSet<>(path.get(path.size() - 1).line);//最后一站的线路
            HashSet<String> saveSet=new HashSet<>();
            for (int i = path.size() - 2; i >= 0; i--) {//一个一个往前推，保留路径
                saveSet = new HashSet<>(currentSet);
                currentSet.retainAll(path.get(i).line);
                if (currentSet.isEmpty()) { break; }
            }
            if (currentSet.isEmpty()) currentLine=saveSet;
            else currentLine=currentSet;
        }
        //判断currentLine与next有无交集
        currentLine.retainAll(next.line);

        return currentLine.isEmpty();
    }

    public static StringBuilder toStringBuilder(Vector<Station> path) {
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < path.size() - 1; j++) {//每次配对，输出路径
            result.append(path.get(j).name).append("-");//"这一站-"
            result.append(getLineName(path.get(j),path.get(j + 1))).append("-");//"Line X-"
        }
        result.append(path.get(path.size()-1).name);
        return result;
    }

    public static String getLineName(Station start,Station end) {
        HashSet<String> lineA = new HashSet<>(start.line);

        for (String line : end.line) {
            if (lineA.contains(line)) return line;
        }
        return "line wrong";
    }
}

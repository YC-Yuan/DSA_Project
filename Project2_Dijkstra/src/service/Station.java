package service;

import com.sun.javafx.image.IntPixelGetter;

import java.util.HashSet;
import java.util.Vector;

public class Station implements Comparable<Station> {
    public String name;
    public int index;//站点编号
    public HashSet<Integer> line = new HashSet<>();
    public Vector<Station> neighborStation = new Vector<>();//按顺序存储相邻站点
    public Vector<Integer> neighborTime = new Vector<>();//存储相邻站点时间

    public int distance = 0;//到本次源头的距离，后两位表示换乘
    public HashSet<Integer> currentLine = new HashSet<>();//当前路径所走的线路
    public Vector<Station> path = new Vector<>();//到本次源头的路径
    public boolean isConsidered = false;

    public Station(String name,int index) {
        this.name = name;
        this.index = index;
        path.add(this);
    }

    @Override
    public int compareTo(Station station) {
        return Integer.compare(this.distance,station.distance);
    }
}

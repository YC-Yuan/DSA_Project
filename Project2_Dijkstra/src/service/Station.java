package service;

import java.util.HashSet;
import java.util.Vector;

public class Station implements Comparable<Station> {
    public String name;
    public int index;//站点编号
    public HashSet<String> line = new HashSet<>();
    public Vector<Station> neighborStation = new Vector<>();//按顺序存储相邻站点
    public Vector<Integer> neighborTime = new Vector<>();//存储相邻站点时间

    public float distance = 0;//到本次源头的距离
    public Vector<Station> path = new Vector<>();//到本次源头的路径
    public boolean isConsidered = false;

    public Station(String name,int index) {
        this.name = name;
        this.index = index;
        path.add(this);
    }

    @Override
    public int compareTo(Station station) {
        return Float.compare(this.distance,station.distance);
    }
}

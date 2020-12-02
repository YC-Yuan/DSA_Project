package service;

import java.util.Vector;

public class Station implements Comparable {
    public String name;
    public int index;//站点编号
    public Vector<String> line = new Vector<>();
    public Vector<Station> neighborStation = new Vector<>();//按顺序存储相邻站点
    public Vector<Integer> neighborTime = new Vector<>();//存储相邻站点时间

    public int distance = 0;//到本次源头的距离
    public Vector<Station> path = new Vector<>();//到本次源头的路径

    public Station(String name, int index) {
        this.name = name;
        this.index = index;
    }

    @Override
    public int compareTo(Object o) {
        Station station = (Station) o;
        return Integer.compare(this.distance, station.distance);
    }
}

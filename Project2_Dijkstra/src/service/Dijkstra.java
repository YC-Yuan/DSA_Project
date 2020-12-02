package service;

public class Dijkstra {
    public static void run(String startName,String endName){
        //把name转换成index，找到站点
        Integer startIndex = StationInfo.map.get(startName);
        Integer endIndex = StationInfo.map.get(endName);

        //根据startIndex运行dijkstra


        //更新结果与缓存表
    }
}

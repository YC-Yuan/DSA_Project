public class Floyd {
    public static void run() {
        ShowTime showTime=new ShowTime();

        //初始化，每个结点到自己的距离为0
        for (int i=0;i<312;i++){
            StationInfo.stations[i].paths[i].length=0;
        }


        for (int i = 0; i < 312; i++) {//在i轮纳入0号station
            for (int start = 0; start < 312; start++) {
                for (int end = 0; end < 312; end++) {//迭代更新从start到end站点间的路径
                    //如果start->i+i->end比start->end更小，则更新路线，否则什么也不做
                    //路线更新为：先从start到i，再从i到end，如果前后无换乘，则省略更新点
                    Path.updatePath(StationInfo.stations[start].paths[end],
                            StationInfo.stations[start].paths[i],
                            StationInfo.stations[i].paths[end]);
                }
            }
        }

        showTime.printTime("floyd costs:");
    }
}

package service;

import java.io.*;

public class TxtRead {
    //这个类从txt中读取信息，速度最快
    //跑完时stations与map加载完成

    public static void read() throws IOException {
        String preName, curName;
        int preMin, curMin;
        int index = 0;//该站点总标号
        int preIndex, curIndex;

        for (int i = 1; i < 20; i++) {//对19个TXT
            File txtFile = new File("info\\timetable\\" + i + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile)));

            String message = reader.readLine();
            String[] info = message.split("\\t");

            int lineName = getLine(i);//根据sheetIndex拿到线路
            preName = info[0];
            preMin = Integer.parseInt(info[1].substring(info[1].lastIndexOf(':') + 1));

            //创建起点站,需要检测站点存在与否
            if (!Info.map.containsKey(preName)) {//起点站未重复，创建！
                Info.map.put(preName,index);
                Info.stations[index] = new Station(preName,index);//创建当前站点
                Info.stations[index].line.add(lineName);
                preIndex = index++;
            }
            else {
                preIndex = Info.map.get(preName);//起点站已存在，获取index,增加换乘信息
                Info.stations[preIndex].line.add(lineName);
            }
            //遍历一条线，载入后面的站
            while ((message = reader.readLine()) != null) {
                info = message.split("\\t");
                curName = info[0];
                curMin = Integer.parseInt(info[1].substring(info[1].lastIndexOf(':') + 1));
                curIndex = index;
                //相邻站点名称、时间、标号获取完毕
                if (!Info.map.containsKey(curName)) {//对尚不存在的站点
                    Info.map.put(curName,index);
                    Info.stations[index] = new Station(curName,index);//创建当前站点
                    Info.stations[index++].line.add(lineName);
                }
                else {
                    curIndex = Info.map.get(curName);
                    Info.stations[curIndex].line.add(lineName);
                }
                //根据站点Index和时间差，赋予二者联系
                int distance = getTimeDistance(preMin,curMin);
                Info.stations[preIndex].neighborStation.add(Info.stations[curIndex]);
                Info.stations[preIndex].neighborTime.add(distance);
                Info.stations[curIndex].neighborStation.add(Info.stations[preIndex]);
                Info.stations[curIndex].neighborTime.add(distance);
                //更新信息
                preMin = curMin;
                preIndex = curIndex;
            }
        }
    }

    public static int getLine(int index) {
        switch (index) {
            case 14:
            case 15:
                return 10;
            case 18:
            case 19:
                return 11;
            default:
                return index;
        }
    }

    private static int getTimeDistance(int preMin,int curMin) {
        int distance = curMin - preMin;
        return distance < 0 ? 100 * (distance + 60) : 100 * distance;
    }
}

package service;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ReadExcel {
    public static void readExcel() throws IOException {
        File file = new File("info\\Timetable.xlsx");
        InputStream inputStream = new FileInputStream(file);
        Workbook wb = new XSSFWorkbook(inputStream);// 解析xlsx格式

        String preName, curName;
        int preMin, curMin;
        int index = 0;//该站点总标号
        int preIndex, curIndex;

        for (int i = 0; i < wb.getNumberOfSheets(); i++) {//对每张表
            Sheet sheet = wb.getSheetAt(i);//index为i的工作表
            String lineName = sheet.getSheetName();//站点名即为表名

            preName = sheet.getRow(1).getCell(0).toString();//获取第一站
            preMin = sheet.getRow(1).getCell(1).getLocalDateTimeCellValue().getMinute();
            //创建起点站,需要检测站点存在与否
            if (!StationInfo.map.containsKey(preName)) {//起点站未重复，创建！
                StationInfo.map.put(preName, index);
                StationInfo.stations[index] = new Station(preName, lineName);//创建当前站点
                preIndex = index++;
            } else {
                preIndex = StationInfo.map.get(preName);//起点站已存在，获取index,增加换乘信息
                StationInfo.stations[preIndex].line.add(lineName);
            }

            //遍历一条线，载入后面的站
            for (int row = 2; row <= sheet.getLastRowNum(); row++) {
                curName = sheet.getRow(row).getCell(0).toString();
                curMin = sheet.getRow(row).getCell(1).getLocalDateTimeCellValue().getMinute();
                curIndex = index;
                //相邻站点名称、时间、标号获取完毕
                if (!StationInfo.map.containsKey(curName)) {//对尚不存在的站点
                    StationInfo.map.put(curName, index);
                    StationInfo.stations[index++] = new Station(curName, lineName);//创建当前站点
                } else {
                    curIndex = StationInfo.map.get(curName);
                    StationInfo.stations[curIndex].line.add(lineName);
                }
                //根据站点Index和时间差，赋予二者联系
                int distance = getTimeDistance(preMin, curMin);
                StationInfo.stations[preIndex].paths[curIndex].init(StationInfo.stations[curIndex], distance);
                StationInfo.stations[curIndex].paths[preIndex].init(StationInfo.stations[preIndex], distance);
                //更新信息
                preMin = curMin;
                preIndex = curIndex;
            }
        }
        inputStream.close();
    }

    private static int getTimeDistance(int preMin, int curMin) {
        int distance = curMin - preMin;
        return distance < 0 ? distance + 60 : distance;
    }
}
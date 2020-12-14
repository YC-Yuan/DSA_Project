package service;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class PoiRead {
    //这个类用Poi包读取文件，速度慢

    public static void readExcel() throws IOException {
        File file = new File("info\\Timetable.xlsx");
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        Workbook wb = new XSSFWorkbook(inputStream);// 解析xlsx格式

        String preName, curName;
        int preMin, curMin;
        int index = 0;//该站点总标号
        int preIndex, curIndex;

        for (int i = 0; i < wb.getNumberOfSheets(); i++) {//对每张表
            Sheet sheet = wb.getSheetAt(i);//index为i的工作表
            int lineName = getLine(i);//根据sheetIndex拿到线路

            preName = sheet.getRow(1).getCell(0).toString();//获取第一站
            preMin = sheet.getRow(1).getCell(1).getLocalDateTimeCellValue().getMinute();
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
            for (int row = 2; row <= sheet.getLastRowNum(); row++) {
                curName = sheet.getRow(row).getCell(0).toString();
                curMin = sheet.getRow(row).getCell(1).getLocalDateTimeCellValue().getMinute();
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
        inputStream.close();
    }

    private static int getLine(int sheetIndex) {
        switch (sheetIndex) {
            case 13:
            case 14:
                return 10;
            case 17:
            case 18:
                return 11;
            default:
                return sheetIndex + 1;
        }
    }


    private static int getTimeDistance(int preMin,int curMin) {
        int distance = curMin - preMin;
        return distance < 0 ? 100*(distance + 60) : 100*distance;
    }
}
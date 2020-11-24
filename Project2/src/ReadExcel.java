import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ReadExcel {
    public static void main(String[] args) throws IOException {
        readExcel();
    }


    public static void readExcel() throws IOException {
        File file = new File("C:\\Users\\AAA\\Desktop\\DSA_Repository\\DSA_Project\\Project2\\info\\Timetable.xlsx");
        InputStream inputStream = new FileInputStream(file);
        Workbook wb = new XSSFWorkbook(inputStream);// 解析xlsx格式

        String preName, curName;
        int preMin, curMin;
        int index = 0;//该站点总标号
        int preIndex = 0, curIndex;

        for (int i = 0; i < 17; i++) {//对每张表
            Sheet sheet = wb.getSheetAt(i);// index为i的工作表
            String lineName = sheet.getSheetName();

            preName = sheet.getRow(1).getCell(0).toString();
            preMin = sheet.getRow(1).getCell(1).getLocalDateTimeCellValue().getMinute();
            //创建首个站点
            StationInfo.stations[index++] = new Station(preName, lineName);

            for (int row = 2; row < sheet.getLastRowNum(); row++) {
                curName = sheet.getRow(row).getCell(0).toString();
                curMin = sheet.getRow(row).getCell(1).getLocalDateTimeCellValue().getMinute();
                curIndex = index;
                //相邻站点名称、时间、标号获取完毕
                if (!StationInfo.map.containsKey(curName)) {//对尚不存在的站点
                    StationInfo.map.put(curName, index);
                    StationInfo.stations[index++] = new Station(curName, lineName);//创建当前站点
                } else {
                    curIndex = StationInfo.map.get(curName);
                }
                //根据站点Index和时间差，赋予二者联系
                int distance = getTimeDistance(preMin, curMin);
                System.out.print("preIndex = " + preIndex);
                System.out.println(" curIndex = " + curIndex);
                StationInfo.stations[preIndex].paths[curIndex].path.add(StationInfo.stations[curIndex]);
                StationInfo.stations[preIndex].paths[curIndex].length = distance;
                StationInfo.stations[curIndex].paths[preIndex].path.add(StationInfo.stations[preIndex]);
                StationInfo.stations[curIndex].paths[preIndex].length = distance;


                //更新信息
                preMin = curMin;
                preIndex = curIndex;
            }
        }
        System.out.println("there are " + StationInfo.map.size() + " stations in all");
        inputStream.close();
    }

    private static int getTimeDistance(int preMin, int curMin) {
        int distance = curMin - preMin;
        return distance < 0 ? distance + 60 : distance;
    }
}
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.*;
import java.util.Vector;

public class ReadExcel {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\AAA\\Desktop\\DSA_Repository\\DSA_Project\\Project2\\info\\Timetable.xlsx");

        InputStream inputStream = new FileInputStream(file);
        Workbook wb = new XSSFWorkbook(inputStream);// 解析xlsx格式

        Vector<String> stations = new Vector<>();
        Vector<String> times = new Vector<>();

        for (int i = 0; i < 17; i++) {//对每张表
            Sheet sheet = wb.getSheetAt(i);// index为i的工作表

            int lastRowIndex = sheet.getLastRowNum();

            System.out.println("sheetIndex:" + i);
            System.out.println("rowIndex:" + lastRowIndex);

            for (int row = 1; row < lastRowIndex; row++) {
                System.out.println("station name:" + sheet.getRow(row).getCell(0).toString());
                System.out.println("time:" + sheet.getRow(row).getCell(1).getLocalDateTimeCellValue().getMinute());
            }
        }
    }


    public ReadExcel() throws IOException {
    }
    //生成table并将其展示出来，下面的代码我就不写了
}
import jxl.read.biff.BiffException;
import service.*;

import java.io.*;

public class MyTest {
    public static void main(String[] args) throws IOException, BiffException {
        File file = new File("info\\performance.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));

        ShowTime showTime = new ShowTime();

        //PoiRead.readExcel();
        //JxlRead.readExcel();
        TxtRead.read();

        showTime.printTime("读取耗时：");

        /*String input = "曹杨路 虹桥2号航站楼";
        Info.query(input);

        System.out.println("路线：" + Info.multiStr);
        System.out.print("用时：" + Info.multiTime);
        System.out.println("  换乘数：" + Info.multiChange);*/

        String input;
        while ((input = br.readLine()) != null) {
            System.out.println(input);
            Info.query(input);
            System.out.println("路线：" + Info.multiStr);
            System.out.print("用时：" + Info.multiTime);
            System.out.println("  换乘数：" + Info.multiChange);
        }
        showTime.printTime("运行总耗时：");
    }
}

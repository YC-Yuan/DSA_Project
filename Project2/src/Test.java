import service.Floyd;
import service.ReadExcel;
import service.ShowTime;
import service.StationInfo;

import java.io.*;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        File file=new File("info\\performance.txt");
        FileInputStream fileInputStream=new FileInputStream(file);

        BufferedReader br=new BufferedReader(new InputStreamReader(fileInputStream));

        String input;


        ReadExcel.readExcel();
        ShowTime showTime=new ShowTime();
        Floyd.run();

        while((input=br.readLine())!=null){

            System.out.println(input);

            StationInfo.updateMultiPath(input);
            StationInfo.updateMultiDistance(input);

            System.out.println("路线：" + StationInfo.infoPath);
            //System.out.println("用时：" + StationInfo.infoDistance);
            //System.out.println("换乘数：" + StationInfo.infoChange);
        }
        showTime.printTime("运行总耗时：");

    }
}

import service.*;

import java.io.*;

public class MyTest {
    public static void main(String[] args) throws IOException {
        File file=new File("info\\performance.txt");
        FileInputStream fileInputStream=new FileInputStream(file);

        BufferedReader br=new BufferedReader(new InputStreamReader(fileInputStream));

        ReadExcel.readExcel();

        ShowTime showTime=new ShowTime();

        String input="合川路 滴水湖";
        Info.query(input);
        System.out.println("路线：" + Info.multiStr);
        System.out.print("用时：" + Info.multiTime);
        System.out.println("  换乘数：" + Info.multiChange);

        //String input;
        while((input=br.readLine())!=null){
            System.out.println(input);
            Info.query(input);
            System.out.println("路线：" + Info.multiStr);
            System.out.print("用时：" + Info.multiTime);
            System.out.println("  换乘数：" + Info.multiChange);
        }

        System.out.println("toString次数：" + Info.toStringCalled );
        showTime.printTime("运行总耗时：");
    }
}

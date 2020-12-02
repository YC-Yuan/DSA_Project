import service.*;

import java.io.*;

public class Test {
    public static void main(String[] args) throws IOException {
        File file=new File("info\\performance.txt");
        FileInputStream fileInputStream=new FileInputStream(file);

        BufferedReader br=new BufferedReader(new InputStreamReader(fileInputStream));

        String input;


        ReadExcel.readExcel();
        ShowTime showTime=new ShowTime();

        while((input=br.readLine())!=null){
            Info.query(input);
            System.out.println("路线：" + Info.infoPath);
            System.out.println("用时：" + Info.infoDistance);
            System.out.println("换乘数：" + Info.infoChange);
        }
        showTime.printTime("运行总耗时：");

    }
}

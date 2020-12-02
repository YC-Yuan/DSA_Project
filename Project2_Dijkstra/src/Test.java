import service.*;

import java.io.*;

public class Test {
    public static void main(String[] args) throws IOException {
        File file=new File("info\\performance.txt");
        FileInputStream fileInputStream=new FileInputStream(file);

        BufferedReader br=new BufferedReader(new InputStreamReader(fileInputStream));

        String input="虹口足球场  文井路  镇坪路  国权路  淀山湖大道  上大路  黄兴公园  交通大学  莘庄  文井路  星中路  青浦新城  上海火车站  铁力路  曹路  新闸路  嘉定西  白银路";


        ReadExcel.readExcel();

        ShowTime showTime=new ShowTime();


        while((input=br.readLine())!=null){
            System.out.println(input);
            Info.query(input);
            System.out.println("路线：" + Info.multiStr);
            System.out.println("用时：" + Info.multiTime);
            System.out.println("换乘数：" + Info.multiChange);
        }
        showTime.printTime("运行总耗时：");
    }
}

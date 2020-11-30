import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;

public class Control {
    public static void main(String[] args) throws IOException {
        ReadExcel.readExcel();
        Floyd.run();

        String startName="镇坪路";
        String endName="国权路";

        StationInfo.getPath(startName,endName);
        StationInfo.getDistance(startName,endName);
        StationInfo.printInfoPath();
        StationInfo.printInfoDistance();

        String multiName="虹口足球场  文井路  镇坪路  国权路  淀山湖大道  上大路  " +
                "黄兴公园  交通大学  莘庄  文井路  星中路  青浦新城  上海火车站  铁力路  " +
                "曹路  新闸路  嘉定西  白银路";
        System.out.println(multiName);
        StationInfo.getMultiPath(multiName);
        StationInfo.getMultiDistance(multiName);
        StationInfo.printInfoPath();
        StationInfo.printInfoDistance();
    }
}

import java.io.IOException;

public class Control {
    public static void main(String[] args) throws IOException {
        StationInfo.getMultiDistance("s");

        ReadExcel.readExcel();
        Floyd.run();

        int index=224;//龙溪路，10号线3汇点

        System.out.print(StationInfo.stations[index].name);
        System.out.println(StationInfo.stations[index].line);

        String startName="镇坪路";
        String endName="海伦路";

        StationInfo.getPath(startName,endName);
        StationInfo.getDistance(startName,endName);
        StationInfo.printInfoPath();
        StationInfo.printInfoDistance();

        String multiName="虹口足球场  文井路  镇坪路  国权路  淀山湖大道  上大路  " +
                "黄兴公园  交通大学  莘庄  文井路  星中路  青浦新城  上海火车站  铁力路  " +
                "曹路  新闸路  嘉定西  白银路";
        StationInfo.getMultiDistance(multiName);
        System.out.println(StationInfo.getMultiPath(multiName));
        StationInfo.printInfoDistance();
    }
}

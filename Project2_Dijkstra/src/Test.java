import service.ReadExcel;
import service.Station;
import service.StationInfo;

import java.io.IOException;

public class Test {
    public static void main(String [] args) throws IOException {
        ReadExcel.readExcel();

        for (Station station: StationInfo.stations) {
            System.out.println(station.name+station.line);
            for (int i=0;i<station.neighborTime.size();i++){
                System.out.println("到相邻站：" + station.neighborStation.get(i).line+station.neighborStation.get(i).name + "用时为：" + station.neighborTime.get(i));
            }
        }
    }
}

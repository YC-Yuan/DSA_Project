import java.io.IOException;

public class Control {
    public static void main(String[] args) throws IOException {
        ReadExcel.readExcel();
        Floyd.run();

        int index=224;//龙溪路，10号线3汇点

        System.out.print(StationInfo.stations[index].name);
        System.out.println(StationInfo.stations[index].line);

        int start=224;
        int end=235;

        System.out.println("start:"+StationInfo.stations[start].name);
        System.out.println("end:"+StationInfo.stations[end].name);
        System.out.println(StationInfo.getPath(start,end));
        System.out.println(StationInfo.getDistance(start,end));
    }
}

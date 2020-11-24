import java.io.IOException;

public class Control {
    public static void main(String[] args) throws IOException {
        ReadExcel.readExcel();
        Floyd.run();

        int start=209;
        int end=211;

        System.out.println("start:"+StationInfo.stations[start].name);
        System.out.println("end:"+StationInfo.stations[end].name);
        System.out.println(StationInfo.getPath(start,end));
        System.out.println(StationInfo.getDistance(start,end));
    }
}

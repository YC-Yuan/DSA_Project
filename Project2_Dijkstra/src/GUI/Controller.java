package GUI;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import service.*;

import java.io.*;

import static service.Info.*;

public class Controller {
    public TextField input;
    public TextArea output;
    public Button query;
    public Button performance;

    public static StringBuilder outputInfo = new StringBuilder();

    public void clickQuery(ActionEvent actionEvent) throws IOException {
        ShowTime showTime=new ShowTime();
        outputInfo=new StringBuilder();

        TxtRead.read();

        String multiName=input.getText();
        String[] stationNames = Util.splitByTokenizer(multiName);
        int start;
        int end;
        int time = 0;
        int change = 0;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < stationNames.length - 1; i++) {//两站两站寻找
            //需要获知，是否为合法输入
            Integer save=Info.map.get(stationNames[i]);
            if (save!=null) start= save;
            else {
                output.setText("站名错误，请检查检查并重新输入");
                return;
            }
            save=Info.map.get(stationNames[i + 1]);
            if (save!=null) end=save;
            else {
                output.setText("站名错误，请检查检查并重新输入");
                return;
            }
            if (pathTable[start][end] != null) {//已经算过了，直接拿来用
                time += timeTable[start][end];
                change += changeTable[start][end];
                //不仅有可能算过，还有可能已经转化过，更要直接拿来用
                if (strTable[start][end] != null) {
                    str.append(strTable[start][end]).append("|");
                }
                else {
                    //要是真的没算过，就算一下然后存起来
                    StringBuilder tmpString = pathTable[start][end].toStringBuilder();
                    str.append(tmpString).append("|");
                    strTable[start][end] = tmpString.toString();
                }
            }
            else {//现算现用
                Dijkstra.run(start,end);
                time += infoDistance;
                change += infoChange;
                str.append(infoPath.toStringBuilder()).append("|");
            }
            /*Dijkstra.run(start,end);
            time+=infoDistance;
            change+=infoChange;
            str.append(infoPath);*/
        }
        multiStr = str.toString();
        multiTime = time;
        multiChange = change;
        outputInfo.append("路线：").append(Info.multiStr).append("\n").append("用时：").append(Info.multiTime).append("  换乘数：").append(Info.multiChange).append("\n");
        outputInfo.append(showTime.getPrintTime("运行总耗时：")).append("  加载地图耗时：").append(infoLoadTime).append("mills");

        output.setText(outputInfo.toString());
    }

    public void clickTest(ActionEvent actionEvent) throws IOException {

        outputInfo=new StringBuilder();
        ShowTime showTime = new ShowTime();

        File file = new File("info\\performance.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));

        TxtRead.read();

        String input;
        while ((input = br.readLine()) != null) {
            Info.query(input);
            outputInfo.append("路线：").append(Info.multiStr).append("\n");
            outputInfo.append("用时：").append(Info.multiTime).append("  换乘数：").append(Info.multiChange).append("\n");
        }
        outputInfo.append(showTime.getPrintTime("运行总耗时：")).append("  加载地图耗时：").append(infoLoadTime).append("mills");

        output.setText(outputInfo.toString());
    }
}
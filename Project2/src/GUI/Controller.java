package GUI;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.omg.CosNaming.BindingIterator;
import service.ShowTime;
import service.StationInfo;

public class Controller {
    public TextField input;
    public TextArea output;
    public Button button;

    public void init() {

    }

    public void clickQuery(ActionEvent actionEvent) {
        String inputText = input.getText();

        String[] names = inputText.split("\\s+");
        boolean valid = true;
        for (String name : names
        ) {
            if (!StationInfo.map.containsKey(name)) {
                valid = false;
                break;
            }
        }
        if (valid) {
            StationInfo.updateMultiPath(inputText);
            StationInfo.updateMultiDistance(inputText);

            String result = "读取表格用时："+ ShowTime.readCost+"mills,算法计算用时："+ShowTime.floydCost+"mills";
            result += "\n" + StationInfo.infoPath;
            result += "\n" + "用时" + (int) StationInfo.infoDistance + "分钟";

            output.setText(result);
        } else {
            output.setText("非法输入，请确认站点名是否正确，且用空格分开");
        }
    }
}

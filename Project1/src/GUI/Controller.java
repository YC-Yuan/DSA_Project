package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.impl.YYCompressImpl;
import util.Info;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Controller {
    public Button buttonFile;
    public Button buttonFolder;
    public Button buttonDestination;
    public Button buttonCompress;
    public Button buttonDecompress;


    public Text textTitle;
    public Text textRatio;
    public Text textOriginalSize;
    public Text textCompressedSize;
    public Text textSpeed;
    public Text textTime;

    public AnchorPane rootPane;

    private String destinationPath = "";
    private String originalPath = "";

    private final String defaultOriginalSize = "Original Size:";
    private final String defaultTime = "Time consuming:";
    private final String defaultSpeed = "Average Speed:";
    private final String defaultCompressedSize = "Compressed Size:";
    private final String defaultRatio = "Compression Ratio:";

    @FXML
    public void clickDestination() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose compress/decompress destination");
        File destinationFolder = directoryChooser.showDialog(rootPane.getScene().getWindow());
        destinationPath = destinationFolder.getPath();
    }

    @FXML
    public void clickCompress() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("Alert");
        if (true) {//未选择压缩对象
            alert.headerTextProperty().set("Choose a file/folder before compress!");
            alert.showAndWait();
        }
        else if (true) {//未选择压缩目的地
            alert.headerTextProperty().set("Choose a destination before compress!");
            alert.showAndWait();
        }
        else {//执行压缩
            Info.init();
            //显示正在载入
            //禁用所有按钮
            YYCompressImpl yyCompress = new YYCompressImpl(destinationPath);
            yyCompress.compress(originalPath);
            //恢复按钮，更新资讯
            textOriginalSize.setText(defaultOriginalSize + Info.getOriginalSize() + "MB");
            textTime.setText(defaultTime + Info.getTime() + "s");
            textSpeed.setText(defaultSpeed + Info.getSpeed() + "MB/s");
            textCompressedSize.setText(defaultCompressedSize+Info.getCompressedSize()+"MB");
            textRatio.setText(defaultRatio+Info.getRatio()+"%");
        }
    }

    @FXML
    public void clickDecompress() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("Alert");
        if (true) {//未选择可解压文件
            alert.headerTextProperty().set("Choose a YYCFile/YYCPack before decompress!");
            alert.showAndWait();
        }
        else if (true) {
            alert.headerTextProperty().set("Choose a destination before compress!");
            alert.showAndWait();
        }
        else {//执行压缩

        }
    }

    public void clickFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file/folder");
        File file = fileChooser.showOpenDialog(rootPane.getScene().getWindow());
        originalPath = file.getPath();

    }

    public void clickFolder(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose a folder");
        File folder = directoryChooser.showDialog(rootPane.getScene().getWindow());
        originalPath = folder.getPath();
    }
}

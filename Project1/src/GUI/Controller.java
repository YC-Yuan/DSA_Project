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
import java.text.DecimalFormat;
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

    private static final String defaultOriginalSize = "Original Size:";
    private static final String defaultTime = "Time consuming:";
    private static final String defaultSpeed = "Average Speed:";
    private static final String defaultCompressedSize = "Compressed Size:";
    private static final String defaultRatio = "Compression Ratio:";

    private static final DecimalFormat format = new DecimalFormat("######0.00");

    @FXML
    public void clickDestination() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose compress/decompress destination");
        File destinationFolder = directoryChooser.showDialog(rootPane.getScene().getWindow());
        if (destinationFolder != null) destinationPath = destinationFolder.getPath();
    }

    @FXML
    public void clickCompress() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("Alert");
        if (originalPath.equals("")) {//未选择压缩对象
            alert.headerTextProperty().set("Choose a file/folder before compress!");
            alert.showAndWait();
        } else if (destinationPath.equals("")) {//未选择压缩目的地
            alert.headerTextProperty().set("Choose a destination before compress!");
            alert.showAndWait();
        } else {//执行压缩
            Info.init();
            //显示正在载入
            rootPane.setDisable(true);
            System.out.println("set disable");
            textOriginalSize.setText(defaultCompressedSize + "Compressing, please wait!");
            //禁用所有按钮

            YYCompressImpl yyCompress = new YYCompressImpl(destinationPath);
            yyCompress.compress(originalPath);
            rootPane.setDisable(false);
            System.out.println("set able");

            //恢复按钮，更新资讯
            textOriginalSize.setText(defaultOriginalSize + format.format(Info.getOriginalSize()) + "MB");
            textTime.setText(defaultTime + format.format(Info.getTime()) + "s");
            textSpeed.setText(defaultSpeed + format.format(Info.getSpeed()) + "MB/s");
            textCompressedSize.setText(defaultCompressedSize + format.format(Info.getCompressedSize()) + "MB");
            textRatio.setText(defaultRatio + format.format(Info.getRatio()) + "%");


            //重置地址
            originalPath = "";
            destinationPath = "";
        }
    }

    @FXML
    public void clickDecompress() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("Alert");
        if (originalPath.equals("")) {//未选择可解压文件
            alert.headerTextProperty().set("Choose a YYCFile/YYCPack before decompress!");
            alert.showAndWait();
        } else if (destinationPath.equals("")) {
            alert.headerTextProperty().set("Choose a destination before decompress!");
            alert.showAndWait();
        } else {//执行压缩
            Info.init();
            //
        }
    }

    public void clickFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file/folder");
        File file = fileChooser.showOpenDialog(rootPane.getScene().getWindow());
        if (file != null) {
            originalPath = file.getPath();
        }
    }

    public void clickFolder(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose a folder");
        File folder = directoryChooser.showDialog(rootPane.getScene().getWindow());
        if (folder != null) {
            originalPath = folder.getPath();
        }
    }
}

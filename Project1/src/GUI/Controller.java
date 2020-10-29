package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class Controller {
    public Button buttonSource;
    public Button buttonDestination;
    public Button buttonCompress;
    public Button buttonDecompress;


    public Text textTitle;
    public Text textRatio;
    public Text textSize;
    public Text textSpeed;
    public Text textTime;
    public Text textOriSize;

    public AnchorPane rootPane;

    private String destinationPath;
    private String originalPath;

    @FXML
    public void clickDestination() {
        DirectoryChooser directoryChooser=new DirectoryChooser();
        directoryChooser.setTitle("Choose compress/decompress destination");
        File destinationFolder = directoryChooser.showDialog(rootPane.getScene().getWindow());
        destinationPath=destinationFolder.getPath();
    }

    @FXML
    public void clickCompress() {

    }

    @FXML
    public void clickDecompress() {

    }

    public void clickFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file/folder");
        //File originalFile = fileChooser.showOpenDialog(rootPane.getScene().getWindow());

        DirectoryChooser directoryChooser=new DirectoryChooser();
        directoryChooser.setTitle("Choose a folder");
        directoryChooser.showDialog(rootPane.getScene().getWindow());
    }
}

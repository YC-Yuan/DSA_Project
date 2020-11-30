package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MetroQueryyc.fxml"));
        primaryStage.setTitle("MetroQueryyc");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();

        ShowTime showTime=new ShowTime();
        ReadExcel.readExcel();
        ShowTime.readCost=showTime.getTime();

        showTime.updateTime();
        Floyd.run();
        ShowTime.floydCost=showTime.getTime();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        Application.Parameters params = getParameters();
        List<String> unnamedParams = params.getUnnamed();
        int width = Integer.parseInt(unnamedParams.get(0));
        int height = Integer.parseInt(unnamedParams.get(1));
        int bombs = Integer.parseInt(unnamedParams.get(2));
        primaryStage.setTitle("Minesweeper by Silyukov Maksim");
        primaryStage.setWidth(900);
        primaryStage.setHeight(800);
        InputStream iconStream = getClass().getResourceAsStream("/icon.png");
        Image icon = new Image(iconStream);
        primaryStage.getIcons().add(icon);


        /*GridPane grid = new GridPane();
        for (int y = 1; y <= height; y++) {
            for (int x = 1; x <= width; x++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cell.fxml"));
                ImageView a = loader.load();
                grid.add(a, x, y);
            }
        }*/
        //grid.setAlignment(Pos.CENTER);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cell.fxml"));
        loader.setLocation(getClass().getResource("/tools.fxml"));
        VBox vboxmain = new VBox();
        vboxmain.setAlignment(Pos.CENTER);
        vboxmain.setPadding(new Insets(10, 10, 10, 10));
        ToolBar tools = loader.load();
        vboxmain.getChildren().addAll(tools);
        Scene primaryScene = new Scene(vboxmain, Color.rgb(192, 192, 192));
        primaryStage.setScene(primaryScene);

        primaryStage.show();

    }



    public static void main(String[] args) {
        int w = 30;
        int h = 16;
        int b = 99;
        String[] args2 = new String[]{"10","9","10"};
        if(args.length != 0){
            w = Integer.parseInt(args[0]);
            h = Integer.parseInt(args[1]);
            b = Integer.parseInt(args[2]);
            args2[0] = args[0];
            args2[1] = args[1];
        }
        Application.launch(args2);


    }
}

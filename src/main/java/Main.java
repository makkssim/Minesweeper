
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
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


public  class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

        Application.Parameters params = getParameters();
        List<String> unnamedParams = getParameters().getUnnamed();
        System.out.println(unnamedParams);
        Integer width = Integer.parseInt(unnamedParams.get(0));
        Integer height = Integer.parseInt(unnamedParams.get(1));
        primaryStage.setTitle("Minesweeper by Silyukov Maksim");
        primaryStage.setWidth(900);
        primaryStage.setHeight(800);
        InputStream iconStream = getClass().getResourceAsStream("/icon.png");
        Image icon = new Image(iconStream);
        primaryStage.getIcons().add(icon);


        GridPane grid = new GridPane();
        for(int y = 1; y <= height; y++) {
            for(int x = 1; x <= width; x++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cell.fxml"));
                ImageView a =  loader.load();
                grid.add(a, x, y);
            }
        }
        grid.setAlignment(Pos.CENTER);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cell.fxml"));
        loader.setLocation(getClass().getResource("/tools.fxml"));
        VBox vboxmain = new VBox();
        vboxmain.setAlignment(Pos.CENTER);
        vboxmain.setPadding(new Insets(10, 10, 10, 10));
        ToolBar tools = loader.load();
        vboxmain.getChildren().addAll(tools, grid);
        Scene primaryScene = new Scene(vboxmain, Color.rgb(192,192,192));
        primaryStage.setScene(primaryScene);

        primaryStage.show();

    }
    public static void main(Integer w, Integer h, Integer b) {
        Grid mainGrid = new Grid(w,h,b);
        String[] args = new String[]{w.toString(),h.toString()};
        Application.launch(Main.class,args);


    }
}

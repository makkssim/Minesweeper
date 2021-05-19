
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.InputStream;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Minesweeper by Silyukov Maksim");
        primaryStage.setWidth(1057);
        primaryStage.setHeight(900);
        InputStream iconStream = getClass().getResourceAsStream("/icon.png");
        Image icon = new Image(iconStream);
        primaryStage.getIcons().add(icon);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cell.fxml"));
        loader.setLocation(getClass().getResource("/tools.fxml"));
        VBox vboxmain = new VBox();
        vboxmain.setAlignment(Pos.TOP_CENTER);
        ToolBar tools = loader.load();
        vboxmain.getChildren().addAll(tools);
        Scene primaryScene = new Scene(vboxmain, Color.rgb(192, 192, 192));
        primaryStage.setScene(primaryScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        Application.launch();
    }
}


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import java.io.InputStream;


public  class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.show();
        primaryStage.setTitle("Minesweeper by Silyukov Maksim");
        primaryStage.setWidth(900);
        primaryStage.setHeight(800);
        InputStream iconStream = getClass().getResourceAsStream("/icon.png");
        Image icon = new Image(iconStream);
        primaryStage.getIcons().add(icon);
        Font font = new Font("Arial", 25);


        VBox vboxmain = new VBox();
        vboxmain.setAlignment(Pos.CENTER);
        vboxmain.setPadding(new Insets(10, 10, 10, 10));
        ObservableList<String> modes = FXCollections.observableArrayList("Новичок(9х9, 10 мин)", "Любитель(16х16, 40 мин)", "Эксперт(16х30, 99 мин)", "Особый");
        ComboBox<String> cmbModes = new ComboBox<String>(modes);
        cmbModes.setValue("Сложность");
        cmbModes.setStyle("-fx-font: 25px \"Arial\";");
        Button btnStart = new Button("Start");
        btnStart.setFont(font);
        HBox hboxtop = new HBox(20,cmbModes, btnStart);
        hboxtop.setAlignment(Pos.CENTER);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        InputStream square = getClass().getResourceAsStream("/square.png");
        Image imageSquare = new Image(square);
        for(int y = 1; y <= 16; y++) {
            for(int x = 1; x <= 30; x++) {
                ImageView a = new ImageView(imageSquare);
                int finalX = x;
                int finalY = y;
                a.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        Grid h = new Grid();
                        h.openCell(finalX, finalY);
                    }
                });

                grid.add(a, x, y);
            }
        }
        Label lbl1 = new Label("Время:");
        Label lblTime = new Label("000");
        Label lbl2 = new Label("Мин осталось:");
        Label lblMines = new Label("099");
        lbl1.setFont(font);
        lbl2.setFont(font);
        lblTime.setFont(font);
        lblMines.setFont(font);
        HBox hboxbottom = new HBox(20,lbl1,lblTime,lbl2,lblMines);
        hboxbottom.setAlignment(Pos.CENTER);
        vboxmain.getChildren().addAll(hboxtop,grid,hboxbottom);
        vboxmain.setAlignment(Pos.CENTER);
        ScrollPane root = new ScrollPane(vboxmain);

        Scene primaryScene = new Scene(root, Color.rgb(192,192,192));

        primaryStage.setScene(primaryScene);

        primaryStage.show();


    }
    public static void main(String[] args) {
        Application.launch("30","20");
    }
}

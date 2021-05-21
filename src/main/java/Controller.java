import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.util.Duration;
import org.apache.commons.lang3.math.NumberUtils;

public class Controller {
    @FXML
    public Label lblBomb;
    @FXML
    private static GridPane gp;
    @FXML
    public Label lblTime;
    @FXML
    private ToolBar tb;
    @FXML
    private ComboBox CmbDif;
    @FXML
    private Button btnStart;
    @FXML
    private ImageView cl;
    @FXML
    private Integer w = 30;
    private Integer h = 16;
    private Integer b = 99;
    private Integer flags = 0;
    private boolean isGameStarted;
    Grid mainGrid ;
    private int time = 0;
    Timeline timeline = new Timeline(
            new KeyFrame(
                    Duration.millis(1000),
                    ae -> {
                        time++;
                        lblTime.setText(String.valueOf(time));
                    }
            )
    );

    public void startClick(MouseEvent mouseEvent) throws IOException {
        newScene(h, w);
    }

    public void cellClick(MouseEvent mouseEvent) {
        if (!isGameStarted) {
            timeline.setCycleCount(999);
            timeline.play();
            isGameStarted = true;
        }

        int cellH = (int) mouseEvent.getY() / 26;
        int cellW = (int) (mouseEvent.getX() - (1040 - this.w * 26) / 2) / 26;

        if (cellH < h && cellW < w) {
            GridPane gp = (GridPane) mouseEvent.getSource();
            List list = gp.getChildren();
            if (!mainGrid.grid[cellW][cellH].isOpened()) {
                if(openCheck() || flagCheck()){
                    mainGrid.endGame();
                    showOpened(list);
                    btnStart.setText("WIN!");
                    timeline.stop();
                }
                if (mainGrid.grid[cellW][cellH].isBomb() && mainGrid.grid[cellW][cellH].getMark() != Cell.Mark.flag){
                    btnStart.setText("LOST(");
                    timeline.stop();
                }
                if (mouseEvent.getButton() == MouseButton.PRIMARY && mainGrid.grid[cellW][cellH].getMark() != Cell.Mark.flag) {
                    mainGrid.openCell(cellW, cellH);
                    showOpened(list);
                    if (mainGrid.grid[cellW][cellH].getValue() == Cell.Value.bomb) timeline.stop();
                }
                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    int i = cellH * w + cellW;
                    ImageView cl = (ImageView) list.get(i);
                    if (mainGrid.grid[cellW][cellH].getMark() != Cell.Mark.flag) {
                        cl.setImage(new Image(getClass().getResourceAsStream("/flag.png")));
                        mainGrid.grid[cellW][cellH].setMark(Cell.Mark.flag);
                        flags++;
                    } else {
                        cl.setImage(new Image(getClass().getResourceAsStream("/square.png")));
                        mainGrid.grid[cellW][cellH].setMark(Cell.Mark.square);
                        flags--;
                    }
                    lblBomb.setText(String.valueOf(b - flags));
                }
            }
        }
    }

    public void difChanged(ActionEvent actionEvent) {
        Dialog<Map<String, Integer>> dialog = new Dialog<>();
        dialog.setTitle("Custom grid settings");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.initModality(Modality.NONE);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField width = new TextField();
        width.setPromptText("Width(5-40)");
        TextField height = new TextField();
        height.setPromptText("Height(5-40)");
        TextField bombs = new TextField();
        bombs.setPromptText("Bombs(1-999)");
        grid.add(new Label("Width:"), 0, 0);
        grid.add(width, 1, 0);
        grid.add(new Label("Height:"), 0, 1);
        grid.add(height, 1, 1);
        grid.add(new Label("Bombs:"), 0, 2);
        grid.add(bombs, 1, 2);

        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);
        width.textProperty().addListener((observable, oldValue, newValue) -> okButton.setDisable(btnCheck(width.getText(), height.getText(), bombs.getText())));
        height.textProperty().addListener((observable2, oldValue2, newValue2) -> okButton.setDisable(btnCheck(width.getText(), height.getText(), bombs.getText())));
        bombs.textProperty().addListener((observable3, oldValue3, newValue3) -> okButton.setDisable(btnCheck(width.getText(), height.getText(), bombs.getText())));

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {

            Map res = new HashMap();
            res.put("width", Integer.parseInt(width.getText()));
            res.put("height", Integer.parseInt(height.getText()));
            res.put("bombs", Integer.parseInt(bombs.getText()));
            return res;
        });


        switch ((String) CmbDif.getValue()) {
            case ("beginner(9х9, 10 bombs)"):
                this.w = 9;
                this.h = 9;
                this.b = 10;
                break;
            case ("intermediate(16х16, 40 bombs)"):
                this.w = 16;
                this.h = 16;
                this.b = 40;
                break;
            case ("expert(16х30, 99 bombs)"):
                this.w = 30;
                this.h = 16;
                this.b = 99;
                break;
            case ("customizable"):
                Optional<Map<String, Integer>> custom = dialog.showAndWait();
                this.w = custom.get().get("width");
                this.h = custom.get().get("height");
                this.b = custom.get().get("bombs");
                break;
        }
    }

    public void newScene(Integer height, Integer width) throws IOException {
        Stage curStage = (Stage) tb.getScene().getWindow();
        GridPane grid = new GridPane();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cell.fxml"));
        loader.setLocation(getClass().getResource("/tools.fxml"));
        VBox vboxmain = new VBox();
        vboxmain.setAlignment(Pos.TOP_CENTER);
        Controller as = loader.getController();
        ToolBar tools = loader.load();
        lblBomb = (Label) tools.getItems().get(5);
        lblBomb.setText(String.valueOf(b));
        lblTime = (Label) tools.getItems().get(1);
        btnStart = (Button)tools.getItems().get(3);
        grid.setPadding(new Insets(0, 0, 0, 0));
        grid.setId("gp");
        grid.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cellClick(mouseEvent);
            }
        });
        for (int y = 1; y <= height; y++) {
            for (int x = 1; x <= width; x++) {
                FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/cell.fxml"));
                ImageView a = loader1.load();
                grid.add(a, x, y);
            }
        }
        grid.setAlignment(Pos.CENTER);

        vboxmain.getChildren().addAll(tools, grid);
        Scene primaryScene = new Scene(vboxmain, Color.rgb(192, 192, 192));
        curStage.setScene(primaryScene);
        mainGrid = new Grid(this.w, this.h, this.b);
    }

    private boolean openCheck(){
        boolean a = true;
        for (int y = 0; y < this.h; y++) {
            for (int x = 0; x < this.w; x++) {
                if(!mainGrid.grid[x][y].isOpened() && !mainGrid.grid[x][y].isBomb()) a = false;
            }
        }
        return a;
    }

    private boolean flagCheck(){
        boolean a = true;
        if (b == flags){
            for (int y = 0; y < this.h; y++) {
                for (int x = 0; x < this.w; x++) {
                    if(mainGrid.grid[x][y].getMark() != Cell.Mark.flag  && mainGrid.grid[x][y].isBomb()) a = false;
                }
            }
        } else a = false;
        return a;
    }
    private void showOpened(List list){
        for (int i = 0; i < list.size(); i++) {
            int H = i / w;
            int W = i % w;
            if (mainGrid.grid[W][H].isOpened()) {
                ImageView thisCell = (ImageView) list.get(i);
                thisCell.setImage(new Image(getClass().getResourceAsStream("/" + mainGrid.grid[W][H].getValue() + ".png")));
            }
        }
    }


    private boolean btnCheck(String w, String h, String b) {
        return !(NumberUtils.isParsable(w) && NumberUtils.isParsable(b) && NumberUtils.isParsable(h)
                && Integer.parseInt(w) >= 5 && Integer.parseInt(w) <= 40 && Integer.parseInt(h) >= 5 && Integer.parseInt(h) <= 30
                && Integer.parseInt(b) >= 1 && Integer.parseInt(b) <= 999 && Integer.parseInt(b) < Integer.parseInt(h) * Integer.parseInt(w));
    }
}

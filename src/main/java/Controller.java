import javafx.event.ActionEvent;
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

import org.apache.commons.lang3.math.NumberUtils;

public class Controller {
    @FXML
    public Label lblBomb;
    @FXML
    private static GridPane gp;
    @FXML
    private ToolBar tb;
    @FXML
    private ComboBox CmbDif;
    @FXML
    private Label lbl11;
    @FXML
    private Button btnStart;
    @FXML
    private ImageView cl;
    @FXML
    private  Integer w = 30;
    private  Integer h = 16;
    private  Integer b = 99;
    private  Integer flags = 0;
    private boolean isGameStarted;
    Grid mainGrid = new Grid(w, h, b);


    public void startClick(MouseEvent mouseEvent) throws IOException {

        Stage stage = new Stage();
        newScene(h,w);


    }
    public static GridPane getGP(){
        return gp;
    }

    public void cellClick(MouseEvent mouseEvent) throws IOException {

        int cellH = 0;
        int cellW = 0;
        GridPane gp = (GridPane) cl.getParent();
        List list = gp.getChildren();
        for (int i = 0; i < list.size(); i++) {
            if (cl == list.get(i)) {
                cellH = i / w;
                cellW = i % w;
            }
        }
        if(!mainGrid.grid[cellW][cellH].isOpened()) {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                mainGrid.openCell(cellW, cellH);
                for (int i = 0; i < list.size(); i++) {
                    int H = i / w;
                    int W = i % w;
                    if(mainGrid.grid[W][H].isOpened()){
                        //System.out.println(W+" "+H);
                        ImageView thisCell = (ImageView)list.get(i);
                        thisCell.setImage(new Image(getClass().getResourceAsStream("/" + mainGrid.grid[W][H].getValue() + ".png")));}
                }



            }
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                if(mainGrid.grid[cellW][cellH].getMark() == Cell.Mark.square){
                cl.setImage(new Image(getClass().getResourceAsStream("/flag.png")));
                mainGrid.grid[cellW][cellH].setMark(Cell.Mark.flag);
                flags++;
                }
                else{
                    cl.setImage(new Image(getClass().getResourceAsStream("/square.png")));
                    mainGrid.grid[cellW][cellH].setMark(Cell.Mark.square);
                    flags--;
                }
                lblBomb.setText(String.valueOf(b-flags));

            }
        }
    }

    public void difChanged(ActionEvent actionEvent) {
        lblBomb.setText("fuck u");
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
        width.setPromptText("Width(2-40)");
        TextField height = new TextField();
        height.setPromptText("Height(2-40)");
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
                w = 9;
                h = 9;
                b = 10;
                break;
            case ("intermediate(16х16, 40 bombs)"):
                w = 16;
                h = 16;
                b = 40;
                break;
            case ("expert(16х30, 99 bombs)"):
                w = 30;
                h = 16;
                b = 99;
                break;
            case ("customizable"):
                Optional<Map<String, Integer>> custom = dialog.showAndWait();
                w = custom.get().get("width");
                h = custom.get().get("height");
                b = custom.get().get("bombs");
                break;
        }
        lblBomb.setText(b.toString());
    }
    public void newScene(Integer height, Integer width) throws IOException {
        Stage curStage = (Stage)tb.getScene().getWindow();
        GridPane grid = new GridPane();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cell.fxml"));
        loader.setLocation(getClass().getResource("/tools.fxml"));
        VBox vboxmain = new VBox();
        vboxmain.setAlignment(Pos.CENTER);
        vboxmain.setPadding(new Insets(10, 10, 10, 10));
        Controller as = loader.getController();
        ToolBar tools = loader.load();


        grid.setId("gp");
        for (int y = 1; y <= height; y++) {
            for (int x = 1; x <=width; x++) {

                loader.setLocation(getClass().getResource("/cell.fxml"));
                loader.setController(as);
                ImageView a = loader.load();
                grid.add(a, x, y);
            }
        }
        grid.setAlignment(Pos.CENTER);

        vboxmain.getChildren().addAll(tools, grid);
        Scene primaryScene = new Scene(vboxmain, Color.rgb(192, 192, 192));
        curStage.setScene(primaryScene);
    }

    private boolean btnCheck(String w, String h, String b) {
        return !(NumberUtils.isParsable(w) && NumberUtils.isParsable(b) && NumberUtils.isParsable(h)
                && Integer.parseInt(w) >= 2 && Integer.parseInt(w) <= 40 && Integer.parseInt(h) >= 2 && Integer.parseInt(h) <= 40
                && Integer.parseInt(b) >= 1 && Integer.parseInt(b) <= 999);
    }
}

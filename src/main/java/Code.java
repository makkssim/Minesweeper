import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;

public class Code {


    public void btnClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/Cell.fxml");
        loader.setLocation(xmlUrl);
        Parent cell = loader.load();
        GridPane a = new GridPane();
        for(int i = 0; i<30; i++){
            for(int j = 0; j<30; j++){
                a.add(cell,i,j);
            }
        }
    

    }
}

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.reflect.Array;

public class Grid {
    private Integer width;
    private Integer height;
    private Integer bombs;
    public void setWidth(Integer a){this.width = a;}
    public void setHeight(Integer a){this.height = a;}
    public Cell[][] grid;

    public Grid(){
        Integer i =0;
        while(i<bombs) {
            Integer x = (int) (Math.random() * this.width);
            Integer y = (int) (Math.random() * this.height);
            if (grid[x][y].getValue() != 9) {
                grid[x][y].setValue(9);
                i++;
            }
        }
        for(int y = 1; y <= this.height; y++) {
            for(int x = 1; x <= this.width; x++) {
                if (grid[x][y].getValue() == null) {
                    grid[x][y].setValue(bombsNear(x,y));
                }
            }
        }
    }

    private Integer bombsNear(Integer x, Integer y){
        Integer bombs = 0;
        for(int i = x-1; i<=x+1;x++){
            for(int j = y-1; j<=y+1;y++){
                if(i>=1 && i<= this.width && j>=1 && j<= this.height && grid[i][j].isBomb()) bombs++;
            }
        }
        return bombs;
    }


    public void openCell(Integer x, Integer y){
        //grid.add(new ImageView(new Image(getClass().getResourceAsStream("/."+grid[x][y].getValue().toString()+".png"))), x,y);
        if (grid[x][y].getValue()<=8) grid[x][y].open();
        else this.lose();
        if (grid[x][y].getValue() == 0) for(int i = x-1; i<=x+1;x++){
            for(int j = y-1; j<=y+1;y++){
                if(i>=1 && i<= this.width && j>=1 && j<= this.height) this.openCell(i,j);
            }
        }
    }
    private void lose(){

    }

}

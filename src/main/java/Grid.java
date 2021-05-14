import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.reflect.Array;

public class Grid {
    private Integer width;
    private Integer height;
    private Integer bombs;
    public Cell[][] grid;

    public Grid(Integer w, Integer h, Integer b){
        Integer i =0;
        while(i<bombs) {
            Integer x = (int) (Math.random() * this.width);
            Integer y = (int) (Math.random() * this.height);
            if (grid[x][y].getValue() != Cell.Value.bomb) {
                grid[x][y].setValue(Cell.Value.bomb);
                i++;
            }
        }
        for(int y = 1; y <= this.height; y++) {
            for(int x = 1; x <= this.width; x++) {
                if (grid[x][y].getValue() == null) {
                    Cell.Value val = Cell.Value.zero;
                    switch (bombsNear(x,y)){
                        case(1): val = Cell.Value.one; break;
                        case(2): val = Cell.Value.two; break;
                        case(3): val = Cell.Value.three; break;
                        case(4): val = Cell.Value.four; break;
                        case(5): val = Cell.Value.five; break;
                        case(6): val = Cell.Value.six; break;
                        case(7): val = Cell.Value.seven; break;
                        case(8): val = Cell.Value.eight; break;
                    }
                    grid[x][y].setValue(val);
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
        if (grid[x][y].getValue() != Cell.Value.bomb) grid[x][y].open();
        else this.lose();
        if (grid[x][y].getValue() == Cell.Value.zero) for(int i = x-1; i<=x+1; x++){
            for(int j = y-1; j<=y+1;y++){
                if(i>=1 && i<= this.width && j>=1 && j<= this.height) this.openCell(i,j);
            }
        }
    }
    private void lose(){

    }

}

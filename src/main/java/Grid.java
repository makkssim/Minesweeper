import java.util.HashMap;
import java.util.Map;

public class Grid {
    private Integer width;
    private Integer height;
    private Integer bombs;
    public Cell[][] grid;

    public Grid(Integer w, Integer h, Integer b) {
        int i = 0;
        this.bombs = b;
        this.height = h;
        this.width = w;

        this.grid = new Cell[this.width][this.height];
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                this.grid[x][y] = new Cell();
                this.grid[x][y].setValue(Cell.Value.zero);
            }
        }

        while (i < this.bombs) {
            int x = (int) (Math.random() * this.width);
            int y = (int) (Math.random() * this.height);
            if (this.grid[x][y].getValue() != Cell.Value.bomb) {
                this.grid[x][y].setValue(Cell.Value.bomb);
                i++;
            }
        }
        HashMap<Integer,Cell.Value> values = new HashMap<>() {{
            put(0, Cell.Value.zero);
            put(1, Cell.Value.one);
            put(2, Cell.Value.two);
            put(3, Cell.Value.three);
            put(4, Cell.Value.four);
            put(5, Cell.Value.five);
            put(6, Cell.Value.six);
            put(7, Cell.Value.seven);
            put(8, Cell.Value.eight);
        }};

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (this.grid[x][y].getValue() != Cell.Value.bomb) {
                    this.grid[x][y].setValue(values.get(bombsNear(x,y)));
                }
            }
        }
    }


    public Integer bombsNear(Integer x, Integer y) {

        Integer bs = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < this.width && j >= 0 && j < this.height && this.grid[i][j].isBomb()) bs++;
            }
        }
        return bs;
    }

    public void openCell(Integer x, Integer y) {
        if (this.grid[x][y].isBomb()) {
            this.grid[x][y].setValue(Cell.Value.bombAfterLose);
            endGame();
        }
        if (!this.grid[x][y].isOpened()) this.grid[x][y].open();

        if (this.grid[x][y].getValue() == Cell.Value.zero)
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (i >= 0 && i < this.width && j >= 0 && j < this.height && !this.grid[i][j].isOpened())
                        this.openCell(i, j);
                }
            }
    }

    public void endGame() {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (this.grid[x][y].getValue() != Cell.Value.bomb && this.grid[x][y].getMark() == Cell.Mark.flag)
                    this.grid[x][y].setValue(Cell.Value.wrong);
                this.grid[x][y].open();
            }
        }
    }


    public  boolean winCheck(int flags){
        boolean b = true;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if(!this.grid[x][y].isOpened() && !this.grid[x][y].isBomb()) b = false;
            }
        }
        boolean a = true;
        if (bombs == flags){
            for (int y = 0; y < this.height; y++) {
                for (int x = 0; x < this.width; x++) {
                    if(this.grid[x][y].getMark() != Cell.Mark.flag  && this.grid[x][y].isBomb()) a = false;
                }
            }
        } else a = false;
        if (a || b) this.endGame();
        return (a || b);
    }




}

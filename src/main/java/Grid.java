

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
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (this.grid[x][y].getValue() != Cell.Value.bomb) {
                    Cell.Value val = Cell.Value.zero;
                    switch (bombsNear(x, y)) {
                        case (1):
                            val = Cell.Value.one;
                            break;
                        case (2):
                            val = Cell.Value.two;
                            break;
                        case (3):
                            val = Cell.Value.three;
                            break;
                        case (4):
                            val = Cell.Value.four;
                            break;
                        case (5):
                            val = Cell.Value.five;
                            break;
                        case (6):
                            val = Cell.Value.six;
                            break;
                        case (7):
                            val = Cell.Value.seven;
                            break;
                        case (8):
                            val = Cell.Value.eight;
                            break;
                    }
                    this.grid[x][y].setValue(val);
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



}

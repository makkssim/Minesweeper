import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MsTest {

    @Test
    public void ls(){
        Grid grid = new Grid(30, 16, 99);

        grid.grid[0][0].setMark(Cell.Mark.flag);
        assertEquals(grid.grid[0][0].getMark(), Cell.Mark.flag);

        int i = 0;
        int j = 0;
        while (grid.grid[i][j].getValue() != Cell.Value.zero) {
            while (grid.grid[i][j].getValue() != Cell.Value.zero) {
                j++;
                if (j == 15) {
                    j = 0;
                    break;
                }
            }
            i++;
        }
        grid.openCell(i, j);

        for (int k = i - 1; k <= i + 1; k++) {
            for (int l = j - 1; l <= j + 1; l++) {
                if (k >= 0 && k < 30 && l >= 0 && l < 16)
                    assertTrue(grid.grid[k][l].isOpened());
            }
        }
        j = 0;
        i = 0;
        while (grid.grid[i][j].getValue() != Cell.Value.bomb) {
            while (grid.grid[i][j].getValue() != Cell.Value.bomb) {
                j++;
                if (j == 15) {
                    j = 0;
                    break;
                }
                i++;
            }
        }
        grid.openCell(i, j);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 30; x++) {
                assertTrue(grid.grid[x][y].isOpened());
            }
        }
        Grid grid1 = new Grid(30, 16, 99);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 30; x++) {
                if (grid1.grid[x][y].isBomb())grid1.grid[x][y].setMark(Cell.Mark.flag);
            }
        }
        grid1.winCheck(99);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 30; x++) {
                assertTrue(grid1.grid[x][y].isOpened());
            }
        }
        Grid grid2 = new Grid(30, 16, 99);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 30; x++) {
                if (!grid2.grid[x][y].isBomb())grid2.grid[x][y].open();
            }
        }
        grid2.winCheck(0);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 30; x++) {
                assertTrue(grid2.grid[x][y].isOpened());
            }
        }




    }


}

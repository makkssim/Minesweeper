import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MsTest {

    @Test
    public void ls() throws IOException, URISyntaxException {
        Grid grid = new Grid(30, 16, 99);

        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 30; x++) {
                if (grid.grid[x][y].getValue() != Cell.Value.bomb) {
                    Cell.Value val = Cell.Value.zero;
                    switch (grid.bombsNear(x, y)) {
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
                    assertEquals(grid.grid[x][y].getValue(), val);
                }
            }
        }
        grid.grid[0][0].setMark(Cell.Mark.flag);
        assertEquals(grid.grid[0][0].getMark(), Cell.Mark.flag);
        grid.grid[1][1].open();
        assertTrue(grid.grid[1][1].isOpened());

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


    }


}

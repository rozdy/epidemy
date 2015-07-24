package sorry.no.domain.test_project;

import android.util.Size;

/**
 * Created by hex on 7/22/2015.
 */
public class Board {

    public static final int DEFAULT_WIDTH = 10;
    public static final int DEFAULT_HEIGHT = 10;

    private int width,height;
    private Cell[][] cells;

    public Board() {
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;

        cells = new Cell[height][width];

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell();
            }
    }
}

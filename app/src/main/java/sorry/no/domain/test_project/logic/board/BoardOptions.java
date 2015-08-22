package sorry.no.domain.test_project.logic.board;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hex on 8/16/2015 in the name of the Emperor!
 */
public class BoardOptions {
    private int width, height;

    public static final int DEFAULT_WIDTH = 10;
    public static final int DEFAULT_HEIGHT = 10;

    public static final List<BoardOptions> standardBoardSizes =
            Arrays.asList(new BoardOptions(10, 10), new BoardOptions(20, 20));

    public BoardOptions() {
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
    }

    public BoardOptions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public String toString() {
        return width + "x" + height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardOptions)) return false;

        BoardOptions boardOptions = (BoardOptions) o;

        if (boardOptions.getWidth() != this.getWidth()) return false;
        return (boardOptions.getHeight() == this.getHeight());
    }
}

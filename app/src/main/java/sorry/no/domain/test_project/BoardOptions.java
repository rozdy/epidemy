package sorry.no.domain.test_project;

/**
 * Created by hex on 8/16/2015.
 */
public class BoardOptions {
    private int width, height;

    public static final int DEFAULT_WIDTH = 10;
    public static final int DEFAULT_HEIGHT = 10;

    public BoardOptions() {
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

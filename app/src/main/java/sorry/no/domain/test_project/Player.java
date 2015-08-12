package sorry.no.domain.test_project;

/**
 * Created by hex on 7/22/2015.
 */
public class Player {

    private static int nextId = 0;

    private String name;
    private int color;
    private int id;

    public Player(int color) {
        id = nextId++;
        name = "Player " + (id + 1);
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public static void resetIdCounter() {
        nextId = 0;
    }
}

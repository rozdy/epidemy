package sorry.no.domain.test_project;

/**
 * The Game class would be a Singletone. It means we have a single instance of a game available through the whole app
 */
public class Options {
    private static Options instance;
    private UsersOptions usersOptions;
    private BoardOptions boardOptions;
    private GameOptions gameOptions;

    private Options() {
        usersOptions = new UsersOptions();
        boardOptions = new BoardOptions();
        gameOptions = new GameOptions();
    }

    public static Options getInstance() {
        if (instance == null) {
            instance = new Options();
        }
        return instance;
    }

    public void init() {
        instance = new Options();
    }

    public BoardOptions getBoardOptions() {
        return boardOptions;
    }

    public UsersOptions getUsersOptions() {
        return usersOptions;
    }

    public GameOptions getGameOptions() {
        return gameOptions;
    }
}

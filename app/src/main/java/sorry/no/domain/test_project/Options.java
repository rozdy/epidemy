package sorry.no.domain.test_project;

import sorry.no.domain.test_project.logic.board.BoardOptions;
import sorry.no.domain.test_project.logic.game.GameOptions;
import sorry.no.domain.test_project.logic.player.UsersOptions;

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

    public static void init() {
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

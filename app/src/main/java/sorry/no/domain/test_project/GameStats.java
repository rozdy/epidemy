package sorry.no.domain.test_project;

/**
 * Created by hex on 8/1/2015.
 */
public class GameStats {
    private Player winner, looser;

    public GameStats(Game instance, int reason) {
        switch (reason) { //todo rethink
            case Game.GAME_FINISH_SURRENDER:
            case Game.GAME_FINISH_NO_MOVES:
            case Game.GAME_FINISH_NO_MARKS:
                looser = instance.getPlayer(instance.getActivePlayer());
                winner = instance.getPlayer((instance.getActivePlayer() + 1) % Game.DEFAULT_PLAYERS_NUMBER);
                break;
        }
    }

    public Player getLooser() {
        return looser;
    }

    public Player getWinner() {
        return winner;
    }
}

package sorry.no.domain.test_project;

/**
 * Created by hex on 8/1/2015.
 */
public class GameStats {
    private Player winner, looser;

    public GameStats(Game instance, int reason) {
        switch (reason) {
            case Game.GAME_FINISH_SURRENDER:
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
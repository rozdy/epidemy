package sorry.no.domain.test_project.logic.player;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hex on 8/16/2015.
 */
public class UsersOptions {
    private List<Player> players;

    public static final int[] DEFAULT_PLAYER_COLORS = {Color.RED, Color.BLUE,
            Color.GREEN, Color.YELLOW};
    public static final int MAX_PLAYERS_NUMBER = 4;
    public static final int MIN_PLAYERS_NUMBER = 2;

    public UsersOptions() {
        players = new ArrayList<Player>();
        for (int i = 0; i < MAX_PLAYERS_NUMBER; i++) {
            players.add(new Player(DEFAULT_PLAYER_COLORS[i % DEFAULT_PLAYER_COLORS.length]));
        }
    }

    public Player getPlayer(int number) {
        return players.get(number);
    }
}

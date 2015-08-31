package sorry.no.domain.test_project.logic.bus;

import sorry.no.domain.test_project.logic.player.Player;

/**
 * Created by hex on 8/31/2015 in the name of the Emperor!
 */
public class PlayerLoseEvent {
    private Player player;

    public PlayerLoseEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}

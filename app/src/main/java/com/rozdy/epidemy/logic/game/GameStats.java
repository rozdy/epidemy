package com.rozdy.epidemy.logic.game;

import java.util.List;

import com.rozdy.epidemy.logic.player.Player;

/**
 * Created by hex on 8/1/2015 in the name of the Emperor!
 */
public class GameStats {
    private List<Player> players;

    public GameStats(Game instance) {
        players = instance.getPlayers();
    }

    public Player getWinner() {
        int counter = -1;
        while (!players.get(++counter).isInGame()) {};
        return players.get(counter);
    }
}

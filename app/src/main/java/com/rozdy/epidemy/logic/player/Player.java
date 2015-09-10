package com.rozdy.epidemy.logic.player;

import com.rozdy.epidemy.bus.EventBus;
import com.rozdy.epidemy.bus.GameFinishEvent;
import com.rozdy.epidemy.bus.PlayerLoseEvent;
import com.rozdy.epidemy.logic.game.Game;

/**
 * Created by hex on 7/22/2015 in the name of the Emperor!
 */
public class Player {
    public static final int PLAYER_HAS_NO_MARKS = 1;
    public static final int PLAYER_HAS_NO_MOVES = 2;
    public static final int PLAYER_SURRENDER = 3;

    private static int nextId = 0;

    private String name;
    private int color;
    private int loseTurn = -1;
    private int loseReason = -1;
    private boolean inGame;

    public Player(int color) {
        name = "Player " + ++nextId;
        this.color = color;
        inGame = true;
    }

    public Player(Player player) {
        this.name = player.name;
        this.color = player.color;
        this.loseTurn = player.loseTurn;
        this.loseReason = player.loseReason;
        this.inGame = player.inGame;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void lose(int currentTurn, int reason) {
        inGame = false;
        loseTurn = currentTurn;
        loseReason = reason;
        EventBus.getInstance().post(new PlayerLoseEvent(this));
        if (Game.getInstance().isGameFinished()) {
            EventBus.getInstance().post(new GameFinishEvent());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void resetIdCounter() {
        nextId = 0;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getLoseReason() {
        return loseReason;
    }

    public int getLoseTurn() {
        return loseTurn;
    }
}

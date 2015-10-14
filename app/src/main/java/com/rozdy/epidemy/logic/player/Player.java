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
    private int id;
    private int loseTurn = -1;
    private int loseReason = -1;
    private boolean inGame;
    private boolean ai;

    public Player(int color) {
        id = nextId++;
        name = "Player " + (id + 1);
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

    public int getId() {
        return id;
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

    public boolean isAi() {
        return ai;
    }

    public void setAi(boolean ai) {
        this.ai = ai;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (getColor() != player.getColor()) return false;
        if (getId() != player.getId()) return false;
        if (getLoseTurn() != player.getLoseTurn()) return false;
        if (getLoseReason() != player.getLoseReason()) return false;
        if (isInGame() != player.isInGame()) return false;
        if (isAi() != player.isAi()) return false;
        return !(getName() != null ? !getName().equals(player.getName()) : player.getName() != null);

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + getColor();
        result = 31 * result + getId();
        result = 31 * result + getLoseTurn();
        result = 31 * result + getLoseReason();
        result = 31 * result + (isInGame() ? 1 : 0);
        result = 31 * result + (isAi() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", color=" + color +
                ", id=" + id +
                ", loseTurn=" + loseTurn +
                ", loseReason=" + loseReason +
                ", inGame=" + inGame +
                ", ai=" + ai +
                '}';
    }
}

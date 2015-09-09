package com.rozdy.epidemy.logic.player;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.rozdy.epidemy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hex on 8/16/2015 in the name of the Emperor!
 */
public class UsersOptions {
    private List<Player> players;

    public static final int[] DEFAULT_PLAYER_COLORS = {Color.RED, Color.BLUE,
            Color.DKGRAY, Color.MAGENTA};
    public static final int MAX_PLAYERS_NUMBER = 4;
    public static final int MIN_PLAYERS_NUMBER = 2;

    public UsersOptions() {
        players = new ArrayList<>();
        for (int i = 0; i < MAX_PLAYERS_NUMBER; i++) {
            players.add(new Player(DEFAULT_PLAYER_COLORS[i % DEFAULT_PLAYER_COLORS.length]));
        }
    }

    public Player getPlayer(int number) {
        return players.get(number);
    }

    public void load(Activity activity, SharedPreferences sharedPref) {
        for (Player player : players) {
            int id = player.getId();
            player.setName(sharedPref.getString(activity.getString(R.string.sharedPrefs_player_name) + id, player.getName()));
            player.setColor(sharedPref.getInt(activity.getString(R.string.sharedPrefs_player_color) + id, player.getColor()));
        }
    }

    public void save(Activity activity, SharedPreferences sharedPref) {
        SharedPreferences.Editor editor = sharedPref.edit();
        for (Player player : players) {
            int id = player.getId();
            editor.putString(activity.getString(R.string.sharedPrefs_player_name) + id, player.getName());
            editor.putInt(activity.getString(R.string.sharedPrefs_player_color) + id, player.getColor());
        }
        editor.apply();
    }
}

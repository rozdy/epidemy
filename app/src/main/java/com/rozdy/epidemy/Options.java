package com.rozdy.epidemy;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.rozdy.epidemy.logic.board.BoardOptions;
import com.rozdy.epidemy.logic.game.GameOptions;
import com.rozdy.epidemy.logic.player.UsersOptions;

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

    public static void load(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        Options.getInstance().getGameOptions().load(activity, sharedPref);
        Options.getInstance().getBoardOptions().load(activity, sharedPref);
        Options.getInstance().getUsersOptions().load(activity, sharedPref);
    }

    public static void save(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        Options.getInstance().getGameOptions().save(activity, sharedPref);
        Options.getInstance().getBoardOptions().save(activity, sharedPref);
        Options.getInstance().getUsersOptions().save(activity, sharedPref);
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

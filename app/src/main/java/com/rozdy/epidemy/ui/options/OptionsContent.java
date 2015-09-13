package com.rozdy.epidemy.ui.options;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rozdy.epidemy.Options;
import com.rozdy.epidemy.R;

/**
 * Created by hex on 8/16/2015 in the name of the Emperor!
 */
public class OptionsContent {
    public static List<OptionsItem> ITEMS = new ArrayList<>();

    public static Map<String, OptionsItem> ITEM_MAP = new HashMap<>();

    public static final String GAME_OPTIONS_ID = "1";
    public static final String BOARD_OPTIONS_ID = "2";
    public static final String USERS_OPTIONS_ID = "3";
    public static final String DEFAULTS_OPTIONS_ID = "4";

    public static ArrayAdapter<OptionsItem> createOptionsListAdapter(Context context) {
        init(context);
        return new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_activated_1,
                ITEMS);
    }

    private static void init(Context context) {
        if (ITEMS.isEmpty()) {
            addItem(new OptionsItem(GAME_OPTIONS_ID,
                    context.getResources().getString(R.string.game_options),
                    Options.getInstance().getGameOptions()));
            addItem(new OptionsItem(BOARD_OPTIONS_ID,
                    context.getResources().getString(R.string.board_options),
                    Options.getInstance().getBoardOptions()));
            addItem(new OptionsItem(USERS_OPTIONS_ID,
                    context.getResources().getString(R.string.users_options),
                    Options.getInstance().getUsersOptions()));
            addItem(new OptionsItem(DEFAULTS_OPTIONS_ID,
                    context.getResources().getString(R.string.defaults_options), null));
        }
    }

    private static void addItem(OptionsItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class OptionsItem {
        public String id;
        public String name;
        public Object content;

        public OptionsItem(String id, String name, Object content) {
            this.id = id;
            this.name = name;
            this.content = content;

        }

        @Override
        public String toString() {
            return name;
        }
    }
}

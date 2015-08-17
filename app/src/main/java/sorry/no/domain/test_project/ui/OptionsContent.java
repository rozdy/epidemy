package sorry.no.domain.test_project.ui;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sorry.no.domain.test_project.Game;
import sorry.no.domain.test_project.Options;
import sorry.no.domain.test_project.R;

/**
 * Created by hex on 8/16/2015.
 */
public class OptionsContent {
    public static List<OptionsItem> ITEMS = new ArrayList<OptionsItem>();

    public static Map<String, OptionsItem> ITEM_MAP = new HashMap<String, OptionsItem>();

    public static final String GAME_OPTIONS_ID = "1";
    public static final String BOARD_OPTIONS_ID = "2";
    public static final String USERS_OPTIONS_ID = "3";

    public static ArrayAdapter<OptionsContent.OptionsItem>
    createOptionsListAdapter(Context context, int resource) {
        init(context);
        ArrayAdapter<OptionsContent.OptionsItem> adapter = new ArrayAdapter<OptionsItem>(context,
                android.R.layout.simple_list_item_activated_1,
                ITEMS);
        return adapter;
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

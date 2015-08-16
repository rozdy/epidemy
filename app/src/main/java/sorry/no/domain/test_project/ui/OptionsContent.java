package sorry.no.domain.test_project.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hex on 8/16/2015.
 */
public class OptionsContent {
    public static List<OptionsItem> ITEMS = new ArrayList<OptionsItem>();

    public static Map<String, OptionsItem> ITEM_MAP = new HashMap<String, OptionsItem>();

    static {
        addItem(new OptionsItem("GameOptions", "Game options"));
        addItem(new OptionsItem("BoardOptions", "Board options"));
        addItem(new OptionsItem("UsersOptions", "Users options"));
    }

    private static void addItem(OptionsItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class OptionsItem {
        public String id;
        public String content;

        public OptionsItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}

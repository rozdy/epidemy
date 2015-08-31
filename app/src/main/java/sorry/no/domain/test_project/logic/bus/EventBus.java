package sorry.no.domain.test_project.logic.bus;

import com.squareup.otto.Bus;

/**
 * The Game class would be a Singletone. It means we have a single instance of a game available through the whole app
 */
public class EventBus {
    private static Bus instance;

    public static Bus getInstance() {
        if (instance == null) {
            instance = new Bus();
        }
        return instance;
    }
}

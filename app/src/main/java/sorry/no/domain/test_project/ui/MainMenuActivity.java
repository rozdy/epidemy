package sorry.no.domain.test_project.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import sorry.no.domain.test_project.Options;
import sorry.no.domain.test_project.R;
import sorry.no.domain.test_project.logic.bus.EventBus;
import sorry.no.domain.test_project.logic.game.Game;
import sorry.no.domain.test_project.ui.options.OptionsListActivity;


public class MainMenuActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
    }

    public void startGame(View view) {
        EventBus.init();
        Game.init();
        Intent intent = new Intent(this, GameBoardActivity.class);
        startActivity(intent);
    }

    public void showOptions(View view) {
        Intent intent = new Intent(this, OptionsListActivity.class);
        startActivity(intent);
    }
}

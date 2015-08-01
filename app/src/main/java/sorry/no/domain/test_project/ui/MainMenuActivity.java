package sorry.no.domain.test_project.ui;

import sorry.no.domain.test_project.Game;
import sorry.no.domain.test_project.R;
import sorry.no.domain.test_project.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;


public class MainMenuActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
    }

    public void startGame(View view) {
        Game.init();
        Intent intent = new Intent(this, GameBoardActivity.class);
        startActivity(intent);
    }
}

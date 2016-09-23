package com.rozdy.epidemy.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rozdy.epidemy.Options;
import com.rozdy.epidemy.R;
import com.rozdy.epidemy.logic.game.Game;
import com.rozdy.epidemy.ui.options.OptionsListActivity;


public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Options.load(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Options.save(this);
    }

    public void startGame(View view) {
        Game.init();
        Intent intent = new Intent(this, GameBoardActivity.class);
        startActivity(intent);
    }

    public void showOptions(View view) {
        Intent intent = new Intent(this, OptionsListActivity.class);
        startActivity(intent);
    }

    public void goToHelp(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
}

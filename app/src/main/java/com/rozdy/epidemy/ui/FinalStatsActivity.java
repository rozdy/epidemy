package com.rozdy.epidemy.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rozdy.epidemy.R;
import com.rozdy.epidemy.logic.game.Game;
import com.rozdy.epidemy.logic.game.GameStats;
import com.rozdy.epidemy.logic.player.Player;

public class FinalStatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_stats);

        updateStats();
    }

    private void updateStats() {
        GameStats stats = new GameStats(Game.getInstance());
        int padding = (int) getResources().getDimension(R.dimen.final_stats_padding);
        TableLayout tableLayout = (TableLayout) findViewById(R.id.final_stats_table);
        for (Player player : stats.getPlayers()) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            TextView playerName = new TextView(this);
            playerName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            playerName.setPadding(padding, padding, padding, padding);
            playerName.setText(player.getName());
            tableRow.addView(playerName);
            View playerColor = new View(this);
            playerColor.setLayoutParams(new TableRow.LayoutParams(30, 30)); //Todo fix params
            playerColor.setBackgroundColor(player.getColor());
            playerColor.setPadding(padding, padding, padding, padding);
            tableRow.addView(playerColor);
            TextView playerState = new TextView(this);
            playerState.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            playerState.setPadding(padding, padding, padding, padding);
            if (player.isInGame()) {
                playerState.setText(getString(R.string.winner));
                playerState.setTextColor(Color.GREEN);
            } else {
                playerState.setText(getString(R.string.loser));
                playerState.setTextColor(Color.RED);
            }
            tableRow.addView(playerState);
            TextView playerLoseReason = new TextView(this);
            playerLoseReason.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            playerLoseReason.setPadding(padding, padding, padding, padding);
            switch (player.getLoseReason()) {
                case Player.PLAYER_SURRENDER:
                    playerLoseReason.setText(getString(R.string.surrender));
                    break;
                case Player.PLAYER_HAS_NO_MARKS:
                    playerLoseReason.setText(getString(R.string.has_no_marks));
                    break;
                case Player.PLAYER_HAS_NO_MOVES:
                    playerLoseReason.setText(getString(R.string.has_no_moves));
                    break;
            }
            tableRow.addView(playerLoseReason);
            TextView playerLoseTurn = new TextView(this);
            playerLoseTurn.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            playerLoseTurn.setPadding(padding, padding, padding, padding);
            if (player.getLoseTurn() >= 0) {
                playerLoseTurn.setText("" + (player.getLoseTurn() + 1));
            }
            tableRow.addView(playerLoseTurn);
            tableLayout.addView(tableRow);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_final_stats, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public void revenge(View view) {
        Game.init();
        onBackPressed();
    }

    public void viewBoard(View view) {
        onBackPressed();
    }

    public void backToMainMenu(View view) {
        NavUtils.navigateUpFromSameTask(this);
    }
}

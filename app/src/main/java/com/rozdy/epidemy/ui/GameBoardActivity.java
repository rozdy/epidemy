package com.rozdy.epidemy.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.rozdy.epidemy.Options;
import com.rozdy.epidemy.R;
import com.rozdy.epidemy.bus.AIMoveEvent;
import com.rozdy.epidemy.bus.EventBus;
import com.rozdy.epidemy.bus.GameFinishEvent;
import com.rozdy.epidemy.bus.PlayerLoseEvent;
import com.rozdy.epidemy.logic.board.Board;
import com.rozdy.epidemy.logic.board.BoardAdapter;
import com.rozdy.epidemy.logic.board.BoardImageAdapter;
import com.rozdy.epidemy.logic.board.BoardView;
import com.rozdy.epidemy.logic.board.InvalidPositionException;
import com.rozdy.epidemy.logic.board.PureBoardAdapter;
import com.rozdy.epidemy.logic.board.StatusBarView;
import com.rozdy.epidemy.logic.cell.CellView;
import com.rozdy.epidemy.logic.cell.InvalidCellException;
import com.rozdy.epidemy.logic.game.Game;
import com.rozdy.epidemy.logic.game.InvalidMoveException;
import com.rozdy.epidemy.logic.player.Player;
import com.squareup.otto.Subscribe;

public class GameBoardActivity extends AppCompatActivity {
    private StatusBarView statusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);

        EventBus.getInstance().register(this);

        CellView.initCellViewBackground(this);

        BoardView boardView = (BoardView) findViewById(R.id.board_view);
        boardView.setColumnWidth(CellView.getCellWidth() + 2 * CellView.getCellPadding());
        if (Options.getInstance().getBoardOptions().getShowCellNumeration()) {
            boardView.setNumColumns(Game.getInstance().getBoard().getWidth() + 1);
            boardView.setAdapter(new BoardImageAdapter(this));
        } else {
            boardView.setNumColumns(Game.getInstance().getBoard().getWidth());
            boardView.setAdapter(new PureBoardAdapter(this));
        }
        boardView.setOnItemClickListener(getOnItemClickListener());

        statusBar = (StatusBarView) findViewById(R.id.status_bar);
        statusBar.setActivePlayerName((TextView) findViewById(R.id.active_player));
        statusBar.setNumberOfMoves((TextView) findViewById(R.id.number_of_moves));
        statusBar.setCurrentTurn((TextView) findViewById(R.id.current_turn));
        updateStatusBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BoardView boardView = (BoardView) findViewById(R.id.board_view);
        BoardAdapter adapter = (BoardAdapter) boardView.getAdapter();
        adapter.notifyDataSetChanged();
        boardView.invalidate();
        updateStatusBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
    }

    public OnItemClickListener getOnItemClickListener() {
        return new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BoardAdapter adapter = (BoardAdapter) parent.getAdapter();
                if (!Game.getInstance().isGameFinished()) {
                    try {
                        Toast toast;
                        switch (Game.getInstance().makeAMove(Game.getInstance().getActivePlayer(),
                                adapter.getXByPosition(position), adapter.getYByPosition(position))) {
                            case Board.MARK_PLACED:
                            case Board.WALL_PLACED:
                                adapter.notifyDataSetChanged();
                                view.invalidate();
                                updateStatusBar();
                                if (Game.getInstance().getNumberOfMoves() == Game.getInstance().getMaxNumberOfMoves()) {
                                    toast = Toast.makeText(parent.getContext(), getString(R.string.next_player) +
                                                    Game.getInstance().getPlayer(Game.getInstance().getActivePlayer()).getName(),
                                            Toast.LENGTH_LONG);
                                    toast.show();
                                }
                                break;
                            case Board.UNREACHABLE_CELL:
                                toast = Toast.makeText(parent.getContext(), R.string.UNREACHABLE_CELL, Toast.LENGTH_SHORT);
                                toast.show();
                                break;
                            case Board.ENEMY_WALL_HIT:
                                toast = Toast.makeText(parent.getContext(), R.string.ENEMY_WALL_HIT, Toast.LENGTH_SHORT);
                                toast.show();
                                break;
                            case Board.OWN_CROSS_HIT:
                                toast = Toast.makeText(parent.getContext(), R.string.OWN_CROSS_HIT, Toast.LENGTH_SHORT);
                                toast.show();
                                break;
                            case Board.OWN_WALL_HIT:
                                toast = Toast.makeText(parent.getContext(), R.string.OWN_WALL_HIT, Toast.LENGTH_SHORT);
                                toast.show();
                                break;
                        }
                    } catch (InvalidCellException | InvalidMoveException e) {
                        Toast toast = Toast.makeText(parent.getContext(), e.toString(), Toast.LENGTH_LONG);
                        toast.show();
                    } catch (InvalidPositionException e) {
                        //User clicks element on the top row or the left column. Or some strange error.
                    }
                }
            }
        };
    }

    public void updateStatusBar() {
        statusBar.getActivePlayerName().setText(Game.getInstance().getPlayer(Game.getInstance().getActivePlayer()).getName());
        statusBar.getNumberOfMoves().setText(Game.getInstance().getNumberOfMoves() + "/" + Game.getInstance().getMaxNumberOfMoves());
        statusBar.getCurrentTurn().setText(Game.getInstance().getCurrentTurn() + 1 + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_surrender:
                if (!Game.getInstance().isGameFinished()) {
                    Game.getInstance().getPlayer(Game.getInstance().getActivePlayer()).
                            lose(Game.getInstance().getCurrentTurn(), Player.PLAYER_SURRENDER);
                    Game.getInstance().nextActivePlayer();
                    updateStatusBar();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFinalStats() {
        Intent intent = new Intent(this, FinalStatsActivity.class);
        startActivity(intent);
    }

    @Subscribe
    public void playerLoseAction(PlayerLoseEvent event) {
        Toast toast = Toast.makeText(this, event.getPlayer().getName()
                + getString(R.string.is_defeated), Toast.LENGTH_LONG);
        toast.show();
    }

    @Subscribe
    public void gameFinishAction(GameFinishEvent event) {
        for (Player player : Game.getInstance().getPlayers()) {
            if (player.isInGame()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.behold)
                        .setMessage(player.getName() + getString(R.string.is_winner))
                        .setCancelable(false)
                        .setNegativeButton(R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        Game.getInstance().finish();
                                        showFinalStats();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    @Subscribe
    public void aiMoveAction(AIMoveEvent event) {
        BoardView boardView = (BoardView) findViewById(R.id.board_view);

        boardView.getOnItemClickListener().onItemClick(boardView, boardView.getChildAt(event.getPosition()), event.getPosition(), 0);
    }
}

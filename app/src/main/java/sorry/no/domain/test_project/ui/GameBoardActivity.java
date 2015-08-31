package sorry.no.domain.test_project.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import sorry.no.domain.test_project.Options;
import sorry.no.domain.test_project.R;
import sorry.no.domain.test_project.logic.board.Board;
import sorry.no.domain.test_project.logic.board.BoardAdapter;
import sorry.no.domain.test_project.logic.board.BoardImageAdapter;
import sorry.no.domain.test_project.logic.board.InvalidPositionException;
import sorry.no.domain.test_project.logic.board.PureBoardAdapter;
import sorry.no.domain.test_project.logic.board.StatusBarView;
import sorry.no.domain.test_project.logic.bus.EventBus;
import sorry.no.domain.test_project.logic.bus.GameFinishEvent;
import sorry.no.domain.test_project.logic.bus.PlayerLoseEvent;
import sorry.no.domain.test_project.logic.cell.InvalidCellException;
import sorry.no.domain.test_project.logic.game.Game;
import sorry.no.domain.test_project.logic.game.InvalidMoveException;
import sorry.no.domain.test_project.logic.player.Player;

public class GameBoardActivity extends ActionBarActivity {
    private StatusBarView statusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);

        EventBus.getInstance().register(this);

        GridView gridview = (GridView) findViewById(R.id.grid_view);
        if (Options.getInstance().getBoardOptions().getShowCellNumeration()) {
            gridview.setNumColumns(Game.getInstance().getBoard().getWidth() + 1);
            gridview.setAdapter(new BoardImageAdapter(this));
        } else {
            gridview.setNumColumns(Game.getInstance().getBoard().getWidth());
            gridview.setAdapter(new PureBoardAdapter(this));
        }
        gridview.setOnItemClickListener(getOnItemClickListener());

        statusBar = (StatusBarView) findViewById(R.id.status_bar);
        statusBar.setActivePlayerName((TextView) findViewById(R.id.active_player));
        statusBar.setNumberOfMoves((TextView) findViewById(R.id.number_of_moves));
        statusBar.setCurrentTurn((TextView) findViewById(R.id.current_turn));
        updateStatusBar();
    }

    public OnItemClickListener getOnItemClickListener() {
        return new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BoardAdapter adapter = (BoardAdapter) parent.getAdapter();
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
                Game.getInstance().getPlayer(Game.getInstance().getActivePlayer()).
                        lose(Game.getInstance().getCurrentTurn(), Player.PLAYER_SURRENDER);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFinalStats() {
        FragmentManager fm = getSupportFragmentManager();
        FinalStatsDialog finalStatsDialog = new FinalStatsDialog();
        finalStatsDialog.setCancelable(false);
        finalStatsDialog.show(fm, "final stats dialog");
    }

    @Subscribe
    public void playerLoseAction(PlayerLoseEvent event) {
        Toast toast = Toast.makeText(this, event.getPlayer().getName()
                + getString(R.string.player_lose), Toast.LENGTH_LONG);
        toast.show();
    }

    @Subscribe
    public void gameFinishAction(GameFinishEvent event) {
        Game.getInstance().finish();
        showFinalStats();
    }
}

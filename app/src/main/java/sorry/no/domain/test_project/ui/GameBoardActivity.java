package sorry.no.domain.test_project.ui;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import sorry.no.domain.test_project.Board;
import sorry.no.domain.test_project.BoardImageAdapter;
import sorry.no.domain.test_project.Game;
import sorry.no.domain.test_project.InvalidCellException;
import sorry.no.domain.test_project.InvalidMoveException;
import sorry.no.domain.test_project.InvalidPositionException;
import sorry.no.domain.test_project.R;

public class GameBoardActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setNumColumns(Game.getInstance().getBoard().getWidth() + 1);
        gridview.setAdapter(new BoardImageAdapter(this));
        gridview.setOnItemClickListener(getOnItemClickListener());
    }

    public OnItemClickListener getOnItemClickListener() {
        return (parent, view, position, id) -> {
            BoardImageAdapter adapter = (BoardImageAdapter) parent.getAdapter();
            try {
                Toast toast;
                switch (Game.getInstance().makeAMove(Game.getInstance().getActivePlayer(),
                        adapter.getXByPosition(position), adapter.getYByPosition(position))) {
                    case Board.MARK_PLACED:
                    case Board.WALL_PLACED:
                        adapter.notifyDataSetChanged();
                        view.invalidate();
                        break;
                    case Game.GAME_FINISH_NO_MOVES:
                        Game.finish(Game.GAME_FINISH_NO_MOVES);
                        showFinalStats();
                        break;
                    case Game.GAME_FINISH_NO_MARKS:
                        Game.finish(Game.GAME_FINISH_NO_MARKS);
                        showFinalStats();
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
            } catch (InvalidPositionException | InvalidCellException | InvalidMoveException e) {
                Toast toast = Toast.makeText(parent.getContext(), e.toString(), Toast.LENGTH_LONG);
                toast.show();
            }
        };
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
                Game.finish(Game.GAME_FINISH_SURRENDER);
                showFinalStats();
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
}

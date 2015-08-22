package sorry.no.domain.test_project.ui;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BoardImageAdapter adapter = (BoardImageAdapter) parent.getAdapter();
                try {
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
                        case Board.ENEMY_WALL_HIT:
                        case Board.OWN_CROSS_HIT:
                        case Board.OWN_WALL_HIT:
                            //Todo alert user about invalid move
                    }
                } catch (InvalidPositionException e) {
                    //Todo something here
                } catch (InvalidCellException e) {
                    //Todo something here
                } catch (InvalidMoveException e) {
                    //Todo something here
                }
            }
        });
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
        finalStatsDialog.show(fm, "final stats dialog");
    }
}

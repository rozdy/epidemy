package com.rozdy.epidemy.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.rozdy.epidemy.R;
import com.rozdy.epidemy.logic.board.BoardImageAdapter;
import com.rozdy.epidemy.logic.board.BoardView;
import com.rozdy.epidemy.logic.game.Game;

/**
 * Created by hex on 8/1/2015 in the name of the Emperor!
 */
public class FinalStatsDialog extends DialogFragment {
    public FinalStatsDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.final_stats_congrats);
        builder.setMessage(Game.getInstance().getStats().getWinner().getName() + " "
                + getResources().getString(R.string.final_stats_message));
        builder.setPositiveButton(R.string.final_stats_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getActivity().onBackPressed();
            }
        });
        builder.setNegativeButton(R.string.final_stats_revenge, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Game.init();
                ((GameBoardActivity) getActivity()).updateStatusBar();
                BoardView boardView = (BoardView) getActivity().findViewById(R.id.board_view);
                ((BoardImageAdapter) boardView.getAdapter()).notifyDataSetChanged();
                boardView.invalidate();
                ((GameBoardActivity) getActivity()).updateStatusBar();
            }
        });
        return builder.create();
    }
}

package sorry.no.domain.test_project.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.GridView;

import sorry.no.domain.test_project.BoardImageAdapter;
import sorry.no.domain.test_project.Game;
import sorry.no.domain.test_project.R;

/**
 * Created by hex on 8/1/2015.
 */
public class FinalStatsDialog extends DialogFragment {
    public FinalStatsDialog() {
    }

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
                GridView gridview = (GridView) getActivity().findViewById(R.id.grid_view);
                ((BoardImageAdapter) gridview.getAdapter()).notifyDataSetChanged();
                gridview.invalidate();
            }
        });
        return builder.create();
    }
}

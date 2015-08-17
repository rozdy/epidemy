package sorry.no.domain.test_project.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import sorry.no.domain.test_project.GameOptions;
import sorry.no.domain.test_project.Options;
import sorry.no.domain.test_project.R;
import sorry.no.domain.test_project.UsersOptions;

public class OptionsDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";

    private OptionsContent.OptionsItem mItem;

    public OptionsDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = OptionsContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        if (mItem.id.equals(OptionsContent.GAME_OPTIONS_ID)) {
            rootView = inflater.inflate(R.layout.fragment_game_options_detail, container, false);
            initGameOptions(rootView);
        } else if (mItem.id.equals(OptionsContent.BOARD_OPTIONS_ID)) {
            rootView = inflater.inflate(R.layout.fragment_board_options_detail, container, false);
        } else if (mItem.id.equals(OptionsContent.USERS_OPTIONS_ID)) {
            rootView = inflater.inflate(R.layout.fragment_users_options_detail, container, false);
        } else { //Todo fix this case
            rootView = inflater.inflate(R.layout.fragment_users_options_detail, container, false);
        }

        return rootView;
    }

    private void initGameOptions(View rootView) {
        SeekBar numberOfPlayersSeekBar =
                (SeekBar) rootView.findViewById(R.id.number_of_players_seek_bar);
        numberOfPlayersSeekBar.setMax(UsersOptions.MAX_PLAYERS_NUMBER);
        numberOfPlayersSeekBar.setProgress(Options.getInstance().getGameOptions().getNumberOfPlayers());
        final TextView numberOfPlayers = (TextView) rootView.findViewById(R.id.number_of_players);
        numberOfPlayers.setText(Options.getInstance().getGameOptions().getNumberOfPlayers() + "");
        numberOfPlayersSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress >= UsersOptions.MIN_PLAYERS_NUMBER) {
                    Options.getInstance().getGameOptions().setNumberOfPlayers(progress);
                } else {
                    Options.getInstance().getGameOptions().setNumberOfPlayers(UsersOptions.MIN_PLAYERS_NUMBER);
                    seekBar.setProgress(UsersOptions.MIN_PLAYERS_NUMBER);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                numberOfPlayers.setText(Options.getInstance().getGameOptions().getNumberOfPlayers() + "");
            }
        });
        SeekBar numberOfMovesSeekBar =
                (SeekBar) rootView.findViewById(R.id.number_of_moves_seek_bar);
        numberOfMovesSeekBar.setMax(GameOptions.MAX_NUMBER_OF_MOVES);
        numberOfMovesSeekBar.setProgress(Options.getInstance().getGameOptions().getNumberOfMoves());
        final TextView numberOfMoves = (TextView) rootView.findViewById(R.id.number_of_moves);
        numberOfMoves.setText(Options.getInstance().getGameOptions().getNumberOfMoves() + "");
        numberOfMovesSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress >= GameOptions.MIN_NUMBER_OF_MOVES) {
                    Options.getInstance().getGameOptions().setNumberOfMoves(progress);
                } else {
                    Options.getInstance().getGameOptions().setNumberOfMoves(GameOptions.MIN_NUMBER_OF_MOVES);
                    seekBar.setProgress(GameOptions.MIN_NUMBER_OF_MOVES);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                numberOfMoves.setText(Options.getInstance().getGameOptions().getNumberOfMoves() + "");
            }
        });
    }
}

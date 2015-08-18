package sorry.no.domain.test_project.ui;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sorry.no.domain.test_project.BoardOptions;
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
            initBoardOptions(rootView);
        } else if (mItem.id.equals(OptionsContent.USERS_OPTIONS_ID)) {
            rootView = new TableLayout(getActivity());
            rootView.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT));
            initUsersOptions(rootView);
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

    public void initBoardOptions(View rootView) {
        Spinner boardSizeSpinner = (Spinner) rootView.findViewById(R.id.board_size_spinner);
        ArrayAdapter<BoardOptions> adapter = new ArrayAdapter<>(rootView.getContext(),
                android.R.layout.simple_spinner_item, BoardOptions.standardBoardSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boardSizeSpinner.setAdapter(adapter);
        int position = 0;
        while (!adapter.getItem(position).equals(Options.getInstance().getBoardOptions()) ||
                position == adapter.getCount()) {
            position++;
        }
        if (adapter.getItem(position).equals(Options.getInstance().getBoardOptions())) {
            boardSizeSpinner.setSelection(position);
        }
        boardSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BoardOptions boardSize = (BoardOptions) parent.getItemAtPosition(position);
                Options.getInstance().getBoardOptions().setWidth(boardSize.getWidth());
                Options.getInstance().getBoardOptions().setHeight(boardSize.getHeight());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //Todo add custom board sizes
    }

    public void initUsersOptions(View rootView) {
        for (int player = 0; player < UsersOptions.MAX_PLAYERS_NUMBER; player++) {
            TableRow tableRow = new TableRow(rootView.getContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            TextView playerNumberTextView = new TextView(rootView.getContext());
            playerNumberTextView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            playerNumberTextView.setText((player + 1) + ".");
            tableRow.addView(playerNumberTextView);
            EditText playerNameEditText = new EditText(rootView.getContext());
            playerNameEditText.setLayoutParams(
                    new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
            playerNameEditText.setTag(player);
            playerNameEditText.setText(Options.getInstance().getUsersOptions().getPlayer(player).getName());
            playerNameEditText.addTextChangedListener(new PlayerNameTextWatcher(playerNameEditText));
            tableRow.addView(playerNameEditText);
            //Todo add color picker
            ((TableLayout) rootView).addView(tableRow);
        }
    }

    public static class PlayerNameTextWatcher implements TextWatcher {
        private EditText editText;

        public PlayerNameTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            Options.getInstance().getUsersOptions().getPlayer((int) editText.getTag())
                    .setName(editText.getText().toString());
        }
    }
}

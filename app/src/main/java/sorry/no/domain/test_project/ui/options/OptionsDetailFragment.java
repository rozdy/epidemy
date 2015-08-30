package sorry.no.domain.test_project.ui.options;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import sorry.no.domain.test_project.Options;
import sorry.no.domain.test_project.R;
import sorry.no.domain.test_project.logic.board.BoardOptions;
import sorry.no.domain.test_project.logic.game.GameOptions;
import sorry.no.domain.test_project.logic.player.UsersOptions;
import yuku.ambilwarna.AmbilWarnaDialog;

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
        switch (mItem.id) {
            case OptionsContent.GAME_OPTIONS_ID:
                rootView = inflater.inflate(R.layout.fragment_game_options_detail, container, false);
                initGameOptions(rootView);
                break;
            case OptionsContent.BOARD_OPTIONS_ID:
                rootView = inflater.inflate(R.layout.fragment_board_options_detail, container, false);
                initBoardOptions(rootView);
                break;
            case OptionsContent.USERS_OPTIONS_ID:
                rootView = new TableLayout(getActivity());
                rootView.setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.MATCH_PARENT));
                initUsersOptions(rootView);
                break;
            default:  //Todo fix this case
                rootView = inflater.inflate(R.layout.fragment_users_options_detail, container, false);
                break;
        }
        return rootView;
    }

    private void initGameOptions(View rootView) {
        final TextView numberOfPlayersCaption = (TextView) rootView.findViewById(R.id.number_of_players_caption);
        numberOfPlayersCaption.setText(getString(R.string.number_of_players)
                + Options.getInstance().getGameOptions().getNumberOfPlayers());
        SeekBar numberOfPlayersSeekBar =
                (SeekBar) rootView.findViewById(R.id.number_of_players_seek_bar);
        numberOfPlayersSeekBar.setMax(UsersOptions.MAX_PLAYERS_NUMBER);
        numberOfPlayersSeekBar.setProgress(Options.getInstance().getGameOptions().getNumberOfPlayers());
        final TextView numberOfPlayers = (TextView) rootView.findViewById(R.id.number_of_players);
        numberOfPlayers.setVisibility(View.INVISIBLE);
        numberOfPlayersSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress >= UsersOptions.MIN_PLAYERS_NUMBER) {
                    Options.getInstance().getGameOptions().setNumberOfPlayers(progress);
                } else {
                    Options.getInstance().getGameOptions().setNumberOfPlayers(UsersOptions.MIN_PLAYERS_NUMBER);
                    seekBar.setProgress(UsersOptions.MIN_PLAYERS_NUMBER);
                }
                numberOfPlayers.setText(Options.getInstance().getGameOptions().getNumberOfPlayers() + "");
                int xPos = (seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight())
                        * seekBar.getProgress() / seekBar.getMax() + seekBar.getThumbOffset();
                numberOfPlayers.setPadding(xPos, 0, 0, 0); //Todo align to the center of thumb
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                numberOfPlayers.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                numberOfPlayers.setVisibility(View.INVISIBLE);
                numberOfPlayersCaption.setText(getString(R.string.number_of_players)
                        + Options.getInstance().getGameOptions().getNumberOfPlayers());
            }
        });
        final TextView numberOfMovesCaption = (TextView) rootView.findViewById(R.id.number_of_moves_caption);
        numberOfMovesCaption.setText(getString(R.string.number_of_moves)
                + Options.getInstance().getGameOptions().getNumberOfMoves());
        SeekBar numberOfMovesSeekBar =
                (SeekBar) rootView.findViewById(R.id.number_of_moves_seek_bar);
        numberOfMovesSeekBar.setMax(GameOptions.MAX_NUMBER_OF_MOVES);
        numberOfMovesSeekBar.setProgress(Options.getInstance().getGameOptions().getNumberOfMoves());
        final TextView numberOfMoves = (TextView) rootView.findViewById(R.id.number_of_moves);
        numberOfMoves.setVisibility(View.INVISIBLE);
        numberOfMovesSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress >= GameOptions.MIN_NUMBER_OF_MOVES) {
                    Options.getInstance().getGameOptions().setNumberOfMoves(progress);
                } else {
                    Options.getInstance().getGameOptions().setNumberOfMoves(GameOptions.MIN_NUMBER_OF_MOVES);
                    seekBar.setProgress(GameOptions.MIN_NUMBER_OF_MOVES);
                }
                numberOfMoves.setText(Options.getInstance().getGameOptions().getNumberOfMoves() + "");
                int xPos = (seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight())
                        * seekBar.getProgress() / seekBar.getMax() + seekBar.getThumbOffset();
                numberOfMoves.setPadding(xPos, 0, 0, 0); //Todo align to the center of thumb
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                numberOfMoves.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                numberOfMoves.setVisibility(View.INVISIBLE);
                numberOfMovesCaption.setText(getString(R.string.number_of_moves)
                        + Options.getInstance().getGameOptions().getNumberOfMoves());
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
        Switch showCellNumbers = (Switch) rootView.findViewById(R.id.show_cell_numbers);
        showCellNumbers.setChecked(Options.getInstance().getBoardOptions().getShowCellNumeration());
        showCellNumbers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Options.getInstance().getBoardOptions().setShowCellNumeration(isChecked);
            }
        });
        //Todo add custom board sizes
    }

    public void initUsersOptions(final View rootView) {
        for (int player = 0; player < UsersOptions.MAX_PLAYERS_NUMBER; player++) {
            final TableRow tableRow = new TableRow(rootView.getContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            TextView playerNumberTextView = new TextView(rootView.getContext());
            playerNumberTextView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            playerNumberTextView.setText((player + 1) + ".");
            tableRow.addView(playerNumberTextView);
            EditText playerNameEditText = new EditText(rootView.getContext());
            InputFilter filter = new InputFilter() {
                public CharSequence filter(CharSequence source, int start, int end,
                                           Spanned dest, int dstart, int dend) {
                    for (int i = start; i < end; i++) {
                        if (source.charAt(i) == '\n') {
                            return "";
                        }
                    }
                    return null;
                }
            };
            playerNameEditText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(20)});
            playerNameEditText.setLayoutParams(
                    new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
            playerNameEditText.setTag(player);
            playerNameEditText.setText(Options.getInstance().getUsersOptions().getPlayer(player).getName());
            playerNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        return; // doing nothing when getting focus
                    }
                    EditText editText = ((EditText) v);
                    String newName = editText.getText().toString().trim();
                    for (int index = 0; index < UsersOptions.MAX_PLAYERS_NUMBER; index++) {
                        if (index == (int) editText.getTag()) {
                            continue;
                        }
                        if (Options.getInstance().getUsersOptions().getPlayer(index).getName().equals(newName)) {
                            editText.setText(Options.getInstance().getUsersOptions().getPlayer((int) editText.getTag()).getName());
                            editText.setError("Player name is already in use");
                            return;
                        }
                    }
                    Options.getInstance().getUsersOptions().getPlayer((int) editText.getTag())
                            .setName(newName);
                }
            });
            tableRow.addView(playerNameEditText);
            View colorPicker = new View(rootView.getContext());
            colorPicker.setLayoutParams(new TableRow.LayoutParams(30, 30)); //Todo fix params
            colorPicker.setPadding(3, 3, 3, 3);
            colorPicker.setBackgroundColor(Options.getInstance().getUsersOptions().getPlayer(player).getColor());
            colorPicker.setTag(player);
            colorPicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showColorPickerDialog(v);
                }
            });
            tableRow.addView(colorPicker);
            ((TableLayout) rootView).addView(tableRow);
        }
    }

    private void showColorPickerDialog(final View v) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(v.getContext(),
                Options.getInstance().getUsersOptions().getPlayer((int) v.getTag()).getColor(),
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        //Todo check user colors compatibility (avoid similar colors)
                        Options.getInstance().getUsersOptions().getPlayer((int) v.getTag()).setColor(color);
                        v.setBackgroundColor(Options.getInstance().getUsersOptions().getPlayer((int) v.getTag()).getColor());
                    }

                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                    }

                });
        dialog.show();
    }
}

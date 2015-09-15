package com.rozdy.epidemy.ui.options;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rozdy.epidemy.Options;
import com.rozdy.epidemy.R;
import com.rozdy.epidemy.logic.board.BoardOptions;
import com.rozdy.epidemy.logic.game.GameOptions;
import com.rozdy.epidemy.logic.player.Player;
import com.rozdy.epidemy.logic.player.UsersOptions;

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
            case OptionsContent.DEFAULTS_OPTIONS_ID:
                rootView = inflater.inflate(R.layout.fragment_defaults_options_detail, container, false);
                initDefaultsOptions(rootView);
                break;
            default:
                rootView = new TextView(getActivity());
                rootView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                ((TextView) rootView).setText(getString(R.string.error_options_detail));
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

    private void initBoardOptions(final View rootView) {

        final TextView boardWidthCaption = (TextView) rootView.findViewById(R.id.board_width_caption);
        boardWidthCaption.setText(getString(R.string.board_width)
                + Options.getInstance().getBoardOptions().getWidth());
        SeekBar boardWidthSeekBar =
                (SeekBar) rootView.findViewById(R.id.board_width_seek_bar);
        boardWidthSeekBar.setMax(BoardOptions.MAX_WIDTH);
        boardWidthSeekBar.setProgress(Options.getInstance().getBoardOptions().getWidth());
        final TextView boardWidth = (TextView) rootView.findViewById(R.id.board_width);
        boardWidth.setVisibility(View.INVISIBLE);
        boardWidthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress >= BoardOptions.MIN_WIDTH) {
                    Options.getInstance().getBoardOptions().setWidth(progress);
                    if (Options.getInstance().getBoardOptions().getSquareBoard()) {
                        makeSquareBoard(rootView, progress);
                    }
                } else {
                    Options.getInstance().getBoardOptions().setWidth(BoardOptions.MIN_WIDTH);
                    seekBar.setProgress(BoardOptions.MIN_WIDTH);
                }
                boardWidth.setText(Options.getInstance().getBoardOptions().getWidth() + "");
                int xPos = (seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight())
                        * seekBar.getProgress() / seekBar.getMax() + seekBar.getThumbOffset();
                boardWidth.setPadding(xPos, 0, 0, 0); //Todo align to the center of thumb
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                boardWidth.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                boardWidth.setVisibility(View.INVISIBLE);
                boardWidthCaption.setText(getString(R.string.board_width)
                        + Options.getInstance().getBoardOptions().getWidth());
            }
        });

        final TextView boardHeightCaption = (TextView) rootView.findViewById(R.id.board_height_caption);
        boardHeightCaption.setText(getString(R.string.board_height)
                + Options.getInstance().getBoardOptions().getHeight());
        SeekBar boardHeightSeekBar =
                (SeekBar) rootView.findViewById(R.id.board_height_seek_bar);
        boardHeightSeekBar.setMax(BoardOptions.MAX_HEIGHT);
        boardHeightSeekBar.setProgress(Options.getInstance().getBoardOptions().getHeight());
        final TextView boardHeight = (TextView) rootView.findViewById(R.id.board_height);
        boardHeight.setVisibility(View.INVISIBLE);
        boardHeightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress >= BoardOptions.MIN_HEIGHT) {
                    Options.getInstance().getBoardOptions().setHeight(progress);
                    if (Options.getInstance().getBoardOptions().getSquareBoard()) {
                        makeSquareBoard(rootView, progress);
                    }
                } else {
                    Options.getInstance().getBoardOptions().setHeight(BoardOptions.MIN_HEIGHT);
                    seekBar.setProgress(BoardOptions.MIN_HEIGHT);
                }
                boardHeight.setText(Options.getInstance().getBoardOptions().getHeight() + "");
                int xPos = (seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight())
                        * seekBar.getProgress() / seekBar.getMax() + seekBar.getThumbOffset();
                boardHeight.setPadding(xPos, 0, 0, 0); //Todo align to the center of thumb
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                boardHeight.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                boardHeight.setVisibility(View.INVISIBLE);
                boardHeightCaption.setText(getString(R.string.board_height)
                        + Options.getInstance().getBoardOptions().getHeight());
            }
        });

        Switch squareBoard = (Switch) rootView.findViewById(R.id.square_board);
        squareBoard.setChecked(Options.getInstance().getBoardOptions().getSquareBoard());
        squareBoard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Options.getInstance().getBoardOptions().setSquareBoard(isChecked);
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
    }

    private void makeSquareBoard(View rootView, int progress) {
        if (progress >= BoardOptions.MIN_WIDTH && progress <= BoardOptions.MAX_WIDTH) {
            Options.getInstance().getBoardOptions().setWidth(progress);
        }
        if (progress >= BoardOptions.MIN_HEIGHT && progress <= BoardOptions.MAX_HEIGHT) {
            Options.getInstance().getBoardOptions().setHeight(progress);
        }
        ((TextView) rootView.findViewById(R.id.board_width_caption)).setText(getString(R.string.board_width)
                + Options.getInstance().getBoardOptions().getWidth());
        ((SeekBar) rootView.findViewById(R.id.board_width_seek_bar)).setProgress(Options.getInstance().getBoardOptions().getWidth());
        ((TextView) rootView.findViewById(R.id.board_height_caption)).setText(getString(R.string.board_height)
                + Options.getInstance().getBoardOptions().getHeight());
        ((SeekBar) rootView.findViewById(R.id.board_height_seek_bar)).setProgress(Options.getInstance().getBoardOptions().getHeight());
    }

    private void initUsersOptions(final View rootView) {
        int padding = (int) getResources().getDimension(R.dimen.padding);
        for (int player = 0; player < UsersOptions.MAX_PLAYERS_NUMBER; player++) {
            final TableRow tableRow = new TableRow(rootView.getContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.setPadding(padding, padding, padding, padding);
            TextView playerNumberTextView = new TextView(rootView.getContext());
            playerNumberTextView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            playerNumberTextView.setPadding(padding, padding, padding, padding);
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
            playerNameEditText.setPadding(padding, padding, padding, padding);
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
            colorPicker.setLayoutParams(new TableRow.LayoutParams((int) getResources().getDimension(R.dimen.color_picker),
                    (int) getResources().getDimension(R.dimen.color_picker)));
            colorPicker.setPadding(padding, padding, padding, padding);
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


    private void initDefaultsOptions(View rootView) {
        Button resetToDefaults = (Button) rootView.findViewById(R.id.reset_to_defaults_button);
        resetToDefaults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Player.resetIdCounter();
                Options.init();
            }
        });
    }
}

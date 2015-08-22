package sorry.no.domain.test_project.logic.board;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by hex on 8/21/2015.
 */
public class StatusBarView extends LinearLayout {
    private TextView activePlayerName, numberOfMoves, currentTurn;

    public StatusBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextView getActivePlayerName() {
        return activePlayerName;
    }

    public void setActivePlayerName(TextView activePlayerName) {
        this.activePlayerName = activePlayerName;
    }

    public TextView getNumberOfMoves() {
        return numberOfMoves;
    }

    public void setNumberOfMoves(TextView numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }

    public TextView getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(TextView currentTurn) {
        this.currentTurn = currentTurn;
    }

}

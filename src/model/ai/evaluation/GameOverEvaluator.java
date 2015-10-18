package model.ai.evaluation;

import model.Board;
import model.PieceColor;

public class GameOverEvaluator extends BoardEvaluator {

    public GameOverEvaluator() {
        super();
    }

    public GameOverEvaluator(double weight) {
        super(weight);
    }

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {

        if (theBoard.isEndState(color)) {
            return -1.0 * this.weight;
        } else if (theBoard.isEndState(color.getOppositeColor())) {
            return 1.0 * this.weight;
        } else {
            return 0.0;
        }
    }

}

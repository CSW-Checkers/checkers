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
        double value = 0.0;

        PieceColor winner = theBoard.determineWinner();

        if (winner == null) {
            return value;
        }

        if (winner == color) {
            return 1.0 * this.weight;
        }

        return -1.0 * this.weight;
    }

}

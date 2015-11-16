package model.ai.evaluation;

import model.Board;
import model.PieceColor;

public class GameOverEvaluator implements BoardEvaluatorInterface {
    private GameOverEvaluator instance = null;

    private GameOverEvaluator() {
    }

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {
        if (theBoard.isEndState(color)) {
            return -1.0;
        } else if (theBoard.isEndState(color.getOppositeColor())) {
            return 1.0;
        } else {
            return 0.0;
        }
    }

    @Override
    public BoardEvaluatorInterface getInstance() {
        if (this.instance == null) {
            this.instance = new GameOverEvaluator();
        }

        return this.instance;
    }

}

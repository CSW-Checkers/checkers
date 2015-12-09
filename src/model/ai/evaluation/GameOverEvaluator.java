package model.ai.evaluation;

import model.Board;
import model.PieceColor;

public class GameOverEvaluator implements BoardEvaluatorInterface {
    private static GameOverEvaluator instance = null;

    public static BoardEvaluatorInterface getInstance() {
        if (instance == null) {
            instance = new GameOverEvaluator();
        }

        return instance;
    }

    private GameOverEvaluator() {
    }

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {
        if (theBoard.playerHasLost(color)) {
            return -1.0;
        } else if (theBoard.playerHasLost(color.getOppositeColor())) {
            return 1.0;
        } else {
            return 0.0;
        }
    }

}

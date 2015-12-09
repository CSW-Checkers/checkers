package model.ai.evaluation;

import model.Board;
import model.PieceColor;

public class PawnCountEvaluator implements BoardEvaluatorInterface {
    private static BoardEvaluatorInterface instance = null;

    public static BoardEvaluatorInterface getInstance() {
        if (instance == null) {
            instance = new PawnCountEvaluator();
        }

        return instance;
    }

    private PawnCountEvaluator() {
    }

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {
        double value = 0.0;
        
        value += theBoard.getNumberOfPawns(color);
        value -= theBoard.getNumberOfPawns(color.getOppositeColor());

        return value;
    }

}

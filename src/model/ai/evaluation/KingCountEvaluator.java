package model.ai.evaluation;

import model.Board;
import model.PieceColor;

public class KingCountEvaluator implements BoardEvaluatorInterface {
    @Override
    public String toString() {
        return "KingCountEvaluator";
    }

    private static BoardEvaluatorInterface instance;

    public static BoardEvaluatorInterface getInstance() {
        if (instance == null) {
            instance = new KingCountEvaluator();
        }

        return instance;
    }

    private KingCountEvaluator() {
    }

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {
        double value = 0.0;

        value += theBoard.getKingCount(color);
        value -= theBoard.getKingCount(color.getOppositeColor());
        
        return value;
    }

}

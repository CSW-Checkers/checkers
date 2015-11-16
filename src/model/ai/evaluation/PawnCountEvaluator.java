package model.ai.evaluation;

import model.Board;
import model.PieceColor;
import model.PieceInterface;
import model.Square;

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

        for (final Square square : theBoard.getGameState()) {
            final PieceInterface piece = square.getOccupyingPiece();
            if (piece.getColor() == color) {
                value += 1.0;
            } else if (piece.getColor() == color.getOppositeColor()) {
                value -= 1.0;
            }
        }
        return value;
    }

}

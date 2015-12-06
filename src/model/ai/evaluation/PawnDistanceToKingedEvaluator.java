package model.ai.evaluation;

import model.Board;
import model.PieceColor;
import model.PieceInterface;
import model.Square;

public class PawnDistanceToKingedEvaluator implements BoardEvaluatorInterface {

    private static BoardEvaluatorInterface instance = null;

    public static BoardEvaluatorInterface getInstance() {
        if (instance == null) {
            instance = new PawnDistanceToKingedEvaluator();
        }
        return instance;
    }

    private PawnDistanceToKingedEvaluator() {
    }

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {
        double score = 0.0;

        for (final Square square : theBoard.getGameState()) {

            if (square.isOccupied()) {
                final PieceInterface piece = square.getOccupyingPiece();

                if (!piece.isKing()) { // TODO Figure out how to get pieces kinged and let them off
                                       // back row
                    final int rowNumber = square.getRowNumber();
                    int distanceToOtherSide = 0;

                    if (piece.getColor() == PieceColor.BLACK) {
                        distanceToOtherSide = 8 - rowNumber;
                    } else {
                        distanceToOtherSide = rowNumber - 1;
                    }

                    final double value = 8 - distanceToOtherSide;
                    if (piece.getColor() == color) {
                        score += value;
                    } else {
                        score -= value;
                    }
                }
            }
        }

        return score;
    }

}

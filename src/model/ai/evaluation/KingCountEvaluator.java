package model.ai.evaluation;

import model.Board;
import model.PieceColor;
import model.PieceInterface;
import model.Square;

public class KingCountEvaluator implements BoardEvaluatorInterface {
    private BoardEvaluatorInterface instance;

    private KingCountEvaluator() {
    }

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {
        double value = 0.0;

        for (final Square square : theBoard.getGameState()) {
            final PieceInterface piece = square.getOccupyingPiece();
            if ((piece.getColor() == color) && piece.isKing()) {
                value += 1.0;
            } else if ((piece.getColor() == color.getOppositeColor()) && piece.isKing()) {
                value -= 1.0;
            }
        }
        return value;
    }

    @Override
    public BoardEvaluatorInterface getInstance() {
        if (this.instance == null) {
            this.instance = new KingCountEvaluator();
        }

        return this.instance;
    }

}

package model.ai.evaluation;

import model.Board;
import model.PieceColor;
import model.PieceInterface;
import model.Square;

public class PawnCountEvaluator extends BoardEvaluator {

    public PawnCountEvaluator() {
        super();
    }

    public PawnCountEvaluator(double weight) {
        super(weight);
    }

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {
        double value = 0.0;

        for (Square square : theBoard.getGameState()) {
            PieceInterface piece = square.getOccupyingPiece();
            if (piece.getColor() == color) {
                value += 1.0;
            } else if (piece.getColor() == color.getOppositeColor()) {
                value -= 1.0;
            }
        }
        return value * this.weight;
    }

}

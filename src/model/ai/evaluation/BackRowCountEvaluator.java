package model.ai.evaluation;

import model.Board;
import model.PieceColor;
import model.PieceInterface;
import model.Square;

public class BackRowCountEvaluator extends BoardEvaluator {

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {
        double value = 0.0;

        for (Square square : theBoard.getGameState()) {
            PieceInterface piece = square.getOccupyingPiece();

            // if black piece is on black edge of board
            if (square.isOnBlackEdgeOfBoard() && piece.getColor() == PieceColor.BLACK) {
                if (color == PieceColor.BLACK) {
                    value += 1.0;
                } else {
                    value -= 1.0;
                }
            }

            // if white piece is on white edge of board
            else if (square.isOnWhiteEdgeOfBoard() && piece.getColor() == PieceColor.WHITE) {
                if (color == PieceColor.WHITE) {
                    value += 1.0;
                } else {
                    value -= 1.0;
                }
            }
        }

        return value * this.weight;
    }
}

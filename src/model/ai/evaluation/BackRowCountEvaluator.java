package model.ai.evaluation;

import model.Board;
import model.PieceColor;
import model.PieceInterface;
import model.Square;

public class BackRowCountEvaluator extends BoardEvaluatorDecorator {

    public BackRowCountEvaluator(BoardEvaluatorInterface boardEvaulator) {
        super(boardEvaulator);
    }

    public BackRowCountEvaluator(BoardEvaluatorInterface boardEvaulator, double weight) {
        super(boardEvaulator, weight);
    }

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {
        double value = 0.0;

        for (Square square : theBoard.getGameState()) {
            PieceInterface piece = square.getOccupyingPiece();

            // if black piece is on black edge of board and player-color is black
            if (square.isOnBlackEdgeOfBoard() && color == PieceColor.BLACK
                    && piece.getColor() == PieceColor.BLACK) {
                value += 1.0;
            }
            // if white piece is on white edge of board and player-color is white
            else if (square.isOnWhiteEdgeOfBoard() && color == PieceColor.WHITE
                    && piece.getColor() == PieceColor.WHITE) {
                value += 1.0;
            }
        }
        return this.boardEvaluator.evaluateBoard(theBoard, color) + value * this.weight;
    }

}

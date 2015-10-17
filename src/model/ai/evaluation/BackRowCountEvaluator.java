package model.ai.evaluation;

import model.Board;
import model.PieceColor;

public class BackRowCountEvaluator extends BoardEvaluator {
    public BackRowCountEvaluator() {
        super();
    }

    public BackRowCountEvaluator(double weight) {
        super(weight);
    }

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {
        double value = 0.0;

        for (int i = 1; i <= 4; i++) {

            // if black piece is on black edge of board
            if (theBoard.getPiece(i).getColor() == PieceColor.BLACK) {
                if (color == PieceColor.BLACK) {
                    value += 1.0;
                } else {
                    value -= 1.0;
                }
            }
        }

        for (int i = 29; i <= 32; i++) {

            // if white piece is on white edge of board
            if (theBoard.getPiece(i).getColor() == PieceColor.WHITE) {
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

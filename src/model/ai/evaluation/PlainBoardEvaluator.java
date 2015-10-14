package model.ai.evaluation;

import model.Board;
import model.PieceColor;

public class PlainBoardEvaluator implements BoardEvaluatorInterface {

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {
        return 0;
    }

}

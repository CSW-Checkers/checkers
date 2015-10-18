package model.ai.evaluation;

import model.Board;
import model.PieceColor;

public class BoardEvaluatorSummator extends BoardEvaluatorAggregator {

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {
        double value = 0.0;
        for (BoardEvaluatorInterface boardEvaluatorInterface : this.boardEvalutors) {
            value += boardEvaluatorInterface.evaluateBoard(theBoard, color);
        }
        return value;
    }

}

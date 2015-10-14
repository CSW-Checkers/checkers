package model.ai;

import model.Board;
import model.PieceColor;

public abstract class BoardEvaluatorDecorator implements BoardEvaluatorInterface {
    protected BoardEvaluatorInterface boardEvaluator;

    public BoardEvaluatorDecorator(BoardEvaluatorInterface boardEvaulator) {
        this.boardEvaluator = boardEvaulator;
    }

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {
        return this.boardEvaluator.evaluateBoard(theBoard, color);
    }

}

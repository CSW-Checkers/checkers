package model.ai.evaluation;

import model.Board;
import model.PieceColor;

public abstract class BoardEvaluatorDecorator implements BoardEvaluatorInterface {
    protected BoardEvaluatorInterface boardEvaluator;
    protected double weight;

    public BoardEvaluatorDecorator(BoardEvaluatorInterface boardEvaulator) {
        this.boardEvaluator = boardEvaulator;
        this.weight = 1.0;
    }

    public BoardEvaluatorDecorator(BoardEvaluatorInterface boardEvaulator, double weight) {
        this.boardEvaluator = boardEvaulator;
        this.weight = weight;
    }

    @Override
    public double evaluateBoard(Board theBoard, PieceColor color) {
        return this.boardEvaluator.evaluateBoard(theBoard, color);
    }

}

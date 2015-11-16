package model;

import java.util.HashMap;

import model.ai.evaluation.BoardEvaluatorAggregator;
import model.ai.evaluation.BoardEvaluatorInterface;

public class Strategy {
    private final BoardEvaluatorAggregator aggregator;
    private final PieceColor color;
    private HashMap<BoardEvaluatorInterface, Float> evaluatorWeightMap;

    public Strategy(BoardEvaluatorAggregator aggregator, PieceColor color) {
        this.aggregator = aggregator;
        this.color = color;
    }

    public BoardEvaluatorAggregator getAggregator() {
        return this.aggregator;
    }

    public void getBestMove(Board board, PieceColor color) {
        // TODO
    }

    public PieceColor getColor() {
        return this.color;
    }

    public HashMap<BoardEvaluatorInterface, Float> getEvaluatorWeightMap() {
        return this.evaluatorWeightMap;
    }

}

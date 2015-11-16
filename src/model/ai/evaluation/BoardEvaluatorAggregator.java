package model.ai.evaluation;

import model.Board;
import model.Strategy;

public interface BoardEvaluatorAggregator {
    public double evaluateBoard(Strategy strategy, Board theBoard);
}

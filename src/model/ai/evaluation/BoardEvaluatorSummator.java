package model.ai.evaluation;

import java.util.HashMap;
import java.util.Map;

import model.Board;
import model.PieceColor;
import model.Strategy;

public class BoardEvaluatorSummator implements BoardEvaluatorAggregator {
    public static int count = 0;

    @Override
    public double evaluateBoard(Strategy strategy, Board theBoard) {
        double value = 0.0;
        final PieceColor color = strategy.getColor();
        final HashMap<BoardEvaluatorInterface, Double> weightMap = strategy.getEvaluatorWeightMap();

        for (final Map.Entry<BoardEvaluatorInterface, Double> entry : weightMap.entrySet()) {
            final BoardEvaluatorInterface evaluator = entry.getKey();
            final double weight = entry.getValue().doubleValue();
            value += evaluator.evaluateBoard(theBoard, color) * weight;
            count++;
        }

        return value;
    }

}

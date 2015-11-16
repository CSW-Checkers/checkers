package model.ai.evaluation;

import java.util.HashMap;
import java.util.Map;

import model.Board;
import model.PieceColor;
import model.Strategy;

public class BoardEvaluatorSummator implements BoardEvaluatorAggregator {

    @Override
    public double evaluateBoard(Strategy strategy, Board theBoard) {
        double value = 0.0;
        final PieceColor color = strategy.getColor();
        final HashMap<BoardEvaluatorInterface, Float> weightMap = strategy.getEvaluatorWeightMap();

        for (final Map.Entry<BoardEvaluatorInterface, Float> entry : weightMap.entrySet()) {
            final BoardEvaluatorInterface evaluator = entry.getKey();
            final float weight = entry.getValue().floatValue();
            value += evaluator.evaluateBoard(theBoard, color) * weight;
        }

        return value;
    }

}

package model;

import java.util.HashMap;

import model.ai.evaluation.BackRowCountEvaluator;
import model.ai.evaluation.BoardEvaluatorAggregator;
import model.ai.evaluation.BoardEvaluatorInterface;
import model.ai.evaluation.GameOverEvaluator;
import model.ai.evaluation.KingCountEvaluator;
import model.ai.evaluation.PawnCountEvaluator;
import model.ai.evaluation.PawnDistanceToKingedEvaluator;

public class Strategy {
    private final BoardEvaluatorAggregator aggregator;
    private final PieceColor color;
    private HashMap<BoardEvaluatorInterface, Double> evaluatorWeightMap = new HashMap<>();

    public Strategy(BoardEvaluatorAggregator aggregator, PieceColor color) {
        this.aggregator = aggregator;
        this.color = color;

        // Placeholder initialization
        this.evaluatorWeightMap.put(PawnCountEvaluator.getInstance(), 2.0);
        this.evaluatorWeightMap.put(KingCountEvaluator.getInstance(), 4.0);
        this.evaluatorWeightMap.put(BackRowCountEvaluator.getInstance(), 1.0);
        this.evaluatorWeightMap.put(GameOverEvaluator.getInstance(), 1000.0);
        this.evaluatorWeightMap.put(PawnDistanceToKingedEvaluator.getInstance(), 0.25);

    }

    public Strategy(BoardEvaluatorAggregator aggregator, PieceColor color,
            HashMap<BoardEvaluatorInterface, Double> evaluatorWeightMap) {
        this.aggregator = aggregator;
        this.color = color;
        this.evaluatorWeightMap = evaluatorWeightMap;
    }

    public BoardEvaluatorAggregator getAggregator() {
        return this.aggregator;
    }

    public PieceColor getColor() {
        return this.color;
    }

    public HashMap<BoardEvaluatorInterface, Double> getEvaluatorWeightMap() {
        return this.evaluatorWeightMap;
    }

}

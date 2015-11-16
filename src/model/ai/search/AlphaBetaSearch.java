package model.ai.search;

import model.Board;
import model.MoveInterface;
import model.PieceColor;
import model.Strategy;
import model.ai.evaluation.BoardEvaluatorAggregator;

public class AlphaBetaSearch {
    private final BoardEvaluatorAggregator aggregator;
    private final int depthLimit;
    private final PieceColor playerMakingMove;
    private final AlphaBetaSearchNode root;
    private final Strategy strategy;

    public AlphaBetaSearch(Board startingState, Strategy strategy, int depthLimit) {
        this.root = new AlphaBetaSearchNode(startingState, 0, this.playerMakingMove);
        this.strategy = strategy;
        this.aggregator = strategy.getAggregator();
        this.playerMakingMove = strategy.getColor();
        this.depthLimit = depthLimit;
    }

    public MoveInterface alphaBetaSearch() {
        final double bestValue = this.maxValue(this.root, Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY);
        return this.getBestMove(bestValue);
    }

    private double evaluate(Board theBoard, PieceColor color) {
        return this.aggregator.evaluateBoard(this.strategy, theBoard);
    }

    private MoveInterface getBestMove(double bestValue) {
        MoveInterface bestMove = null;

        for (final AlphaBetaSearchNode node : this.root.getChildren()) {
            if ((node != null) && (node.getValue() == bestValue)) {
                bestMove = node.getMoveThatGotToThisState();
                break;
            }
        }
        return bestMove;
    }

    public double maxValue(AlphaBetaSearchNode node, double alpha, double beta) {
        if ((node.getDepthLevel() == this.depthLimit)
                || node.getBoard().playerHasLost(node.getCurrentPlayersColor())) {
            final double value = this.evaluate(node.getBoard(), this.playerMakingMove);
            node.setValue(value);
            return value;
        }
        node.setValue(Double.NEGATIVE_INFINITY);
        for (final AlphaBetaSearchNode childNode : node.getChildren()) {
            node.setValue(Math.max(node.getValue(), this.minValue(childNode, alpha, beta)));
            if (node.getValue() >= beta) {
                return node.getValue();
            }
            alpha = Math.max(alpha, node.getValue());
        }
        return node.getValue();
    }

    private double minValue(AlphaBetaSearchNode node, double alpha, double beta) {
        if ((node.getDepthLevel() == this.depthLimit)
                || node.getBoard().playerHasLost(node.getCurrentPlayersColor())) {
            final double value = this.evaluate(node.getBoard(), this.playerMakingMove);
            node.setValue(value);
            return value;
        }
        node.setValue(Double.POSITIVE_INFINITY);
        for (final AlphaBetaSearchNode childNode : node.getChildren()) {
            node.setValue(Math.min(node.getValue(), this.maxValue(childNode, alpha, beta)));
            if (node.getValue() <= alpha) {
                return node.getValue();
            }
            beta = Math.min(beta, node.getValue());
        }
        return node.getValue();
    }
}

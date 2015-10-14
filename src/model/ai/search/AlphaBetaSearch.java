package model.ai.search;

import model.Board;
import model.MoveInterface;
import model.PieceColor;
import model.ai.evaluation.BoardEvaluatorInterface;

public class AlphaBetaSearch {
    private int depthLimit;
    private BoardEvaluatorInterface evaluator;
    private PieceColor playerMakingMove;
    private AlphaBetaSearchNode root;

    public AlphaBetaSearch(Board startingState, PieceColor playerMakingMove,
            BoardEvaluatorInterface evaluator, int depthLimit) {
        this.root = new AlphaBetaSearchNode(startingState, 0, playerMakingMove);
        this.evaluator = evaluator;
        this.playerMakingMove = playerMakingMove;
        this.depthLimit = depthLimit;
    }

    public MoveInterface alphaBetaSearch() {
        double bestValue = this.maxValue(this.root, Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY);
        return this.getBestMove(bestValue);
    }

    private double evaluate(Board theBoard, PieceColor color) {
        return this.evaluator.evaluateBoard(theBoard, color);
    }

    private MoveInterface getBestMove(double bestValue) {
        MoveInterface bestMove = null;

        for (AlphaBetaSearchNode node : this.root.getChildren()) {
            if (node != null && node.getValue() == bestValue) {
                bestMove = node.getMoveThatGotToThisState();
                break;
            }
        }
        return bestMove;
    }

    public double maxValue(AlphaBetaSearchNode node, double alpha, double beta) {
        if (node.getDepthLevel() == this.depthLimit
                || node.getBoard().isEndState(node.getCurrentPlayersColor())) {
            double value = this.evaluate(node.getBoard(), this.playerMakingMove);
            node.setValue(value);
            return value;
        }
        node.setValue(Double.NEGATIVE_INFINITY);
        for (AlphaBetaSearchNode childNode : node.getChildren()) {
            node.setValue(Math.max(node.getValue(), this.minValue(childNode, alpha, beta)));
            if (node.getValue() >= beta) {
                return node.getValue();
            }
            alpha = Math.max(alpha, node.getValue());
        }
        return node.getValue();
    }

    private double minValue(AlphaBetaSearchNode node, double alpha, double beta) {
        if (node.getDepthLevel() == this.depthLimit
                || node.getBoard().isEndState(node.getCurrentPlayersColor())) {
            double value = this.evaluate(node.getBoard(), this.playerMakingMove);
            node.setValue(value);
            return value;
        }
        node.setValue(Double.POSITIVE_INFINITY);
        for (AlphaBetaSearchNode childNode : node.getChildren()) {
            node.setValue(Math.min(node.getValue(), this.maxValue(childNode, alpha, beta)));
            if (node.getValue() <= alpha) {
                return node.getValue();
            }
            beta = Math.min(beta, node.getValue());
        }
        return node.getValue();
    }
}

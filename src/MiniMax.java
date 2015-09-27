
public class MiniMax {
    private MiniMaxNode root;
    private BoardEvaluator evaluator;

    public MiniMax(Board startingState, PieceColor playerMakingMove, BoardEvaluator evaluator,
            int depthLimit) {
        root = new MiniMaxNode(startingState, depthLimit, playerMakingMove);
        this.evaluator = evaluator;
    }

    public MoveInterface alphaBetaSearch() {
        double bestValue = maxValue(this.root, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        return getBestMove(bestValue);
    }

    public double maxValue(MiniMaxNode node, double alpha, double beta) {
        if (node.isLeaf()) {
            return evaluate(node.getBoard());
        }
        node.setValue(Double.NEGATIVE_INFINITY);
        for (MiniMaxNode childNode : node.getChildren()) {
            node.setValue(Math.max(node.getValue(), minValue(childNode, alpha, beta)));
            if (node.getValue() >= beta) {
                return node.getValue();
            }
            alpha = Math.max(alpha, node.getValue());
        }
        return node.getValue();
    }

    private double minValue(MiniMaxNode node, double alpha, double beta) {
        if (node.isLeaf()) {
            return evaluate(node.getBoard());
        }
        node.setValue(Double.POSITIVE_INFINITY);
        for (MiniMaxNode childNode : node.getChildren()) {
            node.setValue(Math.min(node.getValue(), maxValue(childNode, alpha, beta)));
            if (node.getValue() <= alpha) {
                return node.getValue();
            }
            beta = Math.min(beta, node.getValue());
        }
        return node.getValue();
    }

    private double evaluate(Board theBoard) {
        return this.evaluator.evaluateBoard(theBoard);
    }

    private MoveInterface getBestMove(double bestValue) {
        MoveInterface bestMove = null;
        for (MiniMaxNode node : this.root.getChildren()) {
            if (node != null && node.getValue() == bestValue) {
                bestMove = node.getMoveThatGotToThisState();
                break;
            }
        }
        return bestMove;
    }
}

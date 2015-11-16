package model;

import model.ai.evaluation.BackRowCountEvaluator;
import model.ai.evaluation.BoardEvaluatorAggregator;
import model.ai.evaluation.BoardEvaluatorSummator;
import model.ai.evaluation.GameOverEvaluator;
import model.ai.evaluation.KingCountEvaluator;
import model.ai.evaluation.PawnCountEvaluator;
import model.ai.search.AlphaBetaSearch;

public class ComputerPlayer implements Player {
    private final PieceColor color;
    private final Strategy strategy;

    public ComputerPlayer(PieceColor color) {
        this.color = color;
        final BoardEvaluatorAggregator aggregator = new BoardEvaluatorSummator();
        this.strategy = new Strategy(aggregator, color);
        aggregator.addBoardEvaluator(new PawnCountEvaluator(1.0));
        aggregator.addBoardEvaluator(new KingCountEvaluator(3.0));
        aggregator.addBoardEvaluator(new GameOverEvaluator(1000.0));
        aggregator.addBoardEvaluator(new BackRowCountEvaluator(2.0));
    }

    @Override
    public PieceColor getColor() {
        return this.color;
    }

    @Override
    public MoveInterface makeMove(Board currentBoard) {

        final MoveInterface moveToMake = new AlphaBetaSearch(currentBoard, this.getColor(),
                new B, 8).alphaBetaSearch();

        currentBoard.movePiece(moveToMake);
        this.printMove(moveToMake);
        return moveToMake;
    }

    private void printMove(MoveInterface moveToMake) {
        System.out.println(this.color.toString() + ": " + moveToMake);
    }

}

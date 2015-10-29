package model;

import model.ai.evaluation.BoardEvaluatorAggregator;
import model.ai.evaluation.BoardEvaluatorSummator;
import model.ai.evaluation.KingCountEvaluator;
import model.ai.evaluation.PawnCountEvaluator;
import model.ai.search.AlphaBetaSearch;

public class ComputerPlayer implements Player {
    private final PieceColor color;

    public ComputerPlayer(PieceColor color) {
        this.color = color;
    }

    @Override
    public PieceColor getColor() {
        return this.color;
    }

    @Override
    public MoveInterface makeMove(Board currentBoard) {

        BoardEvaluatorAggregator boardAgg = new BoardEvaluatorSummator();
        boardAgg.addBoardEvaluator(new PawnCountEvaluator());
        boardAgg.addBoardEvaluator(new KingCountEvaluator());

        final MoveInterface moveToMake = new AlphaBetaSearch(currentBoard, this.getColor(),
                boardAgg, 8).alphaBetaSearch();

        currentBoard.movePiece(moveToMake);
        this.printMove(moveToMake);
        return moveToMake;
    }

    private void printMove(MoveInterface moveToMake) {
        System.out.println(this.color.toString() + ": " + moveToMake);
    }

}

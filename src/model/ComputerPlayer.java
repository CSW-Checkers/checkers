package model;

import model.ai.evaluation.BoardEvaluatorSummator;
import model.ai.search.AlphaBetaSearch;

public class ComputerPlayer implements Player {
    private final PieceColor color;
    private final Strategy strategy;

    public ComputerPlayer(PieceColor color) {
        this.color = color;
        this.strategy = new Strategy(new BoardEvaluatorSummator(), color);
    }

    public ComputerPlayer(PieceColor color, Strategy strategy) {
        this.color = color;
        this.strategy = strategy;
    }

    @Override
    public PieceColor getColor() {
        return this.color;
    }

    @Override
    public MoveInterface makeMove(Board currentBoard) {
        final MoveInterface moveToMake = new AlphaBetaSearch(currentBoard, this.strategy, 8)
                .alphaBetaSearch();

        currentBoard.movePiece(moveToMake);
        this.printMove(moveToMake);
        return moveToMake;
    }

    private void printMove(MoveInterface moveToMake) {
        System.out.println(this.color.toString() + ": " + moveToMake);
    }

}

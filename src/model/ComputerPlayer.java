package model;

import model.ai.evaluation.BoardEvaluatorSummator;
import model.ai.search.AlphaBetaSearch;

public class ComputerPlayer implements Player {
    private final PieceColor color;
    private Strategy strategy;

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

    public Strategy getStrategy() {
        return this.strategy;
    }

    @Override
    public MoveInterface makeMove(Board currentBoard) {
        final MoveInterface moveToMake = new AlphaBetaSearch(currentBoard, this.strategy, 8)
                .alphaBetaSearch();

        currentBoard.movePiece(moveToMake);
        return moveToMake;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

}

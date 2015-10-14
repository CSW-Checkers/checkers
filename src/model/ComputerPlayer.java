package model;

import model.ai.AlphaBetaSearch;
import model.ai.PieceCountEvaluator;

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
    public void makeMove(Board currentBoard) {
        final MoveInterface moveToMake = new AlphaBetaSearch(currentBoard, this.getColor(),
                new PieceCountEvaluator(), 8).alphaBetaSearch();
        currentBoard.movePiece(moveToMake);
        this.printMove(moveToMake);
    }

    private void printMove(MoveInterface moveToMake) {
        System.out.println(this.color.toString() + ": " + moveToMake);
    }

}

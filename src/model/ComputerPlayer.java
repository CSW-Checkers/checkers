package model;

import model.ai.evaluation.KingCountEvaluator;
import model.ai.evaluation.PawnCountEvaluator;
import model.ai.evaluation.PlainBoardEvaluator;
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
    public void makeMove(Board currentBoard) {
        final MoveInterface moveToMake = new AlphaBetaSearch(currentBoard, this.getColor(),
                new KingCountEvaluator(new PawnCountEvaluator(new PlainBoardEvaluator())), 8)
                .alphaBetaSearch();
        currentBoard.movePiece(moveToMake);
        this.printMove(moveToMake);
    }

    private void printMove(MoveInterface moveToMake) {
        System.out.println(this.color.toString() + ": " + moveToMake);
    }

}

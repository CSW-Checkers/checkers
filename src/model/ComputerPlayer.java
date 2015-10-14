package model;

import java.util.Set;

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
        final Set<MoveInterface> possibleMoves = MoveGenerator.getAllPossibleMoves(currentBoard,
                this.getColor());
        final MoveInterface moveToMake = (MoveInterface) possibleMoves.toArray()[0];
        currentBoard.movePiece(moveToMake);
        this.printMove(moveToMake);
    }

    private void printMove(MoveInterface moveToMake) {
        System.out.println(this.color.toString() + ": " + moveToMake);
    }

}

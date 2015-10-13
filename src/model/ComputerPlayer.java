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
        final MoveInterface moveToMake;
        final Set<MoveInterface> jumpMoves = MoveGenerator.getJumpMoves(currentBoard,
                this.getColor());
        if (!jumpMoves.isEmpty()) {
            moveToMake = (Jump) jumpMoves.toArray()[0]; // Select first move
        } else {
            final Set<MoveInterface> nonJumpMoves = MoveGenerator.getNonJumpMoves(currentBoard,
                    this.getColor());
            if (!nonJumpMoves.isEmpty()) {
                moveToMake = (Move) nonJumpMoves.toArray()[0];
            } else {
                throw new IllegalArgumentException("No valid moves for Computer!");
            }
        }
        currentBoard.movePiece(moveToMake);
        this.printMove(moveToMake);
    }

    private void printMove(MoveInterface moveToMake) {
        System.out.println(this.color.toString() + ": " + moveToMake);
    }

}

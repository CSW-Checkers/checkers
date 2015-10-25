package model;

import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PieceColor color;

    public HumanPlayer(PieceColor color) {
        this.color = color;
    }

    @Override
    public PieceColor getColor() {
        return this.color;
    }

    @Override
    public MoveInterface makeMove(Board currentBoard) {
        @SuppressWarnings("resource")
        final Scanner reader = new Scanner(System.in);
        System.out.print("Enter move: ");
        final String moveString = reader.nextLine();
        final MoveInterface moveToMake = MoveBuilder.buildMove(moveString, currentBoard);
        try {
            if (MoveGenerator.getAllPossibleMoves(currentBoard, this.getColor()).contains(
                    moveToMake)) {
                currentBoard.movePiece(moveToMake);
                this.printMove(moveToMake);
            } else {
                throw new Exception("Move not found in list of possible legal moves.");
            }
        } catch (final Exception e) {
            System.out.println(e);
            System.out.println("Invalid move entered. Please try again.");
            return this.makeMove(currentBoard);
        }
        return moveToMake;
    }

    private void printMove(MoveInterface moveToMake) {
        System.out.println(this.color.toString() + ": " + moveToMake);
    }

}

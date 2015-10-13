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
    public void makeMove(Board currentBoard) {
        @SuppressWarnings("resource")
        final Scanner reader = new Scanner(System.in);
        System.out.print("Enter move: ");
        final String moveString = reader.nextLine();
        try {
            final MoveInterface moveToMake = MoveBuilder.buildMove(moveString, currentBoard);
            if (MoveGenerator.getAllPossibleMoves(currentBoard, this.getColor())
                    .contains(moveToMake)) {
                currentBoard.movePiece(moveToMake);
                this.printMove(moveToMake);
            } else {
                throw new Exception();
            }
        } catch (final Exception e) {
            System.err.println("Invalid move entered. Please try again.");
            this.makeMove(currentBoard);
        }
    }

    private void printMove(MoveInterface moveToMake) {
        System.out.println(this.color.toString() + ": " + moveToMake);
    }

}

package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class MoveGenerator {
    private static Set<MoveInterface> calculateJumpMoves(Board board, Set<Square> playersSquares) {

        final Set<MoveInterface> possibleMoves = new HashSet<>();

        for (final Square startingSquare : playersSquares) {
            for (final Square squareOneJumpAway : board
                    .getSquaresThatMightBeOneJumpAway(startingSquare)) {

                final SingleJump jump = new SingleJump(startingSquare.getPosition(),
                        squareOneJumpAway.getPosition(), board);

                if (MoveValidator.isValidMove(jump)) {
                    final ArrayList<SingleJump> jumps = new ArrayList<>();
                    jumps.add(jump);
                    calculateMultiJumpMoves(jumps, possibleMoves);
                }
            }
        }
        return possibleMoves;
    }

    private static void calculateMultiJumpMoves(ArrayList<SingleJump> jumps,
            Set<MoveInterface> possibleMoves) {

        final SingleJump lastJump = jumps.get(jumps.size() - 1);

        final Board newBoard = new Board(lastJump.getBoard());

        newBoard.movePiece(lastJump);

        final boolean oldKingStatus = lastJump.getPiece().isKing();
        final boolean newKingStatus = newBoard.getPiece(lastJump.getEndingPosition()).isKing();
        final boolean wasKingingJump = (oldKingStatus == false) && (newKingStatus == true);

        boolean noMoreJumps = true;

        if (!wasKingingJump) { // must stop when kinged
            final Square lastSquare = lastJump.getEndingSquare();

            for (final Square squareOneJumpAway : newBoard
                    .getSquaresThatMightBeOneJumpAway(lastSquare)) {

                final SingleJump jump = new SingleJump(lastSquare.getPosition(),
                        squareOneJumpAway.getPosition(), newBoard);
                if (MoveValidator.isValidMove(jump)) {

                    noMoreJumps = false;

                    final ArrayList<SingleJump> jumpsCopy = new ArrayList<>();
                    jumpsCopy.addAll(jumps);
                    jumpsCopy.add(jump);

                    calculateMultiJumpMoves(jumpsCopy, possibleMoves);
                }
            }
        }

        // If we didn't find any more pieces to jump,
        // then this jump chain has terminated and needs to be
        // added to the possible moves
        if (noMoreJumps) {
            final int startingPosition = jumps.get(0).getStartingPosition();
            final Board startingBoard = jumps.get(0).getBoard();
            final int endingPosition = jumps.get(jumps.size() - 1).getEndingPosition();

            if (jumps.size() == 1) {
                possibleMoves.add(new SingleJump(startingPosition, endingPosition, startingBoard));
            } else {
                final List<Integer> intermediatePositions = new ArrayList<>();
                for (int i = 0; i < jumps.size(); i++) {
                    if ((i != 0) && (i != jumps.size())) {
                        intermediatePositions.add(jumps.get(i).getStartingPosition());
                    }
                }
                possibleMoves.add(new MultiJump(startingPosition, endingPosition,
                        intermediatePositions, startingBoard));
            }
        }
    }

    private static Set<MoveInterface> calculateNonJumpMoves(Board board,
            Set<Square> playersSquares) {
        final Set<MoveInterface> possibleNonJumpMoves = new HashSet<>();
        for (final Square startingSquare : playersSquares) {
            for (final Square adjacentSquare : board.getAdjacentSquares(startingSquare)) {
                final Move normalMove = new Move(startingSquare.getPosition(),
                        adjacentSquare.getPosition(), board);
                if (MoveValidator.isValidMove(normalMove)) {
                    possibleNonJumpMoves.add(normalMove);
                }
            }
        }
        return possibleNonJumpMoves;
    }

    public static Set<MoveInterface> getAllPossibleMoves(Board board, PieceColor playersColor) {
        final Set<Square> playersSquares = board.getSquaresForPlayer(playersColor);
        final Set<MoveInterface> possibleMoves = calculateJumpMoves(board, playersSquares);
        if (possibleMoves.isEmpty()) {
            possibleMoves.addAll(calculateNonJumpMoves(board, playersSquares));
        }

        return possibleMoves;
    }

    public static Set<MoveInterface> getJumpMoves(Board board, PieceColor color) {
        final Set<Square> playersSquares = board.getSquaresForPlayer(color);
        return calculateJumpMoves(board, playersSquares);
    }

    public static Set<MoveInterface> getNonJumpMoves(Board board, PieceColor currentPlayersColor) {
        final Set<Square> currentPlayersOccupiedSquares = board
                .getSquaresForPlayer(currentPlayersColor);
        return calculateNonJumpMoves(board, currentPlayersOccupiedSquares);
    }

    private MoveGenerator() {
    }
}

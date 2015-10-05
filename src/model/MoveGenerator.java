package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveGenerator {

    private Board board;

    public MoveGenerator(Board board) {
        this.board = board;
    }

    public Set<MoveInterface> getJumpMoves(PieceColor color) {
        Set<Square> playersSquares = determineOccupiedSquares(color);
        return determineJumpMoves(playersSquares);
    }

    private Set<MoveInterface> determineJumpMoves(Set<Square> currentPlayersOccupiedSquares) {

        Set<MoveInterface> possibleMoves = new HashSet<>();

        for (Square startingSquare : currentPlayersOccupiedSquares) {
            for (Square squareOneJumpAway : this.board
                    .getSquaresThatMightBeOneJumpAway(startingSquare)) {

                SingleJump jump = new SingleJump(startingSquare.getPosition(),
                        squareOneJumpAway.getPosition(), this.board);
                if (MoveValidator.isValidMove(jump)) {
                    ArrayList<SingleJump> jumps = new ArrayList<SingleJump>();
                    jumps.add(jump);
                    this.determineMultiJumpMoves(jumps, possibleMoves);
                }
            }
        }
        return possibleMoves;
    }

    private void determineMultiJumpMoves(ArrayList<SingleJump> jumps,
            Set<MoveInterface> possibleMoves) {
        SingleJump lastJump = jumps.get(jumps.size() - 1);

        Board newBoard = new Board(lastJump.getBoard());
        newBoard.movePiece(lastJump);

        Square lastSquare = lastJump.getEndingSquare();

        boolean noMoreJumps = true;

        for (Square squareOneJumpAway : newBoard.getSquaresThatMightBeOneJumpAway(lastSquare)) {

            SingleJump jump = new SingleJump(lastSquare.getPosition(),
                    squareOneJumpAway.getPosition(), newBoard);

            if (MoveValidator.isValidMove(jump)) {
                noMoreJumps = false;
                ArrayList<SingleJump> jumpsCopy = SingleJump.singleJumpListCopier(jumps);
                jumpsCopy.add(jump);

                this.determineMultiJumpMoves(jumpsCopy, possibleMoves);
            }
        }

        // If we didn't find any more pieces to jump,
        // then this jump chain has terminated and needs to be
        // added to the possible moves
        if (noMoreJumps) {

            int startingPosition = jumps.get(0).getStartingPosition();
            Board startingBoard = jumps.get(0).getBoard();
            int endingPosition = jumps.get(jumps.size() - 1).getEndingPosition();

            if (jumps.size() == 1) {
                possibleMoves.add(new SingleJump(startingPosition, endingPosition, startingBoard));
            } else {
                List<Integer> intermediates = new ArrayList<>();
                for (int i = 0; i < jumps.size(); i++) {
                    if (i != 0 && i != jumps.size()) {
                        intermediates.add(jumps.get(i).getStartingPosition());
                    }
                }
                possibleMoves.add(new MultiJump(startingPosition, endingPosition, intermediates,
                        startingBoard));
            }
        }
    }

    public Set<MoveInterface> getNonJumpMoves(PieceColor currentPlayersColor) {
        Set<Square> playersSquares = determineOccupiedSquares(currentPlayersColor);
        return determineNonJumpMoves(playersSquares);
    }

    private Set<MoveInterface> determineNonJumpMoves(Set<Square> playersSquares) {
        Set<MoveInterface> possibleNonJumpMoves = new HashSet<>();
        for (Square startingSquare : playersSquares) {
            for (Square adjacentSquare : this.board.getAdjacentSquares(startingSquare)) {
                Move normalMove = new Move(startingSquare.getPosition(),
                        adjacentSquare.getPosition(), this.board);
                if (MoveValidator.isValidMove(normalMove)) {
                    possibleNonJumpMoves.add(normalMove);
                }
            }
        }
        return possibleNonJumpMoves;
    }

    private Set<Square> determineOccupiedSquares(PieceColor color) {
        Set<Square> playersSquares = new HashSet<>();
        for (Square square : this.board.getGameState()) {
            if (square.isOccupied()) {
                if (square.getOccupyingPiece().getColor() == color) {
                    playersSquares.add(square);
                }
            }
        }
        return playersSquares;
    }

    public Set<MoveInterface> getAllPossibleMoves(PieceColor playersColor) {
        Set<Square> playersSquares = this.determineOccupiedSquares(playersColor);
        Set<MoveInterface> possibleMoves = determineNonJumpMoves(playersSquares);
        possibleMoves.addAll(determineJumpMoves(playersSquares));
        return possibleMoves;
    }
}

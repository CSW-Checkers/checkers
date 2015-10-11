package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveGenerator {

    private Board board;
    public int boardsGenerated = 0;
    public int movesGenerated = 0;

    public MoveGenerator(Board board) {
        this.board = board;
    }

    private Set<MoveInterface> calculateJumpMoves(Set<Square> playersSquares) {

        Set<MoveInterface> possibleMoves = new HashSet<>();

        for (Square startingSquare : playersSquares) {
            for (Square squareOneJumpAway : this.board
                    .getSquaresThatMightBeOneJumpAway(startingSquare)) {

                SingleJump jump = new SingleJump(startingSquare.getPosition(),
                        squareOneJumpAway.getPosition(), this.board);
                this.movesGenerated++;
                if (MoveValidator.isValidMove(jump)) {
                    ArrayList<SingleJump> jumps = new ArrayList<>();
                    jumps.add(jump);
                    this.calculateMultiJumpMoves(jumps, possibleMoves);
                }
            }
        }
        return possibleMoves;
    }

    private void calculateMultiJumpMoves(ArrayList<SingleJump> jumps,
            Set<MoveInterface> possibleMoves) {

        SingleJump lastJump = jumps.get(jumps.size() - 1);
        this.boardsGenerated++;
        Board newBoard = new Board(lastJump.getBoard());
        newBoard.movePiece(lastJump);

        boolean oldKingStatus = lastJump.getPiece().isKing();
        boolean newKingStatus = newBoard.getPiece(lastJump.getEndingPosition()).isKing();
        boolean wasKingingJump = oldKingStatus == false && newKingStatus == true;

        boolean noMoreJumps = true;

        if (!wasKingingJump) { // must stop when kinged
            Square lastSquare = lastJump.getEndingSquare();

            for (Square squareOneJumpAway : newBoard.getSquaresThatMightBeOneJumpAway(lastSquare)) {

                SingleJump jump = new SingleJump(lastSquare.getPosition(),
                        squareOneJumpAway.getPosition(), newBoard);
                this.movesGenerated++;
                if (MoveValidator.isValidMove(jump)) {

                    noMoreJumps = false;

                    ArrayList<SingleJump> jumpsCopy = new ArrayList<>();
                    jumpsCopy.addAll(jumps);
                    jumpsCopy.add(jump);

                    this.calculateMultiJumpMoves(jumpsCopy, possibleMoves);
                }
            }
        }

        // If we didn't find any more pieces to jump,
        // then this jump chain has terminated and needs to be
        // added to the possible moves
        if (noMoreJumps) {
            int startingPosition = jumps.get(0).getStartingPosition();
            Board startingBoard = jumps.get(0).getBoard();
            int endingPosition = jumps.get(jumps.size() - 1).getEndingPosition();

            this.movesGenerated++;

            if (jumps.size() == 1) {
                possibleMoves.add(new SingleJump(startingPosition, endingPosition, startingBoard));
            } else {
                List<Integer> intermediatePositions = new ArrayList<>();
                for (int i = 0; i < jumps.size(); i++) {
                    if (i != 0 && i != jumps.size()) {
                        intermediatePositions.add(jumps.get(i).getStartingPosition());
                    }
                }
                possibleMoves.add(new MultiJump(startingPosition, endingPosition,
                        intermediatePositions, startingBoard));
            }
        }
    }

    private Set<MoveInterface> calculateNonJumpMoves(Set<Square> playersSquares) {
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

    public Set<MoveInterface> getAllPossibleMoves(PieceColor playersColor) {
        Set<Square> playersSquares = this.board.getSquaresForPlayer(playersColor);
        Set<MoveInterface> possibleMoves = this.calculateNonJumpMoves(playersSquares);
        possibleMoves.addAll(this.calculateJumpMoves(playersSquares));
        return possibleMoves;
    }

    public Set<MoveInterface> getJumpMoves(PieceColor color) {
        Set<Square> playersSquares = this.board.getSquaresForPlayer(color);
        return this.calculateJumpMoves(playersSquares);
    }

    public Set<MoveInterface> getNonJumpMoves(PieceColor currentPlayersColor) {
        Set<Square> currentPlayersOccupiedSquares = this.board
                .getSquaresForPlayer(currentPlayersColor);
        return this.calculateNonJumpMoves(currentPlayersOccupiedSquares);
    }
}

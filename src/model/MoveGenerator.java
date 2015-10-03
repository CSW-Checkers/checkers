package model;

import java.util.ArrayList;
import java.util.List;

public class MoveGenerator {

    private Board board;
    private List<Square> currentPlayersOccupiedSquares;
    private List<MoveInterface> possibleMoves;

    public MoveGenerator(Board board, PieceColor color) {
        this.possibleMoves = new ArrayList<MoveInterface>();
        this.board = board;
        this.currentPlayersOccupiedSquares = new ArrayList<Square>();
        // this.determineThisPlayersOccupiedSquares(color);
    }

    public void determineJumpMoves() {

        for (Square startingSquare : this.currentPlayersOccupiedSquares) {
            for (Square squareOneJumpAway : this.board
                    .getSquaresThatMightBeOneJumpAway(startingSquare)) {

                SingleJump jump = new SingleJump(startingSquare.getPosition(),
                        squareOneJumpAway.getPosition(), this.board);
                if (MoveValidator.isValidMove(jump)) {
                    ArrayList<SingleJump> jumps = new ArrayList<SingleJump>();
                    jumps.add(jump);
                    this.determineMultiJumpMoves(jumps);

                }

            }
        }
    }

    private void determineMultiJumpMoves(ArrayList<SingleJump> jumps) {
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

                this.determineMultiJumpMoves(jumpsCopy);
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
                this.possibleMoves
                        .add(new SingleJump(startingPosition, endingPosition, startingBoard));
            } else {
                List<Integer> intermediates = new ArrayList<>();
                for (int i = 0; i < jumps.size(); i++) {
                    if (i != 0 && i != jumps.size()) {
                        intermediates.add(jumps.get(i).getStartingPosition());
                    }
                }
                this.possibleMoves.add(new MultiJump(startingPosition, endingPosition,
                        intermediates, startingBoard));
            }
        }
    }

    public List<MoveInterface> determineNonJumpMoves() {
        List<MoveInterface> possibleNonJumpMoves = new ArrayList<MoveInterface>();
        for (Square startingSquare : this.currentPlayersOccupiedSquares) {
            for (Square adjacentSquare : this.board.getAdjacentSquares(startingSquare)) {
                Move normalMove = new Move(startingSquare.getPosition(),
                        adjacentSquare.getPosition(), this.board);
                if (MoveValidator.isValidMove(normalMove)) {
                    possibleNonJumpMoves.add(normalMove);
                    this.possibleMoves.add(normalMove);
                }
            }
        }
        return possibleNonJumpMoves;
    }

    public List<Square> determineThisPlayersOccupiedSquares(PieceColor color) {
        List<Square> thisPlayersOccupiedSquares = new ArrayList<Square>();
        for (Square square : this.board.getGameState()) {
            if (square.isOccupied()) {
                if (square.getOccupyingPiece().getColor() == color) {
                    thisPlayersOccupiedSquares.add(square);
                    this.currentPlayersOccupiedSquares.add(square);
                }
            }
        }
        return thisPlayersOccupiedSquares;
    }

    public List<MoveInterface> getPossibleMoves() {
        this.determineNonJumpMoves();
        this.determineJumpMoves();
        return this.possibleMoves;
    }

    public List<MoveInterface> getMoves() {
        return possibleMoves;
    }
}

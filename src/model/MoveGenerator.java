package model;

import java.util.ArrayList;
import java.util.List;

public class MoveGenerator {

    private Board board;

    public MoveGenerator(Board board) {
        this.board = board;
    }

    public List<MoveInterface> getJumpMoves(PieceColor currentPlayersColor) {
        List<Square> currentPlayersOccupiedSquares = determineThisPlayersOccupiedSquares(
                currentPlayersColor);
        return determineJumpMoves(currentPlayersOccupiedSquares);
    }

    private List<MoveInterface> determineJumpMoves(List<Square> currentPlayersOccupiedSquares) {

        List<MoveInterface> possibleMoves = new ArrayList<>();

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
            List<MoveInterface> possibleMoves) {
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

    public List<MoveInterface> getNonJumpMoves(PieceColor currentPlayersColor) {
        List<Square> currentPlayersOccupiedSquares = determineThisPlayersOccupiedSquares(
                currentPlayersColor);
        return determineNonJumpMoves(currentPlayersOccupiedSquares);
    }

    private List<MoveInterface> determineNonJumpMoves(List<Square> currentPlayersOccupiedSquares) {
        List<MoveInterface> possibleNonJumpMoves = new ArrayList<MoveInterface>();
        for (Square startingSquare : currentPlayersOccupiedSquares) {
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

    private List<Square> determineThisPlayersOccupiedSquares(PieceColor color) {
        List<Square> thisPlayersOccupiedSquares = new ArrayList<Square>();
        for (Square square : this.board.getGameState()) {
            if (square.isOccupied()) {
                if (square.getOccupyingPiece().getColor() == color) {
                    thisPlayersOccupiedSquares.add(square);
                }
            }
        }
        return thisPlayersOccupiedSquares;
    }

    public List<MoveInterface> getAllPossibleMoves(PieceColor playersColor) {
        List<Square> currentPlayersOccupiedSquares = this
                .determineThisPlayersOccupiedSquares(playersColor);
        List<MoveInterface> possibleMoves = determineNonJumpMoves(currentPlayersOccupiedSquares);
        possibleMoves.addAll(determineJumpMoves(currentPlayersOccupiedSquares));
        return possibleMoves;
    }
}

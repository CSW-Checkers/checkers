package model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Square> gameState;
    private int numberOfBlackPieces;
    private int numberOfWhitePieces;

    public Board() {
        this.gameState = this.getStartingGameBoardState();
        this.numberOfBlackPieces = 12;
        this.numberOfWhitePieces = 12;
    }

    public Board(Board otherBoard) {
        this.gameState = new ArrayList<Square>();
        for (Square square : otherBoard.getGameState()) {
            this.gameState.add(new Square(square));
        }
        this.numberOfBlackPieces = otherBoard.getNumberOfBlackPieces();
        this.numberOfWhitePieces = otherBoard.getNumberOfWhitePieces();
    }

    public Board(List<Integer> blackPositions, List<Integer> whitePositions) {
        this.numberOfBlackPieces = blackPositions.size();
        this.numberOfWhitePieces = whitePositions.size();
        this.gameState = new ArrayList<Square>(32);

        for (int position = 1; position <= 32; position++) {
            this.gameState.add(new Square(position, NullPiece.getInstance()));
            if (blackPositions.contains(position)) {
                this.setOccupyingPiece(position, new Piece(PieceColor.BLACK));
            } else if (whitePositions.contains(position)) {
                this.setOccupyingPiece(position, new Piece(PieceColor.WHITE));
            }
        }
    }

    private void decrementPieceCount(int position) {
        if (this.getPiece(position).isWhite()) {
            this.numberOfWhitePieces--;
        } else if (this.getPiece(position).isBlack()) {
            this.numberOfBlackPieces--;
        }
    }

    public List<Square> getAdjacentSquares(Square square) {
        List<Integer> squareNumbers = square.getAdjacentPositions();
        return this.getSquares((ArrayList<Integer>) squareNumbers);
    }

    public List<Square> getGameState() {
        return this.gameState;
    }

    public int getNumberOfBlackPieces() {
        return this.numberOfBlackPieces;
    }

    public int getNumberOfWhitePieces() {
        return this.numberOfWhitePieces;
    }

    public PieceInterface getPiece(int position) {
        return this.getSquare(position).getOccupyingPiece();
    }

    public ArrayList<PieceInterface> getPieces(List<Integer> positions) {
        ArrayList<PieceInterface> pieces = new ArrayList<>();
        for (int position : positions) {
            pieces.add(this.getPiece(position));
        }

        return pieces;
    }

    public Square getSquare(int position) {
        return this.gameState.get(position - 1);
    }

    public ArrayList<Square> getSquares(ArrayList<Integer> positions) {
        ArrayList<Square> squares = new ArrayList<>();
        for (int position : positions) {
            squares.add(this.getSquare(position));
        }

        return squares;
    }

    /**
     * Returns squares with locations +9, -9, +7, -7 Returns only those squares on the board, i.e.
     * with a checkers number of 1-32 (array index of 0-31). The method returns squares that may be
     * on the other side of the board.
     *
     * @param startingSquare
     *            the square in question
     * @return squares one possibly one jump away, they may try to wrap around the board
     */
    public List<Square> getSquaresThatMightBeOneJumpAway(Square startingSquare) {
        List<Square> squaresPossiblyOneJumpAway = new ArrayList<Square>();

        int startingPosition = startingSquare.getPosition();
        int[] possibleJumpPositions = { startingPosition + 9, startingPosition - 9,
                startingPosition + 7, startingPosition - 7 };

        for (int i = 0; i < possibleJumpPositions.length; i++) {
            if (MoveValidator.isOnBoard(possibleJumpPositions[i])) {
                squaresPossiblyOneJumpAway.add(this.getSquare(possibleJumpPositions[i]));
            }
        }
        return squaresPossiblyOneJumpAway;
    }

    private List<Square> getStartingGameBoardState() {
        List<Square> startingGameBoard = new ArrayList<>(32);

        for (int i = 1; i <= 32; i++) {
            if (i <= 12) {
                startingGameBoard.add(new Square(i, new Piece(PieceColor.BLACK)));
            } else if (i > 12 && i < 21) {
                startingGameBoard.add(new Square(i, NullPiece.getInstance()));
            } else {
                startingGameBoard.add(new Square(i, new Piece(PieceColor.WHITE)));
            }
        }

        return startingGameBoard;
    }

    public boolean isEndState() {
        return (this.numberOfBlackPieces == 0 || this.numberOfWhitePieces == 0);
    }

    public void movePiece(MoveInterface move) {
        PieceInterface pieceToMove = this.pickUpPiece(move.getStartingPosition());
        if (move instanceof Jump) {
            Jump jump = (Jump) move;

            for (int position : jump.getJumpedPositions()) {
                this.removePiece(position);
            }
        }
        this.setOccupyingPiece(move.getEndingPosition(), pieceToMove);
    }

    private PieceInterface pickUpPiece(int position) {
        PieceInterface pieceToPickUp = this.getPiece(position);
        this.removePiece(position);
        return pieceToPickUp;
    }

    public void removePiece(int position) {
        this.decrementPieceCount(position);
        this.getSquare(position).removeOccupyingPiece();
    }

    public void setOccupyingPiece(int position, PieceInterface pieceToSet) {
        this.getSquare(position).setOccupyingPiece(pieceToSet);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gameState == null) ? 0 : gameState.hashCode());
        result = prime * result + numberOfBlackPieces;
        result = prime * result + numberOfWhitePieces;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Board other = (Board) obj;
        if (gameState == null) {
            if (other.gameState != null) {
                return false;
            }
        } else if (!gameState.equals(other.gameState)) {
            return false;
        }
        if (numberOfBlackPieces != other.numberOfBlackPieces) {
            return false;
        }
        if (numberOfWhitePieces != other.numberOfWhitePieces) {
            return false;
        }
        return true;
    }

}

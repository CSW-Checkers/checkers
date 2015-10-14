package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Square {
    private final List<Integer> adjacentPositions;
    private PieceInterface occupyingPiece;
    private final int position;

    public Square(int position, PieceInterface occupyingPiece) {
        if (!MoveValidator.isOnBoard(position)) {
            throw new IllegalArgumentException("Invalid position for square: " + position);
        } else {
            this.position = position;
            this.occupyingPiece = occupyingPiece;
            this.adjacentPositions = this.determineAdjacentSquares();
        }
    }

    public Square(Square otherSquare) {
        this.position = otherSquare.getPosition();
        if (otherSquare.getOccupyingPiece().isNull()) {
            this.occupyingPiece = NullPiece.getInstance();
        } else {
            this.occupyingPiece = new Piece(otherSquare.getOccupyingPiece());
        }
        this.adjacentPositions = new ArrayList<Integer>();
        this.adjacentPositions.addAll(otherSquare.getAdjacentPositions());
    }

    private List<Integer> determineAdjacentSquares() {
        List<Integer> adjacentPositions = new ArrayList<>(4);

        if (this.isCenterSquare()) {
            adjacentPositions = this.getCenterSquareAdjacentPositions(this.position);
        } else {
            adjacentPositions = this.getNonCenterSquareAdjacentPositions();
        }

        return adjacentPositions;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Square other = (Square) obj;
        if (this.adjacentPositions == null) {
            if (other.adjacentPositions != null) {
                return false;
            }
        } else if (!this.adjacentPositions.equals(other.adjacentPositions)) {
            return false;
        }
        if (this.occupyingPiece == null) {
            if (other.occupyingPiece != null) {
                return false;
            }
        } else if (!this.occupyingPiece.equals(other.occupyingPiece)) {
            return false;
        }
        if (this.position != other.position) {
            return false;
        }
        return true;
    }

    public List<Integer> getAdjacentPositions() {
        return this.adjacentPositions;
    }

    private List<Integer> getCenterSquareAdjacentPositions(int position) {
        if (!this.isCenterSquare()) {
            System.err.println("Invalid call. Not a center square.");
            System.out.println("Square.getCenterSquareAdjacentPositions()");
            System.exit(1);
        }

        List<Integer> adjacentPositions = new ArrayList<>(4);
        int rowNumber = (int) Math.ceil(position / 4.0);

        if (rowNumber % 2 == 0) {
            adjacentPositions.add(position - 5);
            adjacentPositions.add(position - 4);
            adjacentPositions.add(position + 3);
            adjacentPositions.add(position + 4);
        } else {
            adjacentPositions.add(position + 5);
            adjacentPositions.add(position + 4);
            adjacentPositions.add(position - 3);
            adjacentPositions.add(position - 4);
        }

        return adjacentPositions;
    }

    public int getColumnNumber() {
        int remainder = this.position % 8;
        switch (remainder) {
            case 0:
                return 7;
            case 1:
                return 2;
            case 2:
                return 4;
            case 3:
                return 6;
            case 4:
                return 8;
            case 5:
                return 1;
            case 6:
                return 3;
            case 7:
                return 5;
            default:
                return -1;
        }
    }

    private List<Integer> getNonCenterSquareAdjacentPositions() {
        List<Integer> adjacentPositions = new ArrayList<>(2);

        if (this.isOnVerticalEdgeOfBoard()) {
            adjacentPositions.add(this.position - 4);
            adjacentPositions.add(this.position + 4);
        } else if (this.isInCornerOfBoard()) {
            if (this.position == 4) {
                adjacentPositions.add(8);
            } else if (this.position == 29) {
                adjacentPositions.add(25);
            } else {
                System.err.println("Invalid corner position");
                System.out.println("Square.getNonCenterSquareAdjacentPositions()");
                System.exit(1);
            }
        } else if (this.isOnBlackEdgeOfBoard()) {
            adjacentPositions.add(this.position + 5);
            adjacentPositions.add(this.position + 4);
        } else if (this.isOnWhiteEdgeOfBoard()) {
            adjacentPositions.add(this.position - 5);
            adjacentPositions.add(this.position - 4);
        } else {
            System.err.println("Invalid square position");
            System.out.println("Square.getNonCenterSquareAdjacentPositions()");
            System.exit(1);
        }

        return adjacentPositions;
    }

    public PieceInterface getOccupyingPiece() {
        return this.occupyingPiece;
    }

    public int getPosition() {
        return this.position;
    }

    public int getRowNumber() {
        return (int) Math.ceil(this.position / 4.0);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((this.adjacentPositions == null) ? 0 : this.adjacentPositions.hashCode());
        result = prime * result
                + ((this.occupyingPiece == null) ? 0 : this.occupyingPiece.hashCode());
        result = prime * result + this.position;
        return result;
    }

    private boolean isCenterSquare() {
        List<Integer> nonCenterSquares = Arrays.asList(1, 2, 3, 4, 5, 12, 13, 20, 21, 28, 29, 30,
                31, 32);

        if (nonCenterSquares.contains(this.position)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isInCornerOfBoard() {
        if (this.position == 4 || this.position == 29) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isInLeftTwoColumns() {
        List<Integer> leftTwoColumnPositions = Arrays.asList(1, 5, 9, 13, 17, 21, 25, 29);
        if (leftTwoColumnPositions.contains(this.getPosition())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isInRightTwoColumns() {
        List<Integer> rightTwoColumnPositions = Arrays.asList(4, 8, 12, 16, 20, 24, 28, 32);
        if (rightTwoColumnPositions.contains(this.getPosition())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isInSameColumn(Square otherSquare) {
        return otherSquare.getColumnNumber() == this.getColumnNumber();
    }

    public boolean isInSameRow(Square otherSquare) {
        return otherSquare.getRowNumber() == this.getRowNumber();
    }

    public boolean isOccupied() {
        return !this.occupyingPiece.isNull();
    }

    public boolean isOnBlackEdgeOfBoard() {
        int[] topEdgePositions = { 1, 2, 3, 4 };
        for (int edgePosition : topEdgePositions) {
            if (this.position == edgePosition) {
                return true;
            }
        }
        return false;
    }

    private boolean isOnVerticalEdgeOfBoard() {
        int[] verticalEdgePositions = { 5, 12, 13, 20, 21, 28 };
        for (int edgePosition : verticalEdgePositions) {
            if (this.position == edgePosition) {
                return true;
            }
        }
        return false;
    }

    public boolean isOnWhiteEdgeOfBoard() {
        int[] bottomEdgePositions = { 29, 30, 31, 32 };
        for (int edgePosition : bottomEdgePositions) {
            if (this.position == edgePosition) {
                return true;
            }
        }
        return false;
    }

    private void kingPieceIfNecessary() {
        if ((this.isOnBlackEdgeOfBoard() && this.occupyingPiece.isWhite())
                || (this.isOnWhiteEdgeOfBoard() && this.occupyingPiece.isBlack())) {
            this.occupyingPiece.kingMe();
        }
    }

    public void removeOccupyingPiece() {
        this.occupyingPiece = NullPiece.getInstance();
    }

    public void setOccupyingPiece(PieceInterface occupyingPiece) {
        if (this.isOccupied()) {
            System.err.println("Occupied square: " + this.position);
            System.out.println("Square.setOccupyingPiece()");
            System.exit(1);
        } else {
            this.occupyingPiece = occupyingPiece;
            this.kingPieceIfNecessary();
        }
    }

    @Override
    public String toString() {
        return "Square [occupyingPiece=" + this.occupyingPiece + ", position=" + this.position
                + "]";
    }
}

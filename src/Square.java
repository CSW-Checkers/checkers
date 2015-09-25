
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Square {
    private final List<Integer> adjacentSquares;
    private Piece occupyingPiece;
    private final int position;

    public Square(int position, Piece occupyingPiece) {
        this.position = position;
        this.occupyingPiece = occupyingPiece;
        this.adjacentSquares = this.determineAdjacentSquares();

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

    public List<Integer> getAdjacentSquares() {
        return this.adjacentSquares;
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

    public Piece getOccupyingPiece() {
        return this.occupyingPiece;
    }

    public int getPosition() {
        return this.position;
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

    public boolean isOccupied() {
        if (this.occupyingPiece == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isOnBlackEdgeOfBoard() {
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

    private boolean isOnWhiteEdgeOfBoard() {
        int[] bottomEdgePositions = { 29, 30, 31, 32 };
        for (int edgePosition : bottomEdgePositions) {
            if (this.position == edgePosition) {
                return true;
            }
        }
        return false;
    }

    private void kingPieceIfNecessary() {
        if ((this.isOnBlackEdgeOfBoard() && this.occupyingPiece.getColor().equals(PieceColor.WHITE))
                || (this.isOnWhiteEdgeOfBoard()
                        && this.occupyingPiece.getColor().equals(PieceColor.BLACK))) {
            this.occupyingPiece.kingMe();
        }
    }

    public void removeOccupyingPiece() {
        this.occupyingPiece = null;
    }

    public void setOccupyingPiece(Piece occupyingPiece) {
        if (this.isOccupied()) {
            System.err.println("Occupied square. Invalid move.");
            System.out.println("Square.setOccupyingPiece()");
            System.exit(1);
        } else {
            this.kingPieceIfNecessary();
            this.occupyingPiece = occupyingPiece;
        }
    }

}

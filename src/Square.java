
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Square {
    private final int position;
    private final List<Integer> adjacentSquares;
    private Piece occupyingPiece;

    public Square(int position, Piece occupyingPiece) {
        this.position = position;
        this.occupyingPiece = occupyingPiece;
        this.adjacentSquares = this.determineAdjacentSquares();

    }

    private List<Integer> determineAdjacentSquares() {
        List<Integer> adjacentPositions = new ArrayList<>(4);

        if (this.isCenterSquare()) {
            adjacentPositions = getCenterSquareAdjacentPositions(this.position);
        } else {
            adjacentPositions = getNonCenterSquareAdjacentPositions();
        }

        return adjacentPositions;
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
            adjacentPositions.add(position + 4);
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
        } else if (isOnBlackEdgeOfBoard()) {
            adjacentPositions.add(this.position + 5);
            adjacentPositions.add(this.position + 4);
        } else if (isOnWhiteEdgeOfBoard()) {
            adjacentPositions.add(this.position - 5);
            adjacentPositions.add(this.position - 4);
        } else {
            System.err.println("Invalid square position");
            System.out.println("Square.getNonCenterSquareAdjacentPositions()");
            System.exit(1);
        }

        return adjacentPositions;
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

    private boolean isOnBlackEdgeOfBoard() {
        int[] topEdgePositions = { 1, 2, 3, 4 };
        for (int edgePosition : topEdgePositions) {
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

    private boolean isInCornerOfBoard() {
        if (this.position == 4 || this.position == 29) {
            return true;
        } else {
            return false;
        }
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

    public int getPosition() {
        return position;
    }

    public List<Integer> getAdjacentSquares() {
        return adjacentSquares;
    }

    public boolean isOccupied() {
        if (occupyingPiece == null) {
            return false;
        } else {
            return true;
        }
    }

    public Piece getOccupyingPiece() {
        return occupyingPiece;
    }

    public void setOccupyingPiece(Piece occupyingPiece) {
        if (isOccupied()) {
            System.err.println("Occupied square. Invalid move.");
            System.out.println("Square.setOccupyingPiece()");
            System.exit(1);
        } else {
            kingPieceIfNecessary();
            this.occupyingPiece = occupyingPiece;
        }
    }

    private void kingPieceIfNecessary() {
        if ((this.isOnBlackEdgeOfBoard() && occupyingPiece.getColor().equals(PieceColor.WHITE))
                || (this.isOnWhiteEdgeOfBoard()
                        && occupyingPiece.getColor().equals(PieceColor.BLACK))) {
            occupyingPiece.kingMe();
        }
    }

    public void removeOccupyingPiece() {
        this.occupyingPiece = null;
    }

}

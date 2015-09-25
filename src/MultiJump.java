
import java.util.ArrayList;

public class MultiJump implements Jump {
    private Board board;
    private int endingPosition;
    private ArrayList<Integer> jumpedPositions;
    private Piece piece;
    private int startingPosition;
    private ArrayList<SingleJump> subJumps;

    public MultiJump(int startingPosition, int endingPosition, ArrayList<SingleJump> subJumps,
            Board board) {
        this.startingPosition = startingPosition;
        this.endingPosition = endingPosition;
        this.subJumps = subJumps;
        this.jumpedPositions = this.determineJumpedPositions();
        this.piece = board.getPiece(startingPosition);
        this.board = board;
    }

    private ArrayList<Integer> determineJumpedPositions() {
        ArrayList<Integer> jumpedPositions = new ArrayList<>();

        for (SingleJump jump : this.subJumps) {
            jumpedPositions.addAll(jump.getJumpedPositions());
        }

        return jumpedPositions;
    }

    @Override
    public Board getBoard() {
        return this.board;
    }

    @Override
    public int getEndingPosition() {
        return this.endingPosition;
    }

    @Override
    public Square getEndingSquare() {
        return this.getBoard().getSquare(this.getEndingPosition());
    }

    @Override
    public ArrayList<Piece> getJumpedPieces() {
        return this.board.getPieces(this.getJumpedPositions());
    }

    @Override
    public ArrayList<Integer> getJumpedPositions() {
        return this.jumpedPositions;
    }

    @Override
    public ArrayList<Square> getJumpedSquares() {
        return this.board.getSquares(this.getJumpedPositions());
    }

    @Override
    public Piece getPiece() {
        return this.piece;
    }

    @Override
    public int getStartingPosition() {
        return this.startingPosition;
    }

    @Override
    public Square getStartingSquare() {
        return this.getBoard().getSquare(this.getStartingPosition());
    }

    public ArrayList<SingleJump> getSubJumps() {
        return this.subJumps;
    }

    @Override
    public String toString() {
        String moveNotation = "";

        for (SingleJump jump : this.subJumps) {
            moveNotation += jump.getStartingPosition() + "x";
        }

        // Strip off trailing 'x'
        moveNotation = moveNotation.substring(0, moveNotation.length() - 1);

        return moveNotation;
    }

}

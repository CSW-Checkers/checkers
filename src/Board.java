
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

    public List<Square> getGameState() {
        return this.gameState;
    }

    public int getNumberOfBlackPieces() {
        return this.numberOfBlackPieces;
    }

    public int getNumberOfWhitePieces() {
        return this.numberOfWhitePieces;
    }

    public Piece getPiece(int position) {
        return this.getSquare(position).getOccupyingPiece();
    }

    public ArrayList<Piece> getPieces(ArrayList<Integer> positions) {
        ArrayList<Piece> pieces = new ArrayList<>();
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

    private List<Square> getStartingGameBoardState() {
        List<Square> startingGameBoard = new ArrayList<>(32);

        for (int i = 0; i < 32; i++) {
            if (i < 12) {
                startingGameBoard.add(new Square(i, new Piece(PieceColor.BLACK)));
            } else if (i >= 12 && i < 21) {
                startingGameBoard.add(new Square(i, null));
            } else {
                startingGameBoard.add(new Square(i, new Piece(PieceColor.WHITE)));
            }
        }

        return startingGameBoard;
    }

    public void movePiece(MoveInterface move) {
        if (MoveValidator.isValidMove(move)) {
            Piece pieceToMove = this.pickUpPiece(move.getStartingPosition());
            if (move instanceof Jump) {
                Jump jump = (Jump) move;

                for (int position : jump.getJumpedPositions()) {
                    this.removePiece(position);
                }
            }
            this.getSquare(move.getEndingPosition()).setOccupyingPiece(pieceToMove);
        } else {
            System.err.println("Invalid move!");
            System.out.println("Board.movePiece()");
            System.exit(1);
        }

    }

    private Piece pickUpPiece(int position) {
        Piece pieceToPickUp = this.getPiece(position);
        this.removePiece(position);
        return pieceToPickUp;
    }

    public void removePiece(int position) {
        this.updatePieceCount(position);
        this.getSquare(position).removeOccupyingPiece();
    }

    private void updatePieceCount(int position) {
        if (this.getPiece(position).getColor().equals(PieceColor.WHITE)) {
            this.numberOfWhitePieces--;
        } else {
            this.numberOfBlackPieces--;
        }
    }
}

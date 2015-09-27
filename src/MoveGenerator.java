import java.util.ArrayList;
import java.util.List;

public class MoveGenerator {

    private List<MoveInterface> possibleMoves;
    private Board board;
    private List<Square> currentPlayersOccupiedSquares;

    public MoveGenerator(Board board, PieceColor color) {
        this.possibleMoves = new ArrayList<MoveInterface>();
        this.board = board;
        currentPlayersOccupiedSquares = new ArrayList<Square>();
        determineThisPlayersOccupiedSquares(color);
    }

    public List<MoveInterface> getPossibleMoves() {
        determineNonJumpMoves();
        determineJumpMoves();
        return possibleMoves;
    }

    private void determineThisPlayersOccupiedSquares(PieceColor color) {
        for (Square square : board.getGameState()) {
            if (square.isOccupied()) {
                if (square.getOccupyingPiece().getColor() == color) {
                    currentPlayersOccupiedSquares.add(square);
                }
            }
        }
    }

    private void determineNonJumpMoves() {
        for (Square startingSquare : this.currentPlayersOccupiedSquares) {
            for (Square adjacentSquare : this.board.getAdjacentSquares(startingSquare)) {
                Move normalMove = new Move(startingSquare.getPosition(),
                        adjacentSquare.getPosition(), this.board);
                if (MoveValidator.isValidMove(normalMove)) {
                    this.possibleMoves.add(normalMove);
                }
            }
        }
    }

    private void determineJumpMoves() {
        for (Square startingSquare : this.currentPlayersOccupiedSquares) {
            for (Square twoAway : this.board.getSquaresPossiblyOneJumpAway(startingSquare)) {
                SingleJump jump = new SingleJump(startingSquare.getPosition(),
                        twoAway.getPosition(), this.board);
                if (MoveValidator.isValidMove(jump)) {
                    ArrayList<SingleJump> jumps = new ArrayList<SingleJump>();
                    jumps.add(jump);
                    determineMultiJumpMoves(jumps);
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

        for (Square twoAway : newBoard.getSquaresPossiblyOneJumpAway(lastSquare)) {
            SingleJump jump = new SingleJump(lastSquare.getPosition(), twoAway.getPosition(),
                    newBoard);
            if (MoveValidator.isValidMove(jump)) {
                noMoreJumps = false;
                ArrayList<SingleJump> jumpsCopy = SingleJump.singleJumpListCopier(jumps);
                jumpsCopy.add(jump);
                determineMultiJumpMoves(jumpsCopy);
            }
        }

        // If we didn't find any more pieces to jump,
        // then this jump chain has terminated and needs to be
        // added to the possible moves
        if (noMoreJumps) {
            int startingPosition = jumps.get(0).getStartingPosition();
            Board startingBoard = jumps.get(0).getBoard();
            int endingPosition = jumps.get(jumps.size() - 1).getEndingPosition();

            this.possibleMoves
                    .add(new MultiJump(startingPosition, endingPosition, jumps, startingBoard));
        }
    }
}

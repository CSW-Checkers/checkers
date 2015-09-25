import java.util.ArrayList;
import java.util.List;

class MiniMaxNode {
    private Board board;
    private Move moveThatGotToThisState;
    private int depthLevel; // 0 is bottom depth
    private PieceColor currentPlayersColor;
    private List<MiniMaxNode> children;
    private double value;

    public MiniMaxNode(Board board, int depthLevel, PieceColor currentPlayersColor) {
        this.board = board;
        this.depthLevel = depthLevel;
        this.currentPlayersColor = currentPlayersColor;
    }

    public List<MiniMaxNode> getChildren() {
        if (children == null) {
            children = new ArrayList<MiniMaxNode>();
            MoveGenerator moveGen = new MoveGenerator(board, currentPlayersColor);
            for (MoveInterface move : moveGen.getPossibleMoves()) {
                Board childBoard = new Board(this.board);
                childBoard.movePiece(move);
                MiniMaxNode childNode = new MiniMaxNode(childBoard, this.depthLevel - 1,
                        this.currentPlayersColor.getOppositeColor());
                children.add(childNode);
            }
        }
        return children;
    }

    public Board getBoard() {
        return board;
    }

    public Move getMoveThatGotToThisState() {
        return moveThatGotToThisState;
    }

    public boolean isLeaf() {
        return (this.depthLevel == 0 || this.board.isEndState());
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
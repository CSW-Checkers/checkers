package model.ai;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Move;
import model.MoveGenerator;
import model.MoveInterface;
import model.PieceColor;

class AlphaBetaSearchNode {
    private Board board;
    private List<AlphaBetaSearchNode> children;
    private PieceColor currentPlayersColor;
    private int depthLevel; // 0 is bottom depth
    private Move moveThatGotToThisState;
    private double value;

    public AlphaBetaSearchNode(Board board, int depthLevel, PieceColor currentPlayersColor) {
        this.board = board;
        this.depthLevel = depthLevel;
        this.currentPlayersColor = currentPlayersColor;
    }

    public Board getBoard() {
        return this.board;
    }

    public List<AlphaBetaSearchNode> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<AlphaBetaSearchNode>();
            MoveGenerator moveGen = new MoveGenerator(this.board, this.currentPlayersColor);
            for (MoveInterface move : moveGen.getPossibleMoves()) {
                Board childBoard = new Board(this.board);
                childBoard.movePiece(move);
                AlphaBetaSearchNode childNode = new AlphaBetaSearchNode(childBoard,
                        this.depthLevel - 1, this.currentPlayersColor.getOppositeColor());
                this.children.add(childNode);
            }
        }
        return this.children;
    }

    public Move getMoveThatGotToThisState() {
        return this.moveThatGotToThisState;
    }

    public double getValue() {
        return this.value;
    }

    public boolean isLeaf() {
        return (this.depthLevel == 0 || this.board.isEndState());
    }

    public void setValue(double value) {
        this.value = value;
    }
}
package model.ai.search;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.MoveGenerator;
import model.MoveInterface;
import model.PieceColor;

class AlphaBetaSearchNode {
    private Board board;
    private List<AlphaBetaSearchNode> children;
    private PieceColor currentPlayersColor;
    private int depthLevel; // 0 is root
    private MoveInterface moveThatGotToThisState;
    private double value;

    public AlphaBetaSearchNode(Board board, int depthLevel, PieceColor currentPlayersColor) {
        this.board = board;
        this.depthLevel = depthLevel;
        this.currentPlayersColor = currentPlayersColor;
    }

    public AlphaBetaSearchNode(Board board, int depthLevel, PieceColor currentPlayersColor,
            MoveInterface moveThatGotToThisState) {
        this.board = board;
        this.depthLevel = depthLevel;
        this.currentPlayersColor = currentPlayersColor;
        this.moveThatGotToThisState = moveThatGotToThisState;
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
        AlphaBetaSearchNode other = (AlphaBetaSearchNode) obj;
        if (this.board == null) {
            if (other.board != null) {
                return false;
            }
        } else if (!this.board.equals(other.board)) {
            return false;
        }
        if (this.children == null) {
            if (other.children != null) {
                return false;
            }
        } else if (!this.children.equals(other.children)) {
            return false;
        }
        if (this.currentPlayersColor != other.currentPlayersColor) {
            return false;
        }
        if (this.depthLevel != other.depthLevel) {
            return false;
        }
        if (this.moveThatGotToThisState == null) {
            if (other.moveThatGotToThisState != null) {
                return false;
            }
        } else if (!this.moveThatGotToThisState.equals(other.moveThatGotToThisState)) {
            return false;
        }
        if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value)) {
            return false;
        }
        return true;
    }

    public Board getBoard() {
        return this.board;
    }

    public List<AlphaBetaSearchNode> getChildren() {
        if (this.children == null) {

            ArrayList<AlphaBetaSearchNode> childNodes = new ArrayList<AlphaBetaSearchNode>();

            if (this.depthLevel == 0) {
                this.children = childNodes;
            }

            for (MoveInterface move : MoveGenerator.getAllPossibleMoves(this.board,
                    this.currentPlayersColor)) {

                Board childBoard = new Board(this.board);
                childBoard.movePiece(move);
                AlphaBetaSearchNode childNode = new AlphaBetaSearchNode(childBoard,
                        this.depthLevel + 1, this.currentPlayersColor.getOppositeColor());
                if (this.depthLevel == 0) {
                    childNode.setMoveThatGotToThisState(move);
                }
                childNodes.add(childNode);
            }
            return childNodes;
        } else {
            return this.children;
        }

    }

    public PieceColor getCurrentPlayersColor() {
        return this.currentPlayersColor;
    }

    public int getDepthLevel() {
        return this.depthLevel;
    }

    public MoveInterface getMoveThatGotToThisState() {
        return this.moveThatGotToThisState;
    }

    public double getValue() {
        return this.value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.board == null) ? 0 : this.board.hashCode());
        result = prime * result + ((this.children == null) ? 0 : this.children.hashCode());
        result = prime * result
                + ((this.currentPlayersColor == null) ? 0 : this.currentPlayersColor.hashCode());
        result = prime * result + this.depthLevel;
        result = prime * result + ((this.moveThatGotToThisState == null) ? 0
                : this.moveThatGotToThisState.hashCode());
        long temp;
        temp = Double.doubleToLongBits(this.value);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public void setMoveThatGotToThisState(MoveInterface moveThatGotToThisState) {
        this.moveThatGotToThisState = moveThatGotToThisState;

    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "AlphaBetaSearchNode [board=" + this.board + ", children=" + this.children
                + ", currentPlayersColor=" + this.currentPlayersColor + ", depthLevel="
                + this.depthLevel + ", moveThatGotToThisState=" + this.moveThatGotToThisState
                + ", value=" + this.value + "]";
    }

}
package model.ai.search;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Board;
import model.Move;
import model.MoveInterface;
import model.MultiJump;
import model.PieceColor;
import model.SingleJump;
import model.ai.search.AlphaBetaSearchNode;

public class AlphaBetaSearchNodeTest {

    @Test
    public void testGetChildrenForComplexBoard() {
        List<Integer> blackPositions = Arrays.asList(10, 11, 13, 18, 25, 27, 30);
        List<Integer> whitePositions = Arrays.asList(1, 7, 8, 16, 22, 23, 24);
        Board board = new Board(blackPositions, whitePositions);
        board.getPiece(10).kingMe();
        board.getPiece(23).kingMe();
        board.getPiece(25).kingMe();

        int startingDepth = 8;

        PieceColor startingColor = PieceColor.BLACK;
        AlphaBetaSearchNode searchNode = new AlphaBetaSearchNode(board, startingDepth,
                startingColor);
        List<AlphaBetaSearchNode> children = searchNode.getChildren();

        // check correct number of children
        assertEquals(3, children.size());

        // set expected search nodes
        Set<AlphaBetaSearchNode> expectedSearchNodes = new HashSet<>();

        int expectedDepth = startingDepth + 1;
        PieceColor expectedColor = startingColor.getOppositeColor();

        MoveInterface move = new SingleJump(11, 20, board);
        Board newBoard = new Board(board);
        newBoard.movePiece(move);
        expectedSearchNodes
                .add(new AlphaBetaSearchNode(newBoard, expectedDepth, expectedColor, null));

        move = new MultiJump(10, 17, Arrays.asList(3, 12, 19, 26), board);
        newBoard = new Board(board);
        newBoard.movePiece(move);
        expectedSearchNodes
                .add(new AlphaBetaSearchNode(newBoard, expectedDepth, expectedColor, null));

        move = new MultiJump(10, 28, Arrays.asList(3, 12, 19), board);
        newBoard = new Board(board);
        newBoard.movePiece(move);
        expectedSearchNodes
                .add(new AlphaBetaSearchNode(newBoard, expectedDepth, expectedColor, null));

        // get set of actual search nodes
        Set<AlphaBetaSearchNode> actualSearchNodes = new HashSet<>();
        actualSearchNodes.addAll(children);

        // assert expected and actual search nodes are equal
        assertEquals(expectedSearchNodes, actualSearchNodes);

    }

    @Test
    public void testGetChildrenForStartingBoard() {
        Board board = new Board();
        int startingDepth = 1;
        PieceColor startingColor = PieceColor.BLACK;
        AlphaBetaSearchNode searchNode = new AlphaBetaSearchNode(board, startingDepth,
                startingColor);
        List<AlphaBetaSearchNode> children = searchNode.getChildren();

        // check correct number of children
        assertEquals(children.size(), 7);

        // add expected search nodes

        Set<AlphaBetaSearchNode> expectedNodes = new HashSet<>();

        int expectedDepth = startingDepth + 1;
        PieceColor expectedColor = startingColor.getOppositeColor();

        MoveInterface move = new Move(9, 13, board);
        Board newBoard = new Board(board);
        newBoard.movePiece(move);
        expectedNodes.add(new AlphaBetaSearchNode(newBoard, expectedDepth, expectedColor, null));

        move = new Move(9, 14, board);
        newBoard = new Board(board);
        newBoard.movePiece(move);
        expectedNodes.add(new AlphaBetaSearchNode(newBoard, expectedDepth, expectedColor, null));

        move = new Move(10, 14, board);
        newBoard = new Board(board);
        newBoard.movePiece(move);
        expectedNodes.add(new AlphaBetaSearchNode(newBoard, expectedDepth, expectedColor, null));

        move = new Move(10, 15, board);
        newBoard = new Board(board);
        newBoard.movePiece(move);
        expectedNodes.add(new AlphaBetaSearchNode(newBoard, expectedDepth, expectedColor, null));

        move = new Move(11, 15, board);
        newBoard = new Board(board);
        newBoard.movePiece(move);
        expectedNodes.add(new AlphaBetaSearchNode(newBoard, expectedDepth, expectedColor, null));

        move = new Move(11, 16, board);
        newBoard = new Board(board);
        newBoard.movePiece(move);
        expectedNodes.add(new AlphaBetaSearchNode(newBoard, expectedDepth, expectedColor, null));

        move = new Move(12, 16, board);
        newBoard = new Board(board);
        newBoard.movePiece(move);
        expectedNodes.add(new AlphaBetaSearchNode(newBoard, expectedDepth, expectedColor, null));

        // create set of actual search nodes
        Set<AlphaBetaSearchNode> actualSearchNodes = new HashSet<>();
        actualSearchNodes.addAll(children);

        // check that expected equals actual
        assertEquals(expectedNodes, actualSearchNodes);
    }
}

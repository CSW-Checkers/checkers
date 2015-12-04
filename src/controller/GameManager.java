package controller;

import java.util.HashMap;

import model.Board;
import model.ComputerPlayer;
import model.PieceColor;
import model.Player;
import model.Strategy;
import model.ai.evaluation.BackRowCountEvaluator;
import model.ai.evaluation.BoardEvaluatorInterface;
import model.ai.evaluation.BoardEvaluatorSummator;
import model.ai.evaluation.GameOverEvaluator;
import model.ai.evaluation.KingCountEvaluator;
import model.ai.evaluation.PawnCountEvaluator;
import model.ai.evaluation.PawnDistanceToKingedEvaluator;
import view.cli.CommandLineHelper;

public class GameManager {
    public static void main(String[] args) {
        final GameManager gameManager = new GameManager();
        gameManager.refineStrategy();
    }

    private Player blackPlayer;
    private Board gameBoard;
    private Player whitePlayer;

    private void initializeComputerPlayers() {
        final Strategy blackStrategy = getStartingStrategy(PieceColor.BLACK);
        final Strategy whiteStrategy = getStartingStrategy(PieceColor.WHITE);

        this.blackPlayer = new ComputerPlayer(PieceColor.BLACK, blackStrategy);
        this.whitePlayer = new ComputerPlayer(PieceColor.WHITE, whiteStrategy);
    }

    private Strategy getStartingStrategy(PieceColor color) {
        final HashMap<BoardEvaluatorInterface, Double> weightMap = new HashMap<BoardEvaluatorInterface, Double>();
        
        if (color.equals(PieceColor.BLACK)) {
            weightMap.put(PawnCountEvaluator.getInstance(), 1.0);
            weightMap.put(KingCountEvaluator.getInstance(), 1.4);
//            weightMap.put(BackRowCountEvaluator.getInstance(), 0.25);
//            weightMap.put(GameOverEvaluator.getInstance(), 1000.0);
//            weightMap.put(PawnDistanceToKingedEvaluator.getInstance(), 0.125);
        } else {
            weightMap.put(PawnCountEvaluator.getInstance(), 1.0);
            weightMap.put(KingCountEvaluator.getInstance(), 10.0);
//            weightMap.put(BackRowCountEvaluator.getInstance(), 1.0);
//            weightMap.put(GameOverEvaluator.getInstance(), 1000.0);
//            weightMap.put(PawnDistanceToKingedEvaluator.getInstance(), 0.25);
        }
        
        return new Strategy(new BoardEvaluatorSummator(), color, weightMap);
    }
    private void displayWinner(Board endingBoard) {
        if (endingBoard.isDrawState()) {
            System.out.println("Draw");
        } else if (endingBoard.playerHasLost(PieceColor.WHITE)) {
            System.out.println("Black wins");
        } else {
            System.out.println("White wins");
        }

        System.out.println("White pieces: " + endingBoard.getTotalNumberOfWhitePieces());
        System.out.println("Black pieces: " + endingBoard.getTotalNumberOfBlackPieces());
        CommandLineHelper.printBoard(endingBoard);
    }

    private void refineStrategy() {
        this.initializeComputerPlayers();
        
        for (int i = 0; i < 100; i++) {
            playGame();
            updateWeights();
        }
    }
    
    private void updateWeights() {
        // TODO Auto-generated method stub
        
    }

    private Player getOtherPlayer(Player currentPlayer) {
        Player playerToReturn;
        if (currentPlayer.getColor().equals(PieceColor.BLACK)) {
            playerToReturn = this.whitePlayer;
        } else {
            playerToReturn = this.blackPlayer;
        }
        return playerToReturn;
    }

    private void playGame() {
        this.gameBoard = new Board();
        
        PieceColor currentColor = PieceColor.BLACK;
        Player currentPlayer = this.blackPlayer;
        int moveCount = 0;
        while (!this.gameBoard.isEndState(currentColor)) {
            moveCount++;
            currentPlayer.makeMove(this.gameBoard);
            currentColor = currentColor.getOppositeColor();
            currentPlayer = this.getOtherPlayer(currentPlayer);
        }
        System.out.println("Moves: " + moveCount);
        this.displayWinner(this.gameBoard);
    }
}

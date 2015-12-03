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
        gameManager.playGame();
    }

    private Player blackPlayer;
    private final Board gameBoard = new Board();
    private Player whitePlayer;

    private void choosePlayerTypes() {
        // @SuppressWarnings("resource")
        // final Scanner reader = new Scanner(System.in);
        // System.out.println("Select 1 for Computer. Select 2 for Human.");
        // for (final PieceColor color : PieceColor.values()) {
        // System.out.print("Select player type for " + color + ": ");
        // final int selection = reader.nextInt();
        // if (color.equals(PieceColor.BLACK)) {
        // if (selection == 1) {
        // this.blackPlayer = new ComputerPlayer(color);
        // } else if (selection == 2) {
        // this.blackPlayer = new HumanPlayer(color);
        // }
        // } else {
        // if (selection == 1) {
        // this.whitePlayer = new ComputerPlayer(color);
        // } else if (selection == 2) {
        // this.whitePlayer = new HumanPlayer(color);
        // }
        // }
        // }

        final HashMap<BoardEvaluatorInterface, Double> blackWeightMap = new HashMap<BoardEvaluatorInterface, Double>();
        blackWeightMap.put(PawnCountEvaluator.getInstance(), 1.0);
        blackWeightMap.put(KingCountEvaluator.getInstance(), 1.0);
        blackWeightMap.put(BackRowCountEvaluator.getInstance(), 1.0);
        blackWeightMap.put(GameOverEvaluator.getInstance(), 1000.0);
        blackWeightMap.put(PawnDistanceToKingedEvaluator.getInstance(), 1.0);

        final HashMap<BoardEvaluatorInterface, Double> whiteWeightMap = new HashMap<BoardEvaluatorInterface, Double>();
        whiteWeightMap.put(PawnCountEvaluator.getInstance(), 2.0);
        whiteWeightMap.put(KingCountEvaluator.getInstance(), 4.0);
        whiteWeightMap.put(BackRowCountEvaluator.getInstance(), 1.0);
        whiteWeightMap.put(GameOverEvaluator.getInstance(), 1000.0);
        whiteWeightMap.put(PawnDistanceToKingedEvaluator.getInstance(), 0.25);

        final Strategy blackStrategy = new Strategy(new BoardEvaluatorSummator(), PieceColor.BLACK,
                blackWeightMap);
        final Strategy whiteStrategy = new Strategy(new BoardEvaluatorSummator(), PieceColor.WHITE,
                whiteWeightMap);

        this.blackPlayer = new ComputerPlayer(PieceColor.BLACK, blackStrategy);
        this.whitePlayer = new ComputerPlayer(PieceColor.WHITE, whiteStrategy);
    }

    private void displayWinner(Board endingBoard) {
        if (endingBoard.isDrawState()) {
            System.out.println("Draw");
        } else if (endingBoard.playerHasLost(PieceColor.WHITE)) {
            System.out.println("Black wins");
        } else {
            System.out.println("White wins");
        }

        System.out.println("White pieces: " + endingBoard.getNumberOfWhitePieces());
        System.out.println("Black pieces: " + endingBoard.getNumberOfBlackPieces());
        CommandLineHelper.printBoard(endingBoard);
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
        this.choosePlayerTypes();

        PieceColor currentColor = PieceColor.BLACK;
        Player currentPlayer = this.blackPlayer;

        System.out.println("Game start");
        // CommandLineHelper.printBoard(this.gameBoard);
        while (!this.gameBoard.isEndState(currentColor)) {
            currentPlayer.makeMove(this.gameBoard);
            // CommandLineHelper.printBoard(this.gameBoard);
            currentColor = currentColor.getOppositeColor();
            currentPlayer = this.getOtherPlayer(currentPlayer);

            // try {
            // Thread.sleep(500);
            // } catch (final InterruptedException e) {
            // e.printStackTrace();
            // }
        }
        this.displayWinner(this.gameBoard);
    }
}

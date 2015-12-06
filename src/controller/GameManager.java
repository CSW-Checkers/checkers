package controller;

import java.util.HashMap;
import java.util.Random;

import model.Board;
import model.ComputerPlayer;
import model.PieceColor;
import model.Player;
import model.Strategy;
import model.ai.evaluation.BoardEvaluatorInterface;
import model.ai.evaluation.BoardEvaluatorSummator;
import model.ai.evaluation.BoardPositionEvaluator;
import model.ai.evaluation.GameOverEvaluator;
import model.ai.evaluation.KingCountEvaluator;
import model.ai.evaluation.PawnCountEvaluator;
import view.cli.CommandLineHelper;

public class GameManager {
    public static void main(String[] args) {
        final GameManager gameManager = new GameManager();
        gameManager.refineStrategy();
    }

    private Player blackPlayer;
    private Board gameBoard;
    private Player whitePlayer;
    private Random randGauss = new Random();

    private void initializeComputerPlayers() {
        final Strategy blackStrategy = getStartingStrategy(PieceColor.BLACK);
        final Strategy whiteStrategy = getStartingStrategy(PieceColor.WHITE);

        this.blackPlayer = new ComputerPlayer(PieceColor.BLACK, blackStrategy);
        this.whitePlayer = new ComputerPlayer(PieceColor.WHITE, whiteStrategy);
    }

    private Strategy getStartingStrategy(PieceColor color) {
        final HashMap<BoardEvaluatorInterface, Double> weightMap = new HashMap<BoardEvaluatorInterface, Double>();

        if (color.equals(PieceColor.BLACK)) {
            weightMap.put(PawnCountEvaluator.getInstance(), 10.0);
            weightMap.put(KingCountEvaluator.getInstance(), 15.0);
            // weightMap.put(BackRowCountEvaluator.getInstance(), 0.25);
            weightMap.put(GameOverEvaluator.getInstance(), 1000.0);
            // weightMap.put(PawnDistanceToKingedEvaluator.getInstance(), 0.1);
            // weightMap.put(TradePieceEvaluator.getInstance(), 500.0);
            weightMap.put(BoardPositionEvaluator.getInstance(), 0.1);

        } else {
            weightMap.put(PawnCountEvaluator.getInstance(), 1.0);
            weightMap.put(KingCountEvaluator.getInstance(), 1.5);
            // weightMap.put(BackRowCountEvaluator.getInstance(), 1.0);
            weightMap.put(GameOverEvaluator.getInstance(), 1000.0);
            // weightMap.put(PawnDistanceToKingedEvaluator.getInstance(), 1.0);
        }

        return new Strategy(new BoardEvaluatorSummator(), color, weightMap);
    }

    private void displayWinner(Board endingBoard) {
        Player losingPlayer = getLosingPlayer(endingBoard);

        if (losingPlayer == null) {
            System.out.println("Draw");
        } else {
            Player winningPlayer = getOtherPlayer(losingPlayer);
            System.out.println(winningPlayer.getColor() + " wins");
        }

        System.out.println("White pieces: " + endingBoard.getTotalNumberOfWhitePieces());
        System.out.println("Black pieces: " + endingBoard.getTotalNumberOfBlackPieces());
    }

    private Player getLosingPlayer(Board endingBoard) {
        if (endingBoard.isDrawState()) {
            return null;
        } else if (endingBoard.playerHasLost(PieceColor.WHITE)) {
            return this.whitePlayer;
        } else {
            return this.blackPlayer;
        }
    }

    private void refineStrategy() {
        this.initializeComputerPlayers();

        for (int i = 1; i < 50; i++) {
            System.out.println("GAME #: " + i);
            playGame();
            CommandLineHelper.printBoard(this.gameBoard);
            CommandLineHelper.printFinalPositions(this.gameBoard);
            // updateWeights();
            // printWeights();
            System.exit(0);
        }
    }

    private void printWeights() {
        System.out.println("##-- Updated Weights After Game --##");
        printWeightsOfPlayer((ComputerPlayer) whitePlayer);
        printWeightsOfPlayer((ComputerPlayer) blackPlayer);
    }

    private void updateWeights() {
        ComputerPlayer losingPlayer = (ComputerPlayer) getLosingPlayer(this.gameBoard);

        if (losingPlayer == null) {
            // Draw state

            // Determine superior player in draw
            int whitePieces = gameBoard.getTotalNumberOfWhitePieces();
            int blackPieces = gameBoard.getTotalNumberOfBlackPieces();

            if (whitePieces > blackPieces) {
                losingPlayer = (ComputerPlayer) this.blackPlayer;
            } else if (whitePieces < blackPieces) {
                losingPlayer = (ComputerPlayer) this.whitePlayer;
            } else {
                losingPlayer = (ComputerPlayer) pickRandomPlayer();
                System.out.println("true tie");
            }
        }

        Strategy strategy = losingPlayer.getStrategy();
        HashMap<BoardEvaluatorInterface, Double> startingWeightMap = strategy
                .getEvaluatorWeightMap();

        for (BoardEvaluatorInterface evaluatorKey : startingWeightMap.keySet()) {
            Double weightToPerturb = startingWeightMap.get(evaluatorKey);

            // mean of 0.0, stdev of 1.0
            double gaussian = randGauss.nextGaussian();
            weightToPerturb = Math.abs(weightToPerturb + gaussian);

            startingWeightMap.replace(evaluatorKey, weightToPerturb);
        }
    }

    private Player pickRandomPlayer() {
        Player randomPlayer = null;
        Random random = new Random();
        int selection = random.nextInt(2);
        if (selection == 0) {
            randomPlayer = this.whitePlayer;
        } else if (selection == 1) {
            randomPlayer = this.blackPlayer;
        }

        return randomPlayer;
    }

    private void printWeightsOfPlayer(ComputerPlayer computerPlayer) {
        System.out.println(computerPlayer.getColor() + " weights:");

        HashMap<BoardEvaluatorInterface, Double> weightMap = computerPlayer.getStrategy()
                .getEvaluatorWeightMap();

        for (BoardEvaluatorInterface evaluatorKey : weightMap.keySet()) {
            String evaluatorName = evaluatorKey.getClass().getSimpleName();
            Double weight = weightMap.get(evaluatorKey);
            System.out.println(evaluatorName + ": " + weight);
        }
        System.out.println(); // Blank line
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
        CommandLineHelper.printFinalPositions(this.gameBoard);

        PieceColor currentColor = PieceColor.BLACK;
        Player currentPlayer = this.blackPlayer;
        int moveCount = 0;
        while (!this.gameBoard.isEndState(currentColor)) {
            moveCount++;
            currentPlayer.makeMove(this.gameBoard);
            CommandLineHelper.printBoard(this.gameBoard);
            currentColor = currentColor.getOppositeColor();
            currentPlayer = this.getOtherPlayer(currentPlayer);
        }
        System.out.println("Moves: " + moveCount);
        this.displayWinner(this.gameBoard);
    }
}

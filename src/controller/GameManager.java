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
import model.ai.evaluation.TradePieceEvaluator;

public class GameManager {
    public static void main(String[] args) {
        final GameManager gameManager = new GameManager();
        gameManager.refineStrategy();
    }

    private Player blackPlayer;
    private Board gameBoard;
    private Player whitePlayer;
    private Random randGauss = new Random();
    private int drawCount = 0;
    private int whiteWins = 0;
    private int blackWins = 0;

    private void displayWinner(Board endingBoard) {
        Player losingPlayer = this.getLosingPlayer(endingBoard);

        if (losingPlayer == null) {
            this.drawCount++;
            System.out.print("Draw");
        } else {
            Player winningPlayer = this.getOtherPlayer(losingPlayer);
            System.out.print(winningPlayer.getColor() + " wins");
            if (winningPlayer.getColor() == PieceColor.WHITE) {
                this.whiteWins++;
            } else {
                this.blackWins++;
            }
        }

        System.out.print(" White pieces: " + endingBoard.getTotalNumberOfWhitePieces());
        System.out.println(" Black pieces: " + endingBoard.getTotalNumberOfBlackPieces());
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

    private Player getOtherPlayer(Player currentPlayer) {
        Player playerToReturn;
        if (currentPlayer.getColor().equals(PieceColor.BLACK)) {
            playerToReturn = this.whitePlayer;
        } else {
            playerToReturn = this.blackPlayer;
        }
        return playerToReturn;
    }

    private Strategy getStartingStrategy(PieceColor color) {
        final HashMap<BoardEvaluatorInterface, Double> weightMap = new HashMap<BoardEvaluatorInterface, Double>();

        if (color.equals(PieceColor.BLACK)) {
            weightMap.put(PawnCountEvaluator.getInstance(), 10.0);
            weightMap.put(KingCountEvaluator.getInstance(), 15.0);
            // weightMap.put(BackRowCountEvaluator.getInstance(), 0.25);
            weightMap.put(GameOverEvaluator.getInstance(), 1000.0);
            // weightMap.put(PawnDistanceToKingedEvaluator.getInstance(), 0.1);
            weightMap.put(TradePieceEvaluator.getInstance(), 0.1);
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

    private void initializeComputerPlayers() {
        final Strategy blackStrategy = this.getStartingStrategy(PieceColor.BLACK);
        final Strategy whiteStrategy = this.getStartingStrategy(PieceColor.WHITE);

        this.blackPlayer = new ComputerPlayer(PieceColor.BLACK, blackStrategy);
        this.whitePlayer = new ComputerPlayer(PieceColor.WHITE, whiteStrategy);
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

    private void playGame() {
        this.gameBoard = new Board();
        // CommandLineHelper.printFinalPositions(this.gameBoard);

        PieceColor currentColor = PieceColor.BLACK;
        Player currentPlayer = this.blackPlayer;
        int moveCount = 0;
        while (!this.gameBoard.isEndState(currentColor)) {
            moveCount++;
            currentPlayer.makeMove(this.gameBoard);
            // System.out.print(currentPlayer.makeMove(this.gameBoard).toString());
            // System.out.print(" White Kings: " + this.gameBoard.getNumberOfWhiteKings());
            // System.out.print(" White Pawns: " + this.gameBoard.getNumberOfWhitePawns());
            // System.out.print(" Black Kings: " + this.gameBoard.getNumberOfBlackKings());
            // System.out.println(" Black Pawns: " + this.gameBoard.getNumberOfBlackPawns());
            // CommandLineHelper.printBoard(this.gameBoard);
            currentColor = currentColor.getOppositeColor();
            currentPlayer = this.getOtherPlayer(currentPlayer);
        }
        System.out.println("Moves: " + moveCount);
        this.displayWinner(this.gameBoard);
    }

    private void printStats() {
        System.out.println("White Wins: " + this.whiteWins + " Black Wins: " + this.blackWins
                + " Draws: " + this.drawCount);

    }

    private void printWeights() {
        System.out.println("##-- Updated Weights After Game --##");
        this.printWeightsOfPlayer((ComputerPlayer) this.whitePlayer);
        this.printWeightsOfPlayer((ComputerPlayer) this.blackPlayer);
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

    private void refineStrategy() {
        this.initializeComputerPlayers();

        for (int i = 1; i <= 50; i++) {
            System.out.println("GAME #: " + i);
            this.playGame();
            // CommandLineHelper.printBoard(this.gameBoard);
            // CommandLineHelper.printFinalPositions(this.gameBoard);
            // updateWeights();
            // printWeights();
            // System.exit(0);
        }

        this.printStats();
    }

    private void updateWeights() {
        ComputerPlayer losingPlayer = (ComputerPlayer) this.getLosingPlayer(this.gameBoard);

        if (losingPlayer == null) {
            // Draw state

            // Determine superior player in draw
            int whitePieces = this.gameBoard.getTotalNumberOfWhitePieces();
            int blackPieces = this.gameBoard.getTotalNumberOfBlackPieces();

            if (whitePieces > blackPieces) {
                losingPlayer = (ComputerPlayer) this.blackPlayer;
            } else if (whitePieces < blackPieces) {
                losingPlayer = (ComputerPlayer) this.whitePlayer;
            } else {
                losingPlayer = (ComputerPlayer) this.pickRandomPlayer();
                System.out.println("true tie");
            }
        }

        Strategy strategy = losingPlayer.getStrategy();
        HashMap<BoardEvaluatorInterface, Double> startingWeightMap = strategy
                .getEvaluatorWeightMap();

        for (BoardEvaluatorInterface evaluatorKey : startingWeightMap.keySet()) {
            Double weightToPerturb = startingWeightMap.get(evaluatorKey);

            // mean of 0.0, stdev of 1.0
            double gaussian = this.randGauss.nextGaussian();
            weightToPerturb = Math.abs(weightToPerturb + gaussian);

            startingWeightMap.replace(evaluatorKey, weightToPerturb);
        }
    }
}

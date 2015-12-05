package controller;

import java.util.Random;
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
        //CommandLineHelper.printBoard(endingBoard);
    }

    private void refineStrategy() {
        this.initializeComputerPlayers();
        
        for (int i = 0; i < 4; i++) {
            System.out.println("GAME #: " + (i + 1));
            playGame();
            updateWeights();
            System.out.println("##-- Updated Weights After Game --##");
            printWeightsOfPlayer((ComputerPlayer)whitePlayer);
            printWeightsOfPlayer((ComputerPlayer)blackPlayer);
        }
    }
    
    private void updateWeights() {
        ComputerPlayer player = null;
        if (this.gameBoard.isDrawState()) {
            //calculate how good each player did            
            int whitePieces = gameBoard.getTotalNumberOfWhitePieces();
            int blackPieces = gameBoard.getTotalNumberOfBlackPieces();
            if(whitePieces > blackPieces){
                player = (ComputerPlayer)this.blackPlayer;
            }else if (whitePieces < blackPieces){
                player = (ComputerPlayer)this.whitePlayer;
            }else{
                //MUST DECIDE WHAT TO DO IN A DRAW
                //right now I just jitter white's weights, but we'll want to do something different
                player = (ComputerPlayer)this.whitePlayer;//just so it doesn't crash later
                System.out.println("true tie");
            }
        } else if (this.gameBoard.playerHasLost(PieceColor.WHITE)) {
            // jitter white player's weights
            player = (ComputerPlayer)this.whitePlayer;
        } else {
            // jitter black player's weights
            player = (ComputerPlayer)this.blackPlayer;
        }
        Strategy strategy = player.getStrategy();
        HashMap<BoardEvaluatorInterface, Double> weightMapToPerturb = strategy.getEvaluatorWeightMap();
        for (BoardEvaluatorInterface evalInterface : weightMapToPerturb.keySet()) {
            Double weightToPerturbe = weightMapToPerturb.get(evalInterface);
            // mean of 0.0, stdev of 0.5
            double gaussian = randGauss.nextGaussian() * 5.0;// get Gaussian random number
            weightToPerturbe += gaussian;
            if (weightToPerturbe < 0.0) {// if it becomes negative
                weightToPerturbe = Math.abs(weightToPerturbe);// just make is small
            }
            weightMapToPerturb.replace(evalInterface, weightToPerturbe);
        }
    }
    
    private void printWeightsOfPlayer(ComputerPlayer cp){
        if(cp.getColor() == PieceColor.BLACK){
            System.out.println("Black's weights: ");
        }else{
            System.out.println("White's weights: ");
        }
        Strategy stg = cp.getStrategy();
        HashMap<BoardEvaluatorInterface, Double> weightMapToPrint = stg.getEvaluatorWeightMap();
        for (BoardEvaluatorInterface evalInterface : weightMapToPrint.keySet()) {
            System.out.println(evalInterface.toString() + ": " + weightMapToPrint.get(evalInterface));
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

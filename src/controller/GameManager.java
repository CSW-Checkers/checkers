package controller;

import java.util.Scanner;

import model.Board;
import model.ComputerPlayer;
import model.HumanPlayer;
import model.MoveGenerator;
import model.PieceColor;
import model.Player;
import view.cli.CommandLineHelper;

public class GameManager {
    public static void main(String[] args) {
        final GameManager gameManager = new GameManager();
        // TODO Specify player types for colors
        gameManager.playGame();
    }

    private Player blackPlayer;
    private final Board gameBoard = new Board();
    private Player whitePlayer;

    private void choosePlayerTypes() {
        @SuppressWarnings("resource")
        final Scanner reader = new Scanner(System.in);
        System.out.println("Select 1 for Computer. Select 2 for Human.");
        for (PieceColor color : PieceColor.values()) {
            System.out.print("Select player type for " + color + ": ");
            int selection = reader.nextInt();
            if (color.equals(PieceColor.BLACK)) {
                if (selection == 1) {
                    this.blackPlayer = new ComputerPlayer(color);
                } else if (selection == 2) {
                    this.blackPlayer = new HumanPlayer(color);
                }
            } else {
                if (selection == 1) {
                    this.whitePlayer = new ComputerPlayer(color);
                } else if (selection == 2) {
                    this.whitePlayer = new HumanPlayer(color);
                }
            }
        }
    }

    private void determineWinner() {
        final boolean whiteHasNoPieces = this.gameBoard.getNumberOfWhitePieces() == 0;
        final boolean whiteHasNoMoves = MoveGenerator
                .getAllPossibleMoves(this.gameBoard, PieceColor.WHITE).isEmpty();
        final boolean blackHasNoPieces = this.gameBoard.getNumberOfBlackPieces() == 0;
        final boolean blackHasNoMoves = MoveGenerator
                .getAllPossibleMoves(this.gameBoard, PieceColor.BLACK).isEmpty();

        final boolean whiteWins = blackHasNoPieces || blackHasNoMoves;
        final boolean blackWins = whiteHasNoPieces || whiteHasNoMoves;

        if (whiteWins) {
            System.out.println("White wins");
        } else if (blackWins) {
            System.out.println("Black wins");
        } else {
            System.err.println("Invalid ending state!");
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
        this.choosePlayerTypes();

        PieceColor currentColor = PieceColor.BLACK;
        Player currentPlayer = this.blackPlayer;

        System.out.println("Game start");
        CommandLineHelper.printBoard(this.gameBoard);
        while (!this.gameBoard.isEndState(currentColor)) {
            currentPlayer.makeMove(this.gameBoard);
            CommandLineHelper.printBoard(this.gameBoard);
            currentColor = currentColor.getOppositeColor();
            currentPlayer = this.getOtherPlayer(currentPlayer);

            try {
                Thread.sleep(500);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.determineWinner();
    }
}

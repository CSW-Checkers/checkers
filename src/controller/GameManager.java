package controller;

import model.Board;
import model.ComputerPlayer;
import model.MoveGenerator;
import model.PieceColor;
import model.Player;

public class GameManager {
    public static void main(String[] args) {
        final GameManager gameManager = new GameManager();
        // TODO Specify player types for colors
        gameManager.playGame();
    }

    private final Player blackPlayer = new ComputerPlayer(PieceColor.BLACK);
    private final Board gameBoard = new Board();
    private final Player whitePlayer = new ComputerPlayer(PieceColor.WHITE);

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
        System.out.println("Game start");
        PieceColor currentColor = PieceColor.BLACK;
        Player currentPlayer = this.blackPlayer;
        while (!this.gameBoard.isEndState(currentColor)) {
            currentPlayer.makeMove(this.gameBoard);
            // Show board view
            currentColor = currentColor.getOppositeColor();
            currentPlayer = this.getOtherPlayer(currentPlayer);
            try {
                Thread.sleep(250);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }

        }

        this.determineWinner();
    }

}

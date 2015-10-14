package view.cli;

import java.util.ArrayList;

import model.Board;
import model.PieceInterface;

public final class CommandLineHelper {
    private static final String blackKing = "{B}";
    private static final String blackPiece = " B ";
    private static final String nullPiece = "   ";
    private static final String whiteKing = "{W}";
    private static final String whitePiece = " W ";

    private static String determinePieceToPrint(Board board, int position) {
        String pieceToPrint;
        PieceInterface currentPiece = board.getPiece(position);
        if (currentPiece.isNull()) {
            pieceToPrint = nullPiece;
        } else if (currentPiece.isBlack()) {
            if (currentPiece.isKing()) {
                pieceToPrint = blackKing;
            } else {
                pieceToPrint = blackPiece;
            }
        } else {
            if (currentPiece.isKing()) {
                pieceToPrint = whiteKing;
            } else {
                pieceToPrint = whitePiece;
            }
        }
        return pieceToPrint;
    }

    private static ArrayList<Integer> getSquarePositionsForRow(int row) {
        ArrayList<Integer> positions = new ArrayList<Integer>();
        final int maxPosition = row * 4;
        for (int i = 3; i >= 0; i--) {
            positions.add(maxPosition - i);
        }
        return positions;
    }

    private static boolean isOdd(int row) {
        return (row % 2 == 1);
    }

    public static void printBoard(Board board) {
        printRowDivider();
        for (int row = 1; row <= 8; row++) {
            if (isOdd(row)) {
                printOddRow(board, row);
            } else {
                printEvenRow(board, row);
            }
            printRowDivider();
        }
    }

    private static void printBusinessRow(Board board, int row) {
        String pieceToPrint;
        for (int position : getSquarePositionsForRow(row)) {
            pieceToPrint = determinePieceToPrint(board, position);
            if (isOdd(row)) {
                System.out.printf("||#####|| %s ", pieceToPrint);
            } else {
                System.out.printf("|| %s ||#####", pieceToPrint);
            }
        }
        System.out.println("||");
    }

    private static void printEvenFillerRow() {
        System.out.println("||     ||#####||     ||#####||     ||#####||     ||#####||");
    }

    private static void printEvenRow(Board board, int row) {
        printNumberedRow(row);
        printBusinessRow(board, row);
        printEvenFillerRow();

    }

    private static void printNumberedRow(int row) {
        for (int position : getSquarePositionsForRow(row)) {
            if (isOdd(row)) {
                System.out.printf("||#####||%-2d   ", position);
            } else {
                System.out.printf("||%-2d   ||#####", position);
            }
        }
        System.out.println("||");
    }

    private static void printOddFillerRow() {
        System.out.println("||#####||     ||#####||     ||#####||     ||#####||     ||");
    }

    private static void printOddRow(Board board, int row) {
        printNumberedRow(row);
        printBusinessRow(board, row);
        printOddFillerRow();
    }

    private static void printRowDivider() {
        System.out.println("||======================================================||");
    }
}

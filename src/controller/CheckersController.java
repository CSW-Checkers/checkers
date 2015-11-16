package controller;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Board;
import model.ComputerPlayer;
import model.MoveBuilder;
import model.MoveGenerator;
import model.MoveInterface;
import model.MultiJump;
import model.PieceColor;
import model.PieceInterface;
import model.Player;
import model.SingleJump;
import model.Square;
import view.CheckersPane;

public class CheckersController extends Application {

    private class EnterHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent ke) {
            final KeyCode keyCode = ke.getCode();
            if (keyCode == KeyCode.ENTER) {
                final String moveString = CheckersController.this.botTextArea.getText().trim();
                /* Now I will get the move of the human player and change board */
                MoveInterface moveToMake = null;
                try {
                    moveToMake = MoveBuilder.buildMove(moveString,
                            CheckersController.this.gameBoard);
                } catch (final Exception e) {
                    CheckersController.this.gameText.append("\n" + e.toString());
                    CheckersController.this.gameTextBox
                            .setText(CheckersController.this.gameText.toString());
                    CheckersController.this.botTextArea.clear();
                    return;
                }

                if (MoveGenerator.getAllPossibleMoves(CheckersController.this.gameBoard,
                        CheckersController.this.humanPlayerColor).contains(moveToMake)) {
                    CheckersController.this.gameBoard.movePiece(moveToMake);

                    String color;
                    if (CheckersController.this.humanPlayerColor == PieceColor.BLACK) {
                        color = "Black";
                    } else {
                        color = "White";
                    }

                    CheckersController.this.gameText
                            .append("\n" + color + "\'s move: " + moveString);
                    CheckersController.this.gameTextBox
                            .setText(CheckersController.this.gameText.toString());
                    /*code for animating move*/
                    CheckersController.this.root.computerIsMoving = false;
                    CheckersController.this.animateMove(moveToMake);
                } else {// if the move is not a valid move...

                    String color;
                    if (CheckersController.this.humanPlayerColor == PieceColor.BLACK) {
                        color = "Black";
                    } else {
                        color = "White";
                    }
                    CheckersController.this.gameText.append("\n " + color + "\'s move " + moveString
                            + " was not found in list of possible legal moves.");
                    CheckersController.this.gameTextBox
                            .setText(CheckersController.this.gameText.toString());
                }
                CheckersController.this.botTextArea.clear();
                CheckersController.this.botTextArea.home();
                CheckersController.this.scrollPane.setVvalue(1.0);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);// causes start() to be called
    }

    TextArea botTextArea;

    private Player computerPlayer;
    private PieceColor computerPlayerColor;
    private final Board gameBoard = new Board();
    StringBuffer gameText = new StringBuffer("");
    Text gameTextBox;

    private PieceColor humanPlayerColor;
    // all drawing on the canvas and repositioning of the pieces occurs through
    // this reference.
    CheckersPane root;
    ScrollPane scrollPane;

    public void animateMove(MoveInterface moveToMake) {
        if (moveToMake instanceof MultiJump) {
            final ArrayList<String> movesForView = new ArrayList<>();
            final MultiJump mJump = (MultiJump) moveToMake;
            final ArrayList<SingleJump> subJumps = mJump.getSubJumps();

            final SingleJump firstSingleJump = subJumps.get(0);
            final Square startignSquare = firstSingleJump.getStartingSquare();
            final int startRow = startignSquare.getRowNumber() - 1;
            final int startCol = startignSquare.getColumnNumber() - 1;
            movesForView.add(startRow + "," + startCol);

            final ArrayList<String> jumpedPositions = new ArrayList<>();
            for (final SingleJump singleJump : subJumps) {
                final Square endingSquare = singleJump.getEndingSquare();
                final int endRow = endingSquare.getRowNumber() - 1;
                final int endCol = endingSquare.getColumnNumber() - 1;
                movesForView.add(endRow + "," + endCol);

                /* now for adding the jumped positions*/
                final Square squareOfJumpedPosition = singleJump.getJumpedSquares().get(0);
                final int row = squareOfJumpedPosition.getRowNumber() - 1;
                final int col = squareOfJumpedPosition.getColumnNumber() - 1;

                jumpedPositions.add(row + "," + col);
            }
            // gets piece to check if it is a king at end of turn
            boolean isKingAtEndOfHopping = false;
            final PieceInterface piece = moveToMake.getPiece();
            isKingAtEndOfHopping = piece.isKing() ? true : false;

            CheckersController.this.root.movePieceToPositionsAndRemovePiecesAndKing(movesForView,
                    jumpedPositions, isKingAtEndOfHopping);
        } else if (moveToMake instanceof SingleJump) {
            final ArrayList<String> moveForView = new ArrayList<>();
            final SingleJump sjump = (SingleJump) moveToMake;

            final Square startingSquare = moveToMake.getStartingSquare();
            final int startRow = startingSquare.getRowNumber() - 1;
            final int startCol = startingSquare.getColumnNumber() - 1;
            moveForView.add(startRow + "," + startCol);
            final Square endingSquare = moveToMake.getEndingSquare();
            final int endRow = endingSquare.getRowNumber() - 1;
            final int endCol = endingSquare.getColumnNumber() - 1;
            moveForView.add(endRow + "," + endCol);

            final Square squareOfJumpedPosition = sjump.getJumpedSquares().get(0);
            final int row = squareOfJumpedPosition.getRowNumber() - 1;
            final int col = squareOfJumpedPosition.getColumnNumber() - 1;
            final ArrayList<String> jumpedPosition = new ArrayList<>();
            jumpedPosition.add(row + "," + col);

            // gets piece to check if it is a king at end of turn
            boolean isKingAtEndOfHopping = false;
            final PieceInterface piece = moveToMake.getPiece();
            isKingAtEndOfHopping = piece.isKing() ? true : false;

            CheckersController.this.root.movePieceToPositionsAndRemovePiecesAndKing(moveForView,
                    jumpedPosition, isKingAtEndOfHopping);
        } else {// is just a move
            final ArrayList<String> moveForView = new ArrayList<>();
            final Square startingSquare = moveToMake.getStartingSquare();
            final Square endingSquare = moveToMake.getEndingSquare();
            final int startRow = startingSquare.getRowNumber() - 1;
            final int startCol = startingSquare.getColumnNumber() - 1;
            moveForView.add(startRow + "," + startCol);
            final int endRow = endingSquare.getRowNumber() - 1;
            final int endCol = endingSquare.getColumnNumber() - 1;
            moveForView.add(endRow + "," + endCol);

            // gets piece to check if it is a king at end of turn
            boolean isKingAtEndOfHopping = false;
            final PieceInterface piece = moveToMake.getPiece();
            isKingAtEndOfHopping = piece.isKing() ? true : false;

            CheckersController.this.root.movePieceToPositionsAndRemovePiecesAndKing(moveForView,
                    null, isKingAtEndOfHopping);
        }
    }

    public void computerFinishedMove() {
        System.out.println("computer finished");
        if (this.gameBoard.isDrawState()) {
            this.gameText.append("\n Draw");
            this.gameTextBox.setText(this.gameText.toString());
        } else if (this.gameBoard.isEndState(this.humanPlayerColor)) {
            String color;
            if (this.computerPlayerColor == PieceColor.BLACK) {
                color = "Black";
            } else {
                color = "White";
            }
            this.gameText.append("\n " + color + " Has Won");
            this.gameTextBox.setText(this.gameText.toString());
        }
    }

    /**
     * When this is called, that means that the human player has finished a move and it is the
     * computer's turn to move
     */
    public void humanFinishedMove() {
        System.out.println("human finished");
        if (this.gameBoard.isDrawState()) {
            this.gameText.append("\n Draw");
            this.gameTextBox.setText(this.gameText.toString());
        } else if (this.gameBoard.isEndState(this.computerPlayerColor)) {
            String color;
            if (this.computerPlayerColor == PieceColor.BLACK) {
                color = "Black";
            } else {
                color = "White";
            }
            this.gameText.append("\n " + color + " Has Won");
            this.gameTextBox.setText(this.gameText.toString());
        }

        final MoveInterface moveToMake = this.computerPlayer.makeMove(this.gameBoard);
        String color;
        if (this.computerPlayerColor == PieceColor.BLACK) {
            color = "Black";
        } else {
            color = "White";
        }
        CheckersController.this.gameText
                .append("\n" + color + "\'s move: " + moveToMake.toString());
        CheckersController.this.gameTextBox.setText(CheckersController.this.gameText.toString());
        System.out.println(moveToMake);
        this.root.computerIsMoving = true;
        this.animateMove(moveToMake);

    }

    /*
     * This is where are app starts; place any model or other logic below the initialization of the
     * view.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        /*
         * Will's Note: This is where I initialize the view and add the event handlers
         */
        boolean userInputCorrect = false;
        while (userInputCorrect == false) {
            final Scanner keyboard = new Scanner(System.in);
            System.out.println("will the human play as black(top) or white(bottom)? b/w: ");
            final String response = keyboard.next();
            if (response.equalsIgnoreCase("b")) {// top
                this.humanPlayerColor = PieceColor.BLACK;
                this.computerPlayerColor = PieceColor.WHITE;
                this.computerPlayer = new ComputerPlayer(this.computerPlayerColor);
                userInputCorrect = true;
            } else if (response.equalsIgnoreCase("w")) {
                this.humanPlayerColor = PieceColor.WHITE;
                this.computerPlayerColor = PieceColor.BLACK;
                this.computerPlayer = new ComputerPlayer(this.computerPlayerColor);
                userInputCorrect = true;
            } else {
                System.out.println("That is not valid input; type b or w.");
                System.out.println("will the human play as black(top) or white(bottom)? b/w: ");
            }
            keyboard.close();
        }

        final Group rootGroup = new Group();
        primaryStage.setTitle("checkers");
        this.root = new CheckersPane(this.gameBoard);

        this.gameTextBox = new Text();
        this.gameTextBox.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        this.gameText.append("welcome to checkers\n");
        this.gameTextBox.setText(this.gameText.toString());
        this.gameTextBox.prefWidth(400);
        this.gameTextBox.prefHeight(580);

        this.scrollPane = new ScrollPane();
        this.scrollPane.setContent(this.gameTextBox);
        this.scrollPane.setFitToWidth(true);
        this.scrollPane.setPrefWidth(400);
        this.scrollPane.setPrefHeight(580);

        final VBox vBox = new VBox();
        vBox.getChildren().add(this.scrollPane);

        this.botTextArea = new TextArea();
        this.botTextArea.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        this.botTextArea.setOnKeyPressed(new EnterHandler());
        this.botTextArea.setPrefWidth(400);
        this.botTextArea.setPrefHeight(120);
        vBox.getChildren().addAll(this.botTextArea);

        final HBox hBox = new HBox();
        hBox.getChildren().add(this.root);
        hBox.getChildren().add(vBox);

        rootGroup.getChildren().add(hBox);
        primaryStage.setScene(new Scene(rootGroup, 1100, 700));
        primaryStage.show();

        // set the controller to listen for human has finished moving events
        this.root.addHumanFinishedMoveListener(this);
        this.root.addComputerFinishedMoveListener(this);

        /*
         * End of view initialization and setting of event handlers
         */
        if (this.computerPlayerColor.equals(PieceColor.BLACK)) {
            final MoveInterface moveToMake = this.computerPlayer.makeMove(this.gameBoard);
            String color;
            if (this.computerPlayerColor == PieceColor.BLACK) {
                color = "Black";
            } else {
                color = "White";
            }

            CheckersController.this.gameText
                    .append("\n" + color + "\'s move: " + moveToMake.toString());
            CheckersController.this.gameTextBox
                    .setText(CheckersController.this.gameText.toString());
            System.out.println(moveToMake);
            this.root.computerIsMoving = true;
            this.animateMove(moveToMake);
        }
    }
}

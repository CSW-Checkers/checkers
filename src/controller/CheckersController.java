package controller;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Board;
import model.MoveGenerator;
import model.MoveInterface;
import model.PieceColor;
import view.CheckersPane;

public class CheckersController extends Application implements HumanFinishedMove,
        ComputerFinishedMove {

    /**
     * @author Will This handler continually drags the piece that the user has already clicked on.
     *         The pieces center will be updated to be wherever the cursor's xy coords are.
     */
    private class MouseDraggedHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent drag) {
            if (CheckersController.this.root.imageHasBeenSelected()) {
                double x = drag.getX();
                double y = drag.getY();
                CheckersController.this.root.moveSelectedImageToCurrentXY(x, y);
            }
        }
    }

    /**
     * @author Will This Event Handler moves the peice's center to the xy coords of the user's
     *         cursor when the user clicks on a piece.
     */
    private class MousePressedHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent press) {
            double x = press.getX();
            double y = press.getY();
            // ascertain if an image was pressed
            CheckersController.this.root.selectImage(x, y);
            if (CheckersController.this.root.imageHasBeenSelected()) {
                CheckersController.this.root.moveSelectedImageToCurrentXY(x, y);
            }
        }
    }

    /**
     * @author Will This event handler places the selected piece on the square that the user
     *         released the mouse button over.
     */
    private class MouseReleasedHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent release) {
            if (CheckersController.this.root.imageHasBeenSelected()) {
                double x = release.getX();
                double y = release.getY();
                /*
                 * now I have to find out if the user has selected an image. If that is true, then
                 * we query the model if the user can move the piece to the position that they
                 * released the mouse on.
                 */
                if (CheckersController.this.root.imageHasBeenSelected()) {// if the image is
                    // selected, we might move
                    int row = CheckersController.this.root.getRowForY(y);
                    int col = CheckersController.this.root.getColForX(x);
                    /*
                     * here I will ask the model if it is a valid move, if so the piece will move to
                     * the position.
                     */
                    CheckersController.this.root.placeInSquareOfXY(x, y);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);// causes start() to be called
    }

    // all drawing on the canvas and repositioning of the pieces occurs through
    // this reference.
    CheckersPane root;
    Board board;

    @Override
    public void computerFinishedMove() {
        System.out.println("computer finished");
        // this.root.setDisable(false);// enables root

    }

    /**
     * When this is called, that means that the human player has finished a move and it is the
     * computer's turn to move
     */
    @Override
    public void humanFinishedMove(ArrayList<String> moves) {
        System.out.println("human finished");
        // this.root.setDisable(true);// disables root

        // let computer calculate move
        // execute the move method of the view to reflect this
        // have a handler in the view for the computer finished moving.
        // when the computer's move has finished
        MoveGenerator mg = new MoveGenerator(this.board);
        Set<MoveInterface> actualMoves = mg.getAllPossibleMoves(PieceColor.BLACK);

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
        primaryStage.setTitle("checkers");
        // root = new CheckersPane();
        this.board = new Board();
        this.root = new CheckersPane(this.board);
        Scene checkersScene = new Scene(this.root);
        primaryStage.setScene(checkersScene);
        primaryStage.show();

        checkersScene.addEventHandler(MouseEvent.MOUSE_PRESSED, new MousePressedHandler());
        checkersScene.addEventHandler(MouseEvent.MOUSE_DRAGGED, new MouseDraggedHandler());
        checkersScene.addEventHandler(MouseEvent.MOUSE_RELEASED, new MouseReleasedHandler());

        // set the controller to listen for human has finished moving events
        this.root.addHumanFinishedMoveListener(this);
        this.root.addComputerFinishedMoveListener(this);

        /*
         * End of view initialization and setting of event handlers
         */
        // human controls zerglings, computer controls stalkers
        System.out.println("Who do you want to play as: stalkers(top) or zerglings(botoom)?(s/z)");
        Scanner keyboard = new Scanner(System.in);
        String response = keyboard.next();
        if (response.equals("s")) {// computer goes first
            this.root.setTypeOfPieceHumanPlaysWithAsBlack();
            this.humanFinishedMove(null);
        } else if (response.equals("z")) {// human goes first
            this.root.setTypeOfPieceHumanPlaysWithAsWhite();
            this.computerFinishedMove();
        } else {
            System.out.println("not a valid option, bye bye.");

        }
    }
}

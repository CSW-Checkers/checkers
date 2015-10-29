package view;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.Board;
import model.PieceInterface;
import model.Square;
import controller.CheckersController;

public class CheckersPane extends Pane {

    Canvas canvas = new Canvas(700, 700);
    /*
     * the position of the image is the key, the image is the value
     */
    Hashtable<String, ImageView> positionsOfImages = new Hashtable<String, ImageView>();
    Hashtable<String, String> typeOfpieceAtPosition = new Hashtable<String, String>();

    int numberOfSquaresHorizontally = 8;
    double squareWidth = this.canvas.getWidth() / this.numberOfSquaresHorizontally;
    double squareHeight = this.canvas.getHeight() / this.numberOfSquaresHorizontally;

    String typeOfPieceHumanPlaysWith;

    String zerglingString = "zergling";
    String stalkerString = "stalker";

    public boolean computerIsMoving = true;

    /**
     * deals with the event listener (the controller) regarding requesting a move
     */
    List<CheckersController> listenersForHumanMove = new ArrayList<CheckersController>();

    List<CheckersController> listenersForComputerMove = new ArrayList<CheckersController>();

    /**
     * Instantiates the CheckersPane and does the following: -Draws the board -inits the Dictionary
     * of positions to ImageViews (Ivar: positionsOfImages) -adds the ImageViews to the CheckersPane
     * in the relevant positions
     */
    public CheckersPane() {
        super();
        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        this.getChildren().add(this.canvas);
        this.drawBoard(gc);
        this.initDictionary();
        this.positionPieces();
        Enumeration<String> keys = this.positionsOfImages.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            ImageView iv = this.positionsOfImages.get(key);
            this.getChildren().add(iv);
        }
    }

    /**
     * Note: zerglings are white, stalkers are black
     *
     * @param board
     */
    public CheckersPane(Board board) {
        super();
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        this.getChildren().add(this.canvas);
        this.drawBoard(gc);

        Image zerlingImage = new Image("zergling.jpeg");
        Image stalkerImage = new Image("stalker.jpeg");

        List<Square> squares = board.getGameState();
        for (Square square : squares) {
            if (square.isOccupied()) {
                PieceInterface piece = square.getOccupyingPiece();
                // I use row-1 because row in model is 1, same row in view is 0
                int row = square.getRowNumber() - 1;
                int col = square.getColumnNumber() - 1;
                String position = row + "," + col;

                ImageView iv;
                if (piece.isWhite()) {
                    iv = new ImageView(zerlingImage);
                    this.typeOfpieceAtPosition.put(position, this.zerglingString);
                } else {
                    iv = new ImageView(stalkerImage);
                    this.typeOfpieceAtPosition.put(position, this.stalkerString);
                }
                this.positionsOfImages.put(position, iv);
                iv.setFitWidth(this.squareWidth);
                iv.setFitHeight(this.squareHeight);
                iv.setOpacity(0.8);// SETS OPACITY!!!
                this.getChildren().add(iv);
            }
            this.positionPieces();
        }
    }

    public void addComputerFinishedMoveListener(CheckersController cont) {
        this.listenersForComputerMove.add(cont);
    }

    public void addHumanFinishedMoveListener(CheckersController cont) {
        this.listenersForHumanMove.add(cont);
    }

    /**
     * @param row
     * @param col
     * @return the Color of the square given a row and column
     */
    private Color colorOfSquare(int row, int col) {
        /*
         * if one of the two is even and the other is odd, then the square is red. Otherwise it is
         * black.
         */
        if (row % 2 == 0 && col % 2 == 1 || row % 2 == 1 && col % 2 == 0) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    private void drawBoard(GraphicsContext gc) {
        String[] positionsStrings = new String[] { "5", "13", "21", "29", "1", "9", "17", "25",
                "6", "14", "22", "30", "2", "10", "18", "26", "7", "15", "23", "31", "3", "11",
                "19", "27", "8", "16", "24", "32", "4", "12", "20", "28" };
        double squareWidth = this.canvas.getWidth() / this.numberOfSquaresHorizontally;
        double squareHeight = this.canvas.getHeight() / this.numberOfSquaresHorizontally;
        int squareNumber = 0;
        for (int currentRow = 0; currentRow < this.numberOfSquaresHorizontally; currentRow++) {
            for (int currentCol = 0; currentCol < this.numberOfSquaresHorizontally; currentCol++) {
                Color c = this.colorOfSquare(currentRow, currentCol);
                gc.setFill(c);
                double xOffset = currentRow * squareWidth;
                double yOffset = currentCol * squareHeight;
                gc.fillRect(xOffset, yOffset, squareWidth, squareHeight);
                // adding the numbers of the positions
                gc.setFill(Color.BLACK);
                if (currentRow % 2 == 0 && currentCol % 2 == 1 || currentRow % 2 == 1
                        && currentCol % 2 == 0) {
                    gc.fillText(String.valueOf(positionsStrings[squareNumber]), xOffset + 3.0,
                            yOffset + 12.0, 50.0);
                    squareNumber++;
                }
            }
        }
    }

    private void fireComputerHasMoved() {
        Iterator<CheckersController> it = this.listenersForHumanMove.iterator();
        while (it.hasNext()) {// but there will only be one listener
            CheckersController cont = it.next();
            cont.computerFinishedMove();
        }
    }

    private void fireHumanHasMoved() {
        Iterator<CheckersController> it = this.listenersForHumanMove.iterator();
        while (it.hasNext()) {// but there will only be one listener
            CheckersController cont = it.next();
            cont.humanFinishedMove();
        }
    }

    public int getColForX(double x) {
        int col = (int) x / (int) this.squareWidth;
        return col;
    }

    public int getRowForY(double y) {
        int row = (int) y / (int) this.squareHeight;
        return row;
    }

    private double getXPositionForColumn(int col) {
        return col * this.squareWidth;
    }

    private double getYPositionForRow(int row) {
        return row * this.squareHeight;
    }

    /**
     * Creates the dictionary "positionsOfImages", which contains the row/col position (key) of an
     * ImageView (value)
     */
    private void initDictionary() {
        String[] positionsOfZerglings = { "5,0", "5,2", "5,4", "5,6", "6,1", "6,3", "6,5", "6,7",
                "7,0", "7,2", "7,4", "7,6" };
        String[] positionsOfStalkers = { "0,1", "0,3", "0,5", "0,7", "1,0", "1,2", "1,4", "1,6",
                "2,1", "2,3", "2,5", "2,7" };
        /*
         * bottom pieces are zerglings
         */
        Image image = new Image("zergling.jpeg");
        for (int i = 0; i < positionsOfZerglings.length; i++) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(this.squareWidth);
            imageView.setFitHeight(this.squareHeight);
            this.positionsOfImages.put(positionsOfZerglings[i], imageView);
            this.typeOfpieceAtPosition.put(positionsOfZerglings[i], this.zerglingString);
        }

        /*
         * top pieces are stalkers
         */
        image = new Image("stalker.jpeg");
        for (int i = 0; i < positionsOfStalkers.length; i++) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(this.squareWidth);
            imageView.setFitHeight(this.squareHeight);
            this.positionsOfImages.put(positionsOfStalkers[i], imageView);
            this.typeOfpieceAtPosition.put(positionsOfStalkers[i], this.stalkerString);
        }
    }

    public void kingPieceAtPosition(String position) {
        System.out.println("kinged");
        String nameOfPiece = this.typeOfpieceAtPosition.get(position) == this.stalkerString ? this.stalkerString
                : this.zerglingString;
        ImageView imageToBeReplaced = this.positionsOfImages.get(position);
        Image image;
        image = (nameOfPiece == this.stalkerString) ? new Image("stalkerKing.jpg") : new Image(
                "zerglingKing.jpg");
        ImageView iv = new ImageView(image);
        this.positionsOfImages.put(position, iv);
        iv.setFitWidth(this.squareWidth);
        iv.setFitHeight(this.squareHeight);

        int row = Integer.parseInt(position.substring(0, 1));
        int col = Integer.parseInt(position.substring(2, 3));
        this.positionPieceGivenRowAndColumn(iv, row, col);

        this.getChildren().remove(imageToBeReplaced);
        this.getChildren().add(iv);
        /*
         * NOTE: EVENT FIRED TO THE CONTROLLER BELOW!! In here I signal the controller that the
         * animation of the computer's move has finished.
         */
        this.fireComputerHasMoved();// EVENT FIRED TO CONTROLLER
    }

    /**
     *
     * @param positions
     */
    public void movePieceToPositionsAndRemovePiecesAndKing(ArrayList<String> positions,
            ArrayList<String> toRemove, boolean isKingAtEndOfJump) {
        String initialPosition = positions.get(0);
        ImageView pieceToMove = this.positionsOfImages.get(initialPosition);
        String typeOfPiece = this.typeOfpieceAtPosition.get(initialPosition);

        String nextPosition = positions.get(1);
        int row = Integer.parseInt(nextPosition.substring(0, 1));
        int col = Integer.parseInt(nextPosition.substring(2, 3));

        this.positionsOfImages.remove(initialPosition);
        this.positionsOfImages.put(nextPosition, pieceToMove);
        this.typeOfpieceAtPosition.remove(initialPosition);
        if (typeOfPiece == this.stalkerString) {
            this.typeOfpieceAtPosition.put(nextPosition, this.stalkerString);
        } else {
            this.typeOfpieceAtPosition.put(nextPosition, this.zerglingString);
        }
        /*
         * moving animation
         */
        final Timeline timeline = new Timeline();
        double x = this.getXPositionForColumn(col);
        double y = this.getYPositionForRow(row);
        final KeyValue kv_x = new KeyValue(pieceToMove.xProperty(), x);
        final KeyValue kv_y = new KeyValue(pieceToMove.yProperty(), y);
        final KeyFrame kf_x = new KeyFrame(Duration.millis(500), kv_x);
        final KeyFrame kf_y = new KeyFrame(Duration.millis(500), kv_y);
        timeline.getKeyFrames().add(kf_x);
        timeline.getKeyFrames().add(kf_y);
        if (positions.size() > 2) {// if there is 1 more jump after this one...
            timeline.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    CheckersPane.this.movePieceToPositionsAndRemovePiecesAndKing(
                            new ArrayList<String>(positions.subList(1, positions.size())),
                            toRemove, isKingAtEndOfJump);
                }
            });
        } else {// if this is the last jump
            timeline.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    /*
                     * In here I signal the controller that the animation of the computer's move has
                     * finished.
                     */
                    if (isKingAtEndOfJump) {
                        String finalPosition = positions.get(positions.size() - 1);
                        CheckersPane.this.kingPieceAtPosition(finalPosition);
                    }

                    if (toRemove == null) {// if there are no pieces to remove
                        if (CheckersPane.this.computerIsMoving) {
                            CheckersPane.this.fireComputerHasMoved();
                        } else {
                            CheckersPane.this.fireHumanHasMoved();
                        }
                    } else {// if there are pieces to remove
                        CheckersPane.this.removePiecesAtPositions(toRemove);
                    }

                }
            });
        }
        timeline.play();
    }

    private void positionPieceGivenRowAndColumn(ImageView iv, int row, int col) {
        double x = col * this.squareWidth;
        double y = row * this.squareHeight;
        iv.setX(x);
        iv.setY(y);
    }

    /**
     * uses the position keys (row/col as String) of the images to correctly position the images in
     * the correct x, y position.
     */
    private void positionPieces() {
        Enumeration<String> keys = this.positionsOfImages.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            int row = Character.getNumericValue(key.charAt(0));
            int col = Character.getNumericValue(key.charAt(2));
            ImageView iv = this.positionsOfImages.get(key);
            this.positionPieceGivenRowAndColumn(iv, row, col);
        }
    }

    public void removePiecesAtPositions(ArrayList<String> positions) {
        for (String position : positions) {
            ImageView imageAtPosition = this.positionsOfImages.get(position);

            final Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(imageAtPosition.opacityProperty(), 0);
            KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    if (CheckersPane.this.computerIsMoving) {
                        CheckersPane.this.fireComputerHasMoved();
                    } else {
                        CheckersPane.this.fireHumanHasMoved();
                    }
                }
            });

            timeline.play();

            // removing pieces from those locations on the board
            // i.e. remove them from the positionsOfImages hashtable
            this.positionsOfImages.remove(position);
            this.typeOfpieceAtPosition.remove(position);

            this.getChildren().remove(imageAtPosition);
        }
    }

    public void setTypeOfPieceHumanPlaysWithAsBlack() {
        this.typeOfPieceHumanPlaysWith = "stalker";
    }

    public void setTypeOfPieceHumanPlaysWithAsWhite() {
        this.typeOfPieceHumanPlaysWith = "zergling";
    }

}
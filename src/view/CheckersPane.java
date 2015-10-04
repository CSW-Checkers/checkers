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
import controller.CheckersController;
import model.Board;
import model.Square;
import model.PieceInterface;

public class CheckersPane extends Pane{
	
	Canvas canvas = new Canvas(700, 700);
	/*
	 * the position of the image is the key, the image is the value
	 */
	Hashtable<String, ImageView> positionsOfImages = new Hashtable<String, ImageView>();
	Hashtable<String, String> typeOfpieceAtPosition = new Hashtable<String, String>();
	ImageView selectedImageView = null;
	String selectedKey = null;
	
	int numberOfSquaresHorizontally = 8;
	double squareWidth = canvas.getWidth() / numberOfSquaresHorizontally;
	double squareHeight = canvas.getHeight() / numberOfSquaresHorizontally;
	
	String zerglingString = "zergling";
	String stalkerString = "stalker";
	
	/**
	 * deals with the event listener (the controller) regarding requesting
	 * a move
	 */
	List<CheckersController> listenersForHumanMove = new ArrayList<CheckersController>();
	
	public void addHumanFinishedMoveListener(CheckersController cont){
	    listenersForHumanMove.add(cont);
	}
	
	private void fireHumanHasMoved(ArrayList<String> moves) {
	    Iterator<CheckersController> it = listenersForHumanMove.iterator();
	    while(it.hasNext()){//but there will only be one listener
	        CheckersController cont = it.next();
	        cont.humanFinishedMove(moves);
	    }
	}
	List<CheckersController> listenersForComputerMove = new ArrayList<CheckersController>();
	
	public void addComputerFinishedMoveListener(CheckersController cont){
	    listenersForComputerMove.add(cont);
	}
	
	private void fireComputerHasMoved(){
	    Iterator<CheckersController> it = listenersForHumanMove.iterator();
        while(it.hasNext()){//but there will only be one listener
            CheckersController cont = it.next();
            cont.computerFinishedMove();
        }
	}
	
	/**
	 * Code for seeing whether the clicks were on a image.
	 * If so, the Ivar selectedImageView is set to the ImageView
	 * that was pressed.  The selected ImageView is moved in the method 
	 * moveSelectedImageToCurrentXY(double x, double y)
	 * @param x : x coordinate of user's click
	 * @param y : y coordinate of user's click
	 */
	public void selectImage(double x, double y) {
		for (ImageView iv : positionsOfImages.values()){
			if(iv.isPressed()){
				selectedImageView = iv;
			}
		}
	}
	
	public boolean imageHasBeenSelected(){
		return selectedImageView != null;
	}
	/**
	 * Moves the selected image to the x y coords that the user specified
	 * The ImageView is only moved if it has been selected in 
	 * selectImage(double x, double y)
	 * @param x
	 * @param y
	 */
	public void moveSelectedImageToCurrentXY(double x, double y){
		if(selectedImageView != null){
			selectedImageView.setX(x - selectedImageView.getFitWidth()/2);
			selectedImageView.setY(y - selectedImageView.getFitHeight()/2);
		}
	}
	/**
	 * Places the selected piece in the x, y position specified.
	 * The piece will appear in the correct square corresponding
	 * to the x y position.  Requests moving permission from the controller
	 * NOTE: THIS IS FOR THE HUMAN MOVING THE PIECE. AN EVENT WILL BE FIRED
	 * SIGNALING THE CONTROLLER THAT THE HUMAN HAS MOVED.
	 * @param x
	 * @param y
	 */
	public void placeInSquareOfXY(double x, double y){
		int col = (int) x / (int)squareWidth;
		int row = (int) y / (int)squareHeight;
		
		final Timeline timeline = new Timeline();
        double xDestination = getXPositionForColumn(col);
        double yDestination = getYPositionForRow(row);
        final KeyValue kv_x = new KeyValue(selectedImageView.xProperty(), xDestination);
        final KeyValue kv_y = new KeyValue(selectedImageView.yProperty(), yDestination);
        final KeyFrame kf_x = new KeyFrame(Duration.millis(200), kv_x);
        final KeyFrame kf_y = new KeyFrame(Duration.millis(200), kv_y);
        timeline.getKeyFrames().add(kf_x);
        timeline.getKeyFrames().add(kf_y);
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                /*
                 * Notifying the controller that the human has made a move.
                 */
                ArrayList<String> moves = new ArrayList<String>();
                moves.add("1,2: I moved");
                fireHumanHasMoved(moves);
            }
        });
        timeline.play();
		
		String keyToDelete = null;
		for(String key : positionsOfImages.keySet()){
			if(positionsOfImages.get(key) == selectedImageView){
				keyToDelete = key;
				break;
			}
		}
		positionsOfImages.remove(keyToDelete);
		String positionAsString = row + "," + col;
		positionsOfImages.put(positionAsString, selectedImageView);
		String piece = typeOfpieceAtPosition.get(keyToDelete);
		typeOfpieceAtPosition.remove(keyToDelete);
		typeOfpieceAtPosition.put(positionAsString, piece);
		
		
	}
	
	
	Boolean pieceHasFinishedMoving = false;
	/**
	 * 
	 * @param positions
	 */
	public void movePieceToPositions(ArrayList<String> positions){
		String initialPosition = positions.get(0);
		ImageView pieceToMove = positionsOfImages.get(initialPosition);
		String typeOfPiece = typeOfpieceAtPosition.get(initialPosition);
		
		String nextPosition = positions.get(1);
		int row = Integer.parseInt(nextPosition.substring(0, 1));
		int col = Integer.parseInt(nextPosition.substring(2, 3));
		
		positionsOfImages.remove(initialPosition);
        positionsOfImages.put(nextPosition, pieceToMove);
        typeOfpieceAtPosition.remove(initialPosition);
        if(typeOfPiece == stalkerString)
            typeOfpieceAtPosition.put(nextPosition, stalkerString);
        else
            typeOfpieceAtPosition.put(nextPosition, zerglingString);
         /*
          * moving animation
          */
		final Timeline timeline = new Timeline();
		double x = getXPositionForColumn(col);
		double y = getYPositionForRow(row);
		final KeyValue kv_x = new KeyValue(pieceToMove.xProperty(), x);
		final KeyValue kv_y = new KeyValue(pieceToMove.yProperty(), y);
		final KeyFrame kf_x = new KeyFrame(Duration.millis(500), kv_x);
		final KeyFrame kf_y = new KeyFrame(Duration.millis(500), kv_y);
		timeline.getKeyFrames().add(kf_x);
		timeline.getKeyFrames().add(kf_y);
		if(positions.size() > 2){//if there is 1 more jump after this one...
		    //System.out.println("intermediate");
		    //System.out.println("size: " + positions.size());
		    timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                movePieceToPositions(new ArrayList<String>(positions.subList(1, positions.size())));
            }
		    });
		}
		else{//if this is the last jump
		    //System.out.println("final.");
		    //System.out.println("size: " + positions.size());
		    
		    timeline.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    /*
                     * In here I signal the controller that the animation
                     * of the computer's move has finished.
                     */
                    fireComputerHasMoved();//EVENT FIRED TO CONTROLLER
                }
            });
		}
		timeline.play();
	}
	
	
	public void movePieceToPositionAndKingIt(ArrayList<String> positions){
	    String initialPosition = positions.get(0);
        ImageView pieceToMove = positionsOfImages.get(initialPosition);
        String typeOfPiece = typeOfpieceAtPosition.get(initialPosition);
        
        String nextPosition = positions.get(1);
        int row = Integer.parseInt(nextPosition.substring(0, 1));
        int col = Integer.parseInt(nextPosition.substring(2, 3));
        
        positionsOfImages.remove(initialPosition);
        positionsOfImages.put(nextPosition, pieceToMove);
        typeOfpieceAtPosition.remove(initialPosition);
        if(typeOfPiece == stalkerString)
            typeOfpieceAtPosition.put(nextPosition, stalkerString);
        else
            typeOfpieceAtPosition.put(nextPosition, zerglingString);
         /*
          * moving animation
          */
        final Timeline timeline = new Timeline();
        double x = getXPositionForColumn(col);
        double y = getYPositionForRow(row);
        final KeyValue kv_x = new KeyValue(pieceToMove.xProperty(), x);
        final KeyValue kv_y = new KeyValue(pieceToMove.yProperty(), y);
        final KeyFrame kf_x = new KeyFrame(Duration.millis(500), kv_x);
        final KeyFrame kf_y = new KeyFrame(Duration.millis(500), kv_y);
        timeline.getKeyFrames().add(kf_x);
        timeline.getKeyFrames().add(kf_y);
        if(positions.size() > 2){//if there is 1 more jump after this one...
            timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                movePieceToPositionAndKingIt(new ArrayList<String>(positions.subList(1, positions.size())));
            }
            });
        }
        else{//if this is the last jump
            timeline.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    String finalPosition = positions.get(positions.size() - 1);
                    kingPieceAtPosition(finalPosition);
                }
            });
        }
        timeline.play();
	}
	
	public void removePiecesAtPositions(ArrayList<String> positions){
	    for(String position: positions){
	        ImageView imageAtPosition = positionsOfImages.get(position);
	        
	        final Timeline timeline = new Timeline();
	        KeyValue kv = new KeyValue(imageAtPosition.opacityProperty(), 0);
	        KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);
	        timeline.getKeyFrames().add(kf);
	        timeline.play();
	        
	        //removing pieces from those locations on the board
	        //i.e. remove them from the positionsOfImages hashtable
	        positionsOfImages.remove(position);
	        typeOfpieceAtPosition.remove(position);
	    }
	}
	
	public void kingPieceAtPosition(String position){
	    System.out.println("kinged");
	    String nameOfPiece = typeOfpieceAtPosition.get(position) == stalkerString ? stalkerString : zerglingString;
	    ImageView imageToBeReplaced = positionsOfImages.get(position);
	    Image image;
	    image = (nameOfPiece == stalkerString) ? new Image("stalkerKing.jpg") : new Image("zerglingKing.jpg");
	    ImageView iv = new ImageView(image);
	    positionsOfImages.put(position, iv);
	    iv.setFitWidth(squareWidth);
        iv.setFitHeight(squareHeight);
        
        int row = Integer.parseInt(position.substring(0, 1));
        int col = Integer.parseInt(position.substring(2, 3));
        positionPieceGivenRowAndColumn(iv, row, col);
        
        this.getChildren().remove(imageToBeReplaced);
        this.getChildren().add(iv);
        /* NOTE: EVENT FIRED TO THE CONTROLLER BELOW!!
         * In here I signal the controller that the animation
         * of the computer's move has finished.
         */
        fireComputerHasMoved();//EVENT FIRED TO CONTROLLER
	}
	
	/**
	 * Instantiates the CheckersPane and does the following:
	 * -Draws the board
	 * -inits the Dictionary of positions to ImageViews (Ivar: positionsOfImages)
	 * -adds the ImageViews to the CheckersPane in the relevant positions
	 */
	public CheckersPane(){
		super();
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		this.getChildren().add(canvas);
		drawBoard(gc);
		this.initDictionary();
		this.positionPieces();
		Enumeration<String> keys = positionsOfImages.keys();
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			ImageView iv = positionsOfImages.get(key);
			this.getChildren().add(iv);
		}
	}
	/**
	 * Note: zerglings are white, stalkers are black
	 * @param board
	 */
	public CheckersPane(Board board){
	    super();
	    GraphicsContext gc = canvas.getGraphicsContext2D();
	    this.getChildren().add(canvas);
	    drawBoard(gc);
	    
	    Image zerlingImage = new Image("zergling.jpeg");
        Image stalkerImage = new Image("stalker.jpeg");
	    
	    List<Square> squares = board.getGameState();
	    for(Square square : squares){
            if(square.isOccupied()){
                PieceInterface piece = square.getOccupyingPiece();
                //I use row-1 because row in model is 1, same row in view is 0
                int row = square.getRowNumber() - 1;
                int col = square.getColumnNumber() - 1;
                String position = row + "," + col;
                
                ImageView iv;
                if(piece.isWhite()){
                    iv = new ImageView(zerlingImage);
                    typeOfpieceAtPosition.put(position, zerglingString);
                }else{
                    iv = new ImageView(stalkerImage);
                    typeOfpieceAtPosition.put(position, stalkerString);
                }
                positionsOfImages.put(position, iv);
                iv.setFitWidth(squareWidth);
                iv.setFitHeight(squareHeight);
                this.getChildren().add(iv);
            }
        this.positionPieces();
        }
	}
	/**
	 * Creates the dictionary "positionsOfImages", which contains the 
	 * row/col position (key) of an ImageView (value) 
	 */
	private void initDictionary() {
		String[] positionsOfZerglings =
			{"5,0", "5,2", "5,4", "5,6",
				"6,1", "6,3","6,5", "6,7",
				"7,0", "7,2", "7,4", "7,6"};
		String[] positionsOfStalkers = 
			{"0,1", "0,3", "0,5", "0,7",
				"1,0", "1,2", "1,4", "1,6",
				"2,1", "2,3", "2,5", "2,7"};
		/*
		 * bottom pieces are zerglings
		 */
		Image image = new Image("zergling.jpeg");
		for(int i = 0; i < positionsOfZerglings.length; i++){
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(squareWidth);
			imageView.setFitHeight(squareHeight);
			positionsOfImages.put(positionsOfZerglings[i], imageView);
			typeOfpieceAtPosition.put(positionsOfZerglings[i], zerglingString);
		}
		
		/*
		 * top pieces are stalkers
		 */
		image = new Image("stalker.jpeg");
		for(int i = 0; i < positionsOfStalkers.length; i++){
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(squareWidth);
			imageView.setFitHeight(squareHeight);
			positionsOfImages.put(positionsOfStalkers[i], imageView);
			typeOfpieceAtPosition.put(positionsOfStalkers[i], stalkerString);
		}
	}
	/**
	 * uses the position keys (row/col as String) of the images to 
	 * correctly position the images in the correct x, y position.
	 */
	private void positionPieces() {
		Enumeration<String> keys = positionsOfImages.keys();
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			int row = Character.getNumericValue(key.charAt(0));
			int col = Character.getNumericValue(key.charAt(2));
			ImageView iv = positionsOfImages.get(key);
			positionPieceGivenRowAndColumn(iv, row, col);
		}
	}
	
	private void positionPieceGivenRowAndColumn(ImageView iv, int row, int col){
		double x = col * squareWidth;
		double y = row * squareHeight;
		iv.setX(x);
		iv.setY(y);
	}
	private double getXPositionForColumn(int col){
	    return col * squareWidth;
	}
	private double getYPositionForRow(int row){
	    return row * squareHeight;
	}
	
	private void drawBoard(GraphicsContext gc){
		double squareWidth = canvas.getWidth() / numberOfSquaresHorizontally;
		double squareHeight = canvas.getHeight() / numberOfSquaresHorizontally;
		
		for(int currentRow = 0; currentRow < numberOfSquaresHorizontally; currentRow++){
			for(int currentCol = 0; currentCol < numberOfSquaresHorizontally; currentCol++){
				Color c = colorOfSquare(currentRow, currentCol);
				gc.setFill(c);
				double xOffset = currentRow * squareWidth;
				double yOffset = currentCol * squareHeight;
				gc.fillRect(xOffset, yOffset, squareWidth, squareHeight);
			}
		}
	}
	/**
	 * @param row
	 * @param col
	 * @return the Color of the square given a row and column
	 */
	private Color colorOfSquare(int row, int col){
		/*
		 * if one of the two is even and the other is odd,
		 * then the square is red.  Otherwise it is black.
		 */
		if(row % 2 == 0 && col % 2 == 1 || row % 2 == 1 && col % 2 == 0){
			return Color.WHITE;
		}else{
			return Color.BLACK;
		}
	}
	
	public int getColForX(double x){
	    int col = (int) x / (int)squareWidth;
        return col;
	}
	public int getRowForY(double y){
        int row = (int) y / (int)squareHeight;
        return row;
    }
	
}
















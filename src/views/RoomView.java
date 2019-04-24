/** @author ITP368 course staff */
package views;

import controller.GameController;
import controller.MainController;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class RoomView {

	private MainController controller; // program's controller, used for changing scenes
	private GameController gc;
	private Scene gameScene; // scene to hold game

	private StackPane mainPane; // main pane for scene
	private int mainPaneHeight; // height of scene main pane
	private int mainPaneWidth; // width of scene main pane
	private ImageView background;
	
	private RoomPane roomPane; // game pane, contains game pane

	public RoomView(MainController controller) {
		this.controller = controller;
		this.gc = controller.getGameController();
		mainPaneHeight = controller.getMainPaneHeight();
		mainPaneWidth = controller.getMainPaneWidth();
		
		setupPanes();
		
		gameScene = new Scene(mainPane, mainPaneWidth, mainPaneHeight);
		gc.setupEvents(mainPane);
	}

	private void setupPanes() {
		mainPane = new StackPane();
		background = new ImageView(new Image("images/RoomBG.png"));
		background.setFitWidth(mainPaneWidth);
		background.setFitHeight(mainPaneHeight);
		background.setPreserveRatio(true);
		
		roomPane = new RoomPane(mainPaneWidth, mainPaneHeight, controller);

		mainPane.getChildren().addAll(background, roomPane);
	}
	
	// get scene object
	public Scene getScene() {
		return gameScene;
	}
	
	public RoomPane getPane() {
		return roomPane;
	}

	// method to tell game pane to start game
	public void startGame() {
		roomPane.startGame();
	}
	
	// method to tell game to stop game and reset game
	public void endGame() {
		roomPane.stopGame();
	}
}

package views;

import controller.GameController;
import controller.MainController;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class BackyardView {

	private MainController controller; // program's controller, used for changing scenes
	private GameController gc;
	private Scene gameScene; // scene to hold game

	private StackPane mainPane; // main pane for scene
	private int mainPaneHeight; // height of scene main pane
	private int mainPaneWidth; // width of scene main pane
	private ImageView background;
	
	private BackyardPane backyardPane; // game pane, contains game pane

	public BackyardView(MainController controller) {
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
		background = new ImageView(new Image("images/BackyardBG.png"));
		background.setFitWidth(mainPaneWidth);
		background.setFitHeight(mainPaneHeight);
		background.setPreserveRatio(true);
		
		backyardPane = new BackyardPane(mainPaneWidth, mainPaneHeight, controller);
		
		mainPane.getChildren().addAll(background, backyardPane);
	}
	
	// get scene object
	public Scene getScene() {
		return gameScene;
	}
	
	public BackyardPane getPane() {
		return backyardPane;
	}

	// method to tell game pane to start game
	public void startGame() {
		backyardPane.startGame();
	}
	
	// method to tell game to stop game and reset game
	public void endGame() {
		backyardPane.stopGame();
	}
}

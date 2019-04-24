/** @author ITP368 course staff */

/**
 * Instructions pane view component.
 * 
 * Customization: Add instructions to the game - make it appear before the ball starts moving.
 * 
 * @author Yunji Lee
 * ITP 368, Fall 2018
 * Assignment 10
 * yunjilee@usc.edu
 */

package views;

import controller.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class InstructionsView {

	private MainController controller;
	private Scene instructionsScene;

	private VBox mainPane;
	private int mainPaneHeight;
	private int mainPaneWidth;

	private Label storylineHeader; // label to display header
	private Label storyline;
	private Label instructionsHeader;
	private Label instructions; // label to display instructions
	private Button backButton; // back to main menu

	public InstructionsView(MainController controller) {
		this.controller = controller;
		mainPaneHeight = controller.getMainPaneHeight();
		mainPaneWidth = controller.getMainPaneWidth();
		
		mainPane = new VBox();
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setSpacing(15);
		mainPane.setStyle("-fx-background-color: " + controller.getRoomColor());
		setupPane();
		setupEvents();
		instructionsScene = new Scene(mainPane, mainPaneWidth, mainPaneHeight);
	}

	// set up pane components
	private void setupPane() {
		storylineHeader = new Label("INSTRUCTIONS");
		controller.setHeaderStyle(storylineHeader);
		
		storyline = new Label("You are an alien who was traveling across the galaxy "
				+ "when your spaceship crashed on a distant planet infested with slimes. "
				+ "You’re currently trapped in a dungeon room guarded by slimes outside. "
				+ "In order to escape, you must collect 4 keys, and the only way to do "
				+ "obtain keys is by feeding  stars to the cactus plant in the dungeon room. "
				+ "Stars can be found in the backyard, but you’ll need to avoid the slimes "
				+ "while you’re obtaining them as they can attack you. Every time the cactus "
				+ "plant becomes fully grown, you will obtain a key. If you can obtain all 4 "
				+ "without dying, you’ll be able to escape and win the game.");
		controller.setBodyStyle(storyline);
		storyline.setTextFill(Color.BLACK);
		
		instructionsHeader = new Label("PLAYER CONTROLS");
		controller.setHeaderStyle(instructionsHeader);
		instructionsHeader.setPadding(new Insets(10, 0, 0, 0)); // top, right, bottom, left
		
		instructions = new Label("UP/DOWN/LEFT/RIGHT : move\n" + 
				"SHIFT + UP/DOWN/LEFT/RIGHT : run\n" + 
				"SPACE : interact with objects\n\n"
				+ "TIP 1. Use SPACE to pick up stars, feed stars to cactus, and exit through doors.\n"
				+ "TIP 2. Choose HARD MODE to face even more slimes in the backyard.");
		controller.setBodyStyle(instructions);
		instructions.setTextFill(Color.BLACK);
		instructions.setPadding(new Insets(0, 0, 10, 0)); // top, right, bottom, left
		
		backButton = new Button("Back");
		controller.styleButton(backButton);
		mainPane.getChildren().addAll(storylineHeader, storyline, instructionsHeader, instructions, backButton);
	}
	
	// event handling inside scene
	public void setupEvents() {
		backButton.setOnAction(e -> {
			if(controller.getViewNum() == 0) controller.showStartScene();
			else if(controller.getViewNum() == 3) controller.showMenuScene();
		});
	}
	
	// get scene object
	public Scene getScene() {
		return instructionsScene;
	}
}

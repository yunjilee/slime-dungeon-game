package views;

import controller.MainController;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MenuView {

	private MainController controller;
	private Scene startScene;

	private StackPane mainPane;
	private BorderPane contentPaneBase;
	private VBox contentPane;
	private double mainPaneHeight;
	private double mainPaneWidth;

	private Button resumeButton;
	private Button instructionsButton;
	private Button resetButton;
	private Button exitButton;
	private ImageView background;

	public MenuView(MainController controller) {
		this.controller = controller;
		mainPaneHeight = controller.getMainPaneHeight();
		mainPaneWidth = controller.getMainPaneWidth();
		
		mainPane = new StackPane();
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setStyle("-fx-background-color: " + controller.getRoomColor());
		
		contentPaneBase = new BorderPane();

		contentPane = new VBox();
		contentPane.setSpacing(20);
		contentPane.setAlignment(Pos.CENTER);
		
		setupPane();
		setupEvents();
		startScene = new Scene(mainPane, mainPaneWidth, mainPaneHeight);
	}

	// set up pane components
	private void setupPane() {
		background = new ImageView(new Image("images/MenuSceneBG.png"));
		background.setFitWidth(mainPaneWidth);
		background.setFitHeight(mainPaneHeight);
		background.setPreserveRatio(true);
		
		// Start & Instructions VBox
		resumeButton = new Button("Resume Game");
		controller.styleButton(resumeButton);
		
		instructionsButton = new Button("Instructions");
		controller.styleButton(instructionsButton);
		
		resetButton = new Button("Reset Game");
		controller.styleButton(resetButton);
		
		exitButton = new Button("Exit");
		controller.styleButton(exitButton);
		
		contentPane.getChildren().addAll(instructionsButton, resumeButton, resetButton, exitButton);
		contentPaneBase.setCenter(contentPane);
		
		mainPane.getChildren().addAll(background, contentPaneBase);
	}
	
	// event handling inside scene
	public void setupEvents() {
		resumeButton.setOnAction(e -> {
			controller.showRoomScene();
		});
		instructionsButton.setOnAction(e -> {
			controller.showInstructionsScene();
		});
		resetButton.setOnAction(e -> {
			controller.resetGame();
		});
		exitButton.setOnAction(e -> {
			Platform.exit();
		});
	}
	
	// get scene object
	public Scene getScene() {
		return startScene;
	}
}

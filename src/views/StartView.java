/** @author ITP368 course staff */
package views;

import controller.MainController;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class StartView {

	private MainController controller;
	private Scene startScene;

	private StackPane mainPane;
	private AnchorPane contentPaneAnchor;
	private VBox contentPane;
	private double mainPaneHeight;
	private double mainPaneWidth;

	private Button startButton;
	private Button instructionsButton;
	private Button exitButton;
	private ImageView background;

	public StartView(MainController controller) {
		this.controller = controller;
		mainPaneHeight = controller.getMainPaneHeight();
		mainPaneWidth = controller.getMainPaneWidth();
		
		mainPane = new StackPane();
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setStyle("-fx-background-color: " + controller.getRoomColor());
		
		contentPaneAnchor = new AnchorPane();

		contentPane = new VBox();
		contentPane.setSpacing(15);
		contentPane.setAlignment(Pos.CENTER);
		
		setupPane();
		setupEvents();
		startScene = new Scene(mainPane, mainPaneWidth, mainPaneHeight);
	}

	// set up pane components
	private void setupPane() {
		background = new ImageView(new Image("images/StartSceneBG.png"));
		background.setFitWidth(mainPaneWidth);
		background.setFitHeight(mainPaneHeight);
		background.setPreserveRatio(true);
		
		// Start & Instructions VBox
		startButton = new Button("Start Game");
		controller.styleButton(startButton);
		instructionsButton = new Button("Instructions");
		controller.styleButton(instructionsButton);
		contentPane.getChildren().addAll(startButton, instructionsButton);
		AnchorPane.setTopAnchor(contentPane, mainPaneHeight/2.0 + 50);
		AnchorPane.setLeftAnchor(contentPane, mainPaneWidth/2.0 - startButton.getPrefWidth()/2);
		
		// Exit button
		ImageView exitButtonImg = new ImageView(new Image("images/ExitSign.png"));
		exitButtonImg.setFitHeight(85);
		exitButtonImg.setPreserveRatio(true);
		exitButton = new Button();
		controller.styleButtonHover(exitButton);
		exitButton.setGraphic(exitButtonImg);
		exitButton.setStyle("-fx-background-color: transparent;");
		AnchorPane.setBottomAnchor(exitButton, 10.0);
		AnchorPane.setRightAnchor(exitButton, 50.0);
		
		// Slime image
		ImageView slimeImg = new ImageView(new Image("images/Slime.png"));
		slimeImg.setFitHeight(40);
		slimeImg.setPreserveRatio(true);
		AnchorPane.setBottomAnchor(slimeImg, 15.0);
		AnchorPane.setRightAnchor(slimeImg, 10.0);
		
		contentPaneAnchor.getChildren().addAll(contentPane, exitButton, slimeImg);
		
		mainPane.getChildren().addAll(background, contentPaneAnchor);
	}
	
	// event handling inside scene
	public void setupEvents() {
		startButton.setOnAction(e -> {
			controller.showModeScene();
		});
		instructionsButton.setOnAction(e -> {
			controller.showInstructionsScene();
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

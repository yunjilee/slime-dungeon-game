package views;

import controller.MainController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ModeView {

	private MainController controller;
	private Scene startScene;

	private StackPane mainPane;
	private BorderPane contentPaneBase;
	private VBox contentPane;
	private double mainPaneHeight;
	private double mainPaneWidth;

	private Label label;
	private Button easyMode;
	private Button hardMode;
	private ImageView background;

	public ModeView(MainController controller) {
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
		
		label = new Label("Select a mode:");
		controller.setHeaderStyle(label);
		
		// Start & Instructions VBox
		easyMode = new Button("Easy Mode");
		controller.styleButton(easyMode);
		
		hardMode = new Button("Hard Mode");
		controller.styleButton(hardMode);
		
		contentPane.getChildren().addAll(label, easyMode, hardMode);
		contentPaneBase.setCenter(contentPane);
		
		mainPane.getChildren().addAll(background, contentPaneBase);
	}
	
	// event handling inside scene
	public void setupEvents() {
		easyMode.setOnAction(e -> {
			controller.getGameController().setHardMode(false);
			controller.showRoomScene();
		});
		hardMode.setOnAction(e -> {
			controller.getGameController().setHardMode(true);
			controller.showRoomScene();
		});
	}
	
	// get scene object
	public Scene getScene() {
		return startScene;
	}
}

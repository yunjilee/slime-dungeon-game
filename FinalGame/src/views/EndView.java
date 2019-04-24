/** @author ITP368 course staff */
package views;

import controller.MainController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class EndView {
	
	private static final String LOCAL_URL = "/video/ConfettiVideo.mp4";
	
	private MediaPlayer mediaPlayer;
	private MediaView mediaView;

	private MainController controller;
	private Scene endScene;

	private StackPane basePane;
	private VBox mainPane;
	private int mainPaneHeight;
	private int mainPaneWidth;

	private Label winnerLabel; // label to display winner
	private Button restartButton;

	public EndView(MainController controller) {
		this.controller = controller;
		mainPaneHeight = controller.getMainPaneHeight();
		mainPaneWidth = controller.getMainPaneWidth();
		
		mainPane = new VBox();
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setSpacing(30);
		setupPane();
		setupEvents();
		
		try{
			Media media = new Media(getClass().getResource(LOCAL_URL).toString());
			mediaPlayer = new MediaPlayer(media);
			mediaView = new MediaView(mediaPlayer);
		}
		catch(Exception e){
			System.err.println("Error getting media file: " + e);
			System.exit(0);
		}
		
		basePane = new StackPane();
		basePane.setAlignment(Pos.CENTER);
		basePane.setStyle("-fx-background-color: #000000");
		basePane.getChildren().addAll(mediaView, mainPane);
		
		endScene = new Scene(basePane, mainPaneWidth, mainPaneHeight);
	}

	// set up pane components
	private void setupPane() {
		winnerLabel = new Label("NO WINNER RIGHT NOW!");
		winnerLabel.setTextAlignment(TextAlignment.CENTER);
		winnerLabel.setWrapText(true);
		winnerLabel.setFont(Font.font(controller.getTitleFont(), 40));
		winnerLabel.setTextFill(Color.LIGHTCYAN);
		winnerLabel.setAlignment(Pos.CENTER);
		restartButton = new Button("Restart Game");
		controller.styleButton(restartButton);
		mainPane.getChildren().addAll(winnerLabel, restartButton);
	}
	
	// event handling inside scene
	public void setupEvents() {
		restartButton.setOnAction(e -> {
			controller.resetGame();
			controller.showStartScene();
		});
	}
	
	// set winner label text
	public void setWinLabelText(String win) {
		winnerLabel.setText(win);
	}
	
	// get scene object
	public Scene getScene() {
		return endScene;
	}
	
	// set screen based on whether player won or lost
	public void setPlayerWin(boolean win) {
		if(win) {
			setWinLabelText("You win!");
			mediaPlayer.play();
		}
		else {
			setWinLabelText("You lose!");
			mainPane.setStyle("-fx-background-color: " + controller.getRoomColor());
		}
	}
}

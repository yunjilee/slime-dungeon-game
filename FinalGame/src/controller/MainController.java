package controller;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import views.BackyardView;
import views.EndView;
import views.InstructionsView;
import views.MenuView;
import views.ModeView;
import views.RoomView;
import views.StartView;

public class MainController extends Application {
	
	private static final String MEDIA_URL = "/audio/GameMusic.mp3";
	private MediaPlayer mediaPlayer;
	
	private GameController gc;
	
	private Stage primaryStage; // window stage object
	private StartView startView; // object to hold everything in the starting scene
	private ModeView modeView; // displays modes
	private MenuView menuView; 
	private InstructionsView instructionsView; // object to hold everything in the starting scene
	private RoomView roomView;
	private BackyardView backyardView;
	private EndView endView; // object to hold everything in the ending scene
	
	private String titleFont = "Proxima Nova";
	private String highlightColor = "#cfdada";
	
	private String roomColor = "#869595"; // string for dark backgroundColor
	private String backyardColor = "#80be1f"; // string for light backgroundColor
	private String defaultButtonColor = "#c48e5a"; // color for default buttonColor
	
	private int mainPaneHeight = 800;
	private int mainPaneWidth = 800;

	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		try {
			Media media = new Media(getClass().getResource(MEDIA_URL).toString());
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setOnEndOfMedia(new Runnable() { // repeat infinitely
				public void run() {
					mediaPlayer.seek(Duration.ZERO);
				}
			});
			mediaPlayer.play();
		} catch(Exception e){
			System.err.println("Error getting media file: " + e);
			System.exit(0);
		}
		
		setUpGame();
		primaryStage.show();
	}
	
	public void setUpGame() {
		gc = GameController.getInstance(this);
		setupScenes();
		
		gc.setRoom(roomView.getPane());
		gc.setBackyard(backyardView.getPane());
		gc.setUpTimer();
		
		showStartScene();
	}
	
	// return the window's stage
	public Stage getStage(){
		return primaryStage;
	}
	
	// set up all scenes for the application
	public void setupScenes() {
		startView = new StartView(this);
		modeView = new ModeView(this);
		menuView = new MenuView(this);
		roomView = new RoomView(this);
		backyardView = new BackyardView(this);
		endView = new EndView(this);
		instructionsView = new InstructionsView(this);
	}
	
	// display the starting scene
	public void showStartScene() {
		gc.setViewNum(0);
		primaryStage.setScene(startView.getScene());
	}
	
	// display the starting scene
	public void showModeScene() {
		gc.setViewNum(0);
		primaryStage.setScene(modeView.getScene());
	}
	
	// display the menu scene
	public void showMenuScene() {
		gc.setViewNum(3);
		primaryStage.setScene(menuView.getScene());
	}
	
	// display the instructions scene
	public void showInstructionsScene() {
		primaryStage.setScene(instructionsView.getScene());
	}
	
	// display the room scene
	public void showRoomScene() {
		backyardView.endGame();
		gc.setViewNum(1);
		primaryStage.setScene(roomView.getScene());
		roomView.startGame();
	}
	
	// display the backyard scene
	public void showBackyardScene() {
		roomView.endGame();
		gc.setViewNum(2);
		primaryStage.setScene(backyardView.getScene());
		backyardView.startGame();
		backyardView.getPane().resetPane();
	}
	
	// display the end scene
	public void showEndScene(boolean playerWin){
		roomView.endGame();
		backyardView.endGame();
		
		endView.setPlayerWin(playerWin);
		primaryStage.setScene(endView.getScene());
	}
	
	// get font for application
	public String getTitleFont() {
		return titleFont;
	}
	
	// style button for application
	public void styleButton(Button button) {
		button.setFont(Font.font(getTitleFont(), 25));
		button.setStyle("-fx-background-color: " + defaultButtonColor);
		button.setPrefWidth(450);
		button.setOnMouseEntered(me -> {
			button.setStyle("-fx-background-color: " + highlightColor);
			button.setEffect(new DropShadow(40, Color.web(highlightColor))); // shadow around button
		});
		button.setOnMouseExited(me -> {
			button.setStyle("-fx-background-color: " + defaultButtonColor);
			button.setEffect(null);
		});
	}
	
	// style hoverable button (for image buttons)
	public void styleButtonHover(Button button) {
		button.setOnMouseEntered(me -> {
			button.setEffect(new DropShadow(40, Color.web(highlightColor))); // shadow around button
		});
		button.setOnMouseExited(me -> {
			button.setEffect(null);
		});
	}
	
	// style header text
	public void setHeaderStyle(Label header) {
		header.setTextAlignment(TextAlignment.CENTER);
		header.setWrapText(true);
		header.setFont(Font.font(titleFont, 40));
		header.setTextFill(Color.web(highlightColor));
		header.setAlignment(Pos.CENTER);
	}
	
	// style body text
	public void setBodyStyle(Label body) {
		body.setTextAlignment(TextAlignment.CENTER);
		body.setPrefWidth(mainPaneWidth - 100);
		body.setWrapText(true);
		body.setFont(Font.font(titleFont, 18));
		body.setTextFill(Color.web(highlightColor));
		body.setAlignment(Pos.CENTER);
		body.setStyle("-fx-line-spacing: 0.3em;");
	}
	
	// get pane width
	public int getMainPaneWidth() {
		return mainPaneWidth;
	}
	
	// get pane height
	public int getMainPaneHeight() {
		return mainPaneHeight;
	}
	
	// get room color
	public String getRoomColor() {
		return roomColor;
	}
	
	// get backyard color
	public String getBackyardColor() {
		return backyardColor;
	}
	
	// get game controller
	public GameController getGameController() {
		return gc;
	}
	
	// reset entire game
	public void resetGame() {
		gc.resetGame();
		this.setUpGame();
	}
	
	// get view num
	public int getViewNum() {
		return gc.getViewNum();
	}
	
	public String getDefaultButtonColor() {
		return defaultButtonColor;
	}
	
	public String getHighlightColor() {
		return highlightColor;
	}
 }

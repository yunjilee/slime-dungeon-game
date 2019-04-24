package views;

import controller.GameController;
import controller.MainController;
import javafx.scene.layout.Pane;
import model.HealthBar;
import model.Keys;
import model.Player;
import model.StarsLabel;

public class GamePaneBase extends Pane {
	protected MainController mc; // program's controller, used for changing scenes
	protected GameController gc;
	
	protected StarsLabel starsLabel;
	protected Keys keys;
	protected HealthBar healthBar;

	protected int height; // height of game board
	protected int width; // width of game board
	
	protected Player player;
	
	public GamePaneBase(int mainPaneWidth, int mainPaneHeight, MainController mc) {
		this.mc = mc;
		this.gc = mc.getGameController();
		
		height = mainPaneHeight;
		width = mainPaneWidth;
		this.setMinSize(width, height);
		this.setMaxSize(width, height);
		
		starsLabel = gc.getStarsLabel();
		keys = gc.getKeys();
		healthBar = gc.getHealthBar();
	}
	
	// set up pieces inside game board
	private void setupBasePieces() {
		player = gc.getPlayer(); // get player object
		
		// add to pane if not already added
		if(!this.getChildren().contains(player)) {
			this.getChildren().addAll(player);
		}
		
		// stars label
		starsLabel.setLayoutX(width - 90);
		starsLabel.setLayoutY(20);
		if(!this.getChildren().contains(starsLabel)) this.getChildren().add(starsLabel);
		
		// keys hbox
		int keysWidth = keys.getKeyWidth() * 4;
		int keysHeight = keys.getKeyHeight();
		keys.setLayoutX(width - keysWidth - 35);
		keys.setLayoutY(height - keysHeight - 15);
		if(!this.getChildren().contains(keys)) this.getChildren().add(keys);
		
		// health label
		int barHeight = healthBar.getHeartHeight();
		healthBar.setLayoutX(15);
		healthBar.setLayoutY(height - barHeight - 15);
		if(!this.getChildren().contains(healthBar)) this.getChildren().add(healthBar);
	}

	public void startGame() {
		gc.startTimer();
		setupBasePieces();
		player.toFront(); // keep player on top of other nodes
	}
	
	public void stopGame() {
		gc.stopTimer();
	}
}

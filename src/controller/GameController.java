package controller;

import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Cactus;
import model.Facing;
import model.HealthBar;
import model.Keys;
import model.Player;
import model.StarsLabel;
import views.BackyardPane;
import views.RoomPane;

public class GameController {
	
	private static GameController gcSingleton;
	
	private MainController mc;
	private GameTimer timer;
	
	private Player player;
	private Cactus cactus;
	private StarsLabel starsLabel;
	private Keys keys;
	private HealthBar healthBar;
	
	private int paneHeight;
	private int paneWidth;
	
	// Controls player movement
	public boolean goNorth, goSouth, goEast, goWest;
	public boolean running, interact;
	public boolean keyPressed;
	
	private int viewNum = 0; // 0: non-game 1: room 2: backyard
	private RoomPane room;
	private BackyardPane backyard;
	
	private boolean hardMode = false;
	
	private GameController(MainController mc) {
		this.mc = mc;
		paneHeight = mc.getMainPaneHeight();
		paneWidth = mc.getMainPaneWidth();
		
		player = new Player(paneWidth, paneHeight);
		player.moveTo(paneWidth / 2, paneHeight / 2);
		
		cactus = new Cactus();
		player.setCactus(cactus);
		
		starsLabel = new StarsLabel();
		player.setStarsLabel(starsLabel);
		
		keys = new Keys();
		player.setKeys(keys);
		
		healthBar = new HealthBar(player.getHealth());
		player.setHealthBar(healthBar);
		
		resetGC();
	}
	
	/* Reset game */
	public void resetGame() {
		player.reset();
		cactus.reset();
		starsLabel.reset();
		keys.reset();
		healthBar.reset();
		keyPressed = running = interact = false;
		goNorth = goSouth = goWest = goEast = false;
		stopTimer();
	}
	
	/* Reset game controller */
	public void resetGC() {
		running = false;
		goNorth = false;
		goSouth = false;
		goEast = false;
		goWest = false;
		interact = false;
	}
	
	/* GC uses Singleton Design Pattern */
	public static GameController getInstance(MainController mc) {
        if(gcSingleton == null){
        	gcSingleton = new GameController(mc);
        }
        return gcSingleton;
    }
	
	/* Setup methods */
	public void setRoom(RoomPane room) {
		this.room = room;
	}
	
	public void setBackyard(BackyardPane backyard) {
		this.backyard = backyard;
	}
	
	public void setUpTimer() {
		timer = new GameTimer(this, room, backyard);
	}

	/* Start/stop game timer */
	public void startTimer() {
		timer.start();
	}
	
	public void stopTimer() {
		timer.stop();
	}
	
	/* Return true if collision */
	public boolean playerCollidesWith(ImageView i) {
		if(i.getBoundsInParent().intersects(player.getBoundsInParent())) return true;
		return false;
	}
	
	// Set up event handling for scene
	public void setupEvents(Pane pane) {
		
		pane.setOnKeyPressed(ke -> {
			KeyCode code = ke.getCode();
			switch(code) {
	            case UP:    
	            	goNorth = true; 
	            	keyPressed = true;
	            	getPlayer().setFacing(Facing.UP);
	            	break;
	            case DOWN:  
	            	goSouth = true; 
	            	keyPressed = true;
	            	getPlayer().setFacing(Facing.DOWN);
	            	break;
	            case LEFT:  
	            	goWest  = true; 
	            	keyPressed = true;
	            	getPlayer().setFacing(Facing.LEFT);
	            	break;
	            case RIGHT: 
	            	goEast  = true; 
	            	keyPressed = true;
	            	getPlayer().setFacing(Facing.RIGHT);
	            	break;
	            case SHIFT: running = true; break;
	            case SPACE: interact = true; break;
	            default: break;
	        }
		});
		
		pane.setOnKeyReleased(ke -> {
			KeyCode code = ke.getCode();
			switch(code) {
	            case UP:    goNorth = false; keyPressed = false; break;
	            case DOWN:  goSouth = false; keyPressed = false; break;
	            case LEFT:  goWest  = false; keyPressed = false; break;
	            case RIGHT: goEast  = false; keyPressed = false; break;
	            case SHIFT: running = false; break;
	            default: break;
	        }
		});
		
		pane.requestFocus();
	}
	
	// Feed cactus -> use stars, obtain keys
	public void feedCactus() {
		if(player.getNumStars() > 0) {
			player.removeStar();
			boolean getKey = cactus.upgradeLevel();
			
			ImageView iv = null;

			if(getKey) {
				player.addKey();
				keys.fillKey(player.getNumKeys());
				
				int keys = player.getNumKeys();
				
				switch(keys) {
				case 1: iv = new ImageView(new Image("images/RKey.png")); break;
				case 2: iv = new ImageView(new Image("images/BKey.png")); break;
				case 3: iv = new ImageView(new Image("images/YKey.png")); break;
				case 4: iv = new ImageView(new Image("images/GKey.png")); break;
					default: 
				}
				
				cactusFeedTransition(iv);
			} else {
				iv = new ImageView(new Image("images/Star.png"));
				cactusFeedTransition(iv);
			}
		}
		
		// If player obtains 4 keys, they win!
		if(player.getNumKeys() == 4) {
			endGame(true);
		}
	}
	
	/* Fade transition animation */
	public void cactusFeedTransition(ImageView iv) {
		iv.setFitHeight(30);
		iv.setPreserveRatio(true);
		
		double cactusX = cactus.getLayoutX() + (cactus.getFitWidth() / 2);
		double cactusY = cactus.getLayoutY();
		
		iv.setX(cactusX);
		iv.setY(cactusY);
		
		FadeTransition ft = new FadeTransition();
		ft.setDuration(Duration.seconds(1.0));
		ft.setNode(iv);
		ft.setToValue(0);
		ft.play();
		
		room.getChildren().add(iv);
	}
	
	/* Show end scene on win/lose */
	public void endGame(boolean playerWin) {
		mc.showEndScene(playerWin);
	}
	
	/* Getters & setters */
	public boolean getKeyPressed() {
		return keyPressed;
	}
	
	public void setHardMode(boolean b) {
		hardMode = b;
		backyard.setNumMinions();
	}
	
	public boolean getHardMode() {
		return hardMode;
	}
	
	public void setViewNum(int i) {
		viewNum = i;
	}
	
	public int getViewNum() {
		return viewNum;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Cactus getCactus() {
		return cactus;
	}
	
	public StarsLabel getStarsLabel() {
		return starsLabel;
	}
	
	public Keys getKeys() {
		return keys;
	}
	
	public HealthBar getHealthBar() {
		return healthBar;
	}
}

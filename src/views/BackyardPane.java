package views;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import controller.MainController;
import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Facing;
import model.Minion;

public class BackyardPane extends GamePaneBase {
	
	private ImageView roomExit;
	private static int doorWidth = 100;
	
	private ImageView exitSign;
	
	private ArrayList<Minion> minions;
	private int numMinions = 10;
	private int minNumMinions = 10;
	
	private ArrayList<ImageView> stars;
	private int numStars = 5;
	private int starWidth = 28;
	
	private boolean collision;
	
	public BackyardPane(int mainPaneWidth, int mainPaneHeight, MainController controller) {
		super(mainPaneWidth, mainPaneHeight, controller);
		setupBackyardPieces();
	}

	// Set up pieces inside game board
	private void setupBackyardPieces() {
		setNumMinions();
		
		minions = new ArrayList<>();
		stars = new ArrayList<>();
		resetPane(); // initialize minions & stars
		
		roomExit = new ImageView(new Image("images/RoomDoor.png"));
		roomExit.setFitWidth(doorWidth);
		roomExit.setPreserveRatio(true);
		roomExit.setLayoutX(width / 2 - doorWidth / 2);
		roomExit.setLayoutY(height - roomExit.getBoundsInLocal().getHeight() - 15);
		
		exitSign = new ImageView(new Image("images/ExitSign.png"));
		exitSign.setFitWidth(50);
		exitSign.setPreserveRatio(true);
		exitSign.setLayoutX(width / 2 - doorWidth / 2 - exitSign.getFitWidth() - 15);
		exitSign.setLayoutY(height - exitSign.getBoundsInLocal().getHeight() - 15);
		
		this.getChildren().addAll(roomExit, exitSign);
	}
	
	public ArrayList<Minion> getMinions() {
		return minions;
	}
	
	public void setNumMinions() {
		if(gc.getHardMode()) minNumMinions = numMinions = 15;
		else minNumMinions = numMinions = 10;
	}
	
	public boolean moveMinions(boolean doDamage) {
		collision = false;
		Iterator<Minion> it = minions.iterator();
		while(it.hasNext()) { // use an iterator to avoid concurrent modification
			Minion m = it.next();
			boolean isDead = moveMinion(m, doDamage);
			if(isDead) it.remove(); // thread safe
		}
		
		return collision;
	}
	
	public boolean moveMinion(Minion m, boolean doDamage) {
		boolean isDead = false;
		
		// Move minion
		m.move();
		
		// Figure out width/height of the pane
		double width = mc.getMainPaneWidth();
		
		// Figure out the left, right, top, & bottom minion coordinates
		double left = m.getLayoutX();
		double right = m.getLayoutX() + m.getWidth();
		
		// if touching right or left, change x direction
		if(left <= 0 || right >= width) {
			m.changeXDirection();
		}
		
		// if collides with player & cooldown done, player gets damage
		if(gc.playerCollidesWith(m) && doDamage) {
			this.addCollision(gc.getPlayer().getCenterX(), gc.getPlayer().getCenterY(), 
					m.getCenterX(), m.getCenterY());
			gc.getPlayer().getDamage(); // Update player health
			gc.getHealthBar().update(gc.getPlayer().getHealth()); // Update health bar
			collision = true;
		}
		
		// if health <= 0, remove minion
		if(m.getHealth() <= 0) isDead = true;
		
		return isDead;
	}
	
	private void addCollision(int playerX, int playerY, int minionX, int minionY) {
		ImageView collision = new ImageView(new Image("images/SlimeAttack.png"));
		collision.setFitHeight(60);
		collision.setPreserveRatio(true);
		
		int collisionX = playerX - (playerX - minionX);
		int collisionY = playerY - (playerY - minionY);
		
		collision.setX(collisionX - collision.getFitHeight() / 2);
		collision.setY(collisionY - (int)collision.getBoundsInParent().getHeight() / 2);

		FadeTransition ft = new FadeTransition();
		ft.setDuration(Duration.seconds(0.5));
		ft.setNode(collision);
		ft.setToValue(0);
		ft.play();
		
		this.getChildren().add(collision);
	}
	
	/* Interactable object collisions */
	public void interactCollisions() {
		// to simulate walking in the door
		int roomExitCenterY = (int)(roomExit.getY() + roomExit.getBoundsInLocal().getHeight() / 2);
		
		// room exit
		if(gc.playerCollidesWith(roomExit) && player.getLayoutY() >= roomExitCenterY) {
			
			if(gc.interact) {
				player.moveTo(width / 2 + doorWidth / 2 - 15, 30); // set player position to top center
				mc.showRoomScene();
			}
		}
		
		// stars
		Iterator<ImageView> it = stars.iterator();
		while(it.hasNext()) { // use an iterator to avoid concurrent modification
			ImageView star = it.next();
			if(gc.playerCollidesWith(star) && gc.interact) {
				it.remove();
				this.getChildren().remove(star);
				player.addStar();
			}
		}
	}
	
	/* Non-interactable object collisions */
	public boolean playerCollision() {
		boolean collision = false;
		
		// chained exit
		if(gc.playerCollidesWith(exitSign)) {
			collision = true;
		}
		
		return collision;
	}

	public void resetPane() {
		this.getChildren().removeAll(minions);
		this.getChildren().removeAll(stars);
		
		minions.clear();
		stars.clear();
		
		// initialize minions to random positions, spaced vertically
		if(gc.getPlayer().getNumKeys() == 0) numMinions = minNumMinions;
		else if(gc.getPlayer().getNumKeys() == 1) numMinions = minNumMinions + 3;
		else if(gc.getPlayer().getNumKeys() == 2) numMinions = minNumMinions + 6;
		else if(gc.getPlayer().getNumKeys() == 3) numMinions = minNumMinions + 9;
		else throw new RuntimeException("Bad number of keys.");
		
		int yDist = height / numMinions;
		int numFacingLeft = numMinions / 2;
		for(int i = 0; i < numMinions; i++) {
			Minion m;
			if(i < numFacingLeft) m = new Minion(width, height, Facing.LEFT);	
			else m = new Minion(width, height, Facing.RIGHT);
			minions.add(m);
			
			Random r = new Random();
			int x = r.nextInt(width - m.getWidth());
			int y = i * yDist;
			m.moveTo(x, y);	
		}
		
		// add stars to random positions, spaced vertically
		yDist = height / numStars;
		for(int i = 0; i < numStars; i++) {
			ImageView s = new ImageView(new Image("images/Star.png"));
			s.setFitWidth(starWidth);
			s.setPreserveRatio(true);
			stars.add(s);
			
			Random r = new Random();
			int x = r.nextInt(width - starWidth);
			int y = i * yDist;
			s.relocate(x, y);	
		}
		
		this.getChildren().addAll(minions);
		this.getChildren().addAll(stars);
	}
}
package model;

import javafx.scene.image.Image;

public class Player extends Entity {
	private int numStars;
	private int numKeys;
	
	// from game controller
	private Cactus cactus;
	private StarsLabel starsLabel;
	private Keys keys;
	private HealthBar healthBar;
	
	// to control walking frame animation
	private int imgNum; // 0, 1, 2
	private Image left1, right1, up1, down1;
	private Image left2, right2, up2, down2;

	public Player(int paneWidth, int paneHeight) {
		super(new Image("images/PlayerDown.png"), 100, 71, 8, paneWidth, paneHeight); // set default to face down, 8 health
		
		numStars = 0;
		numKeys = 0;
		imgNum = 0;
		
		setLeftImage(new Image("images/PlayerLeft.png"));
		setRightImage(new Image("images/PlayerRight.png"));
		setUpImage(new Image("images/PlayerUp.png"));
		setDownImage(new Image("images/PlayerDown.png"));
		
		left1 = new Image("images/PlayerLeft1.png");
		right1 = new Image("images/PlayerRight1.png");
		up1 = new Image("images/PlayerUp1.png");
		down1 = new Image("images/PlayerDown1.png");
		
		left2 = new Image("images/PlayerLeft2.png");
		right2 = new Image("images/PlayerRight2.png");
		up2 = new Image("images/PlayerUp2.png");
		down2 = new Image("images/PlayerDown2.png");
	}
	
	@Override
	public void resetEntity() {
		numStars = 0;
		numKeys = 0;
		imgNum = 0;
		facing = Facing.DOWN;
		this.setImage(down);
	}
	
	@Override
	public void setFacing(Facing f) {
		this.facing = f;
	}
	
	/* Frame based walking animation */
	public void updateWalking() {
		if(this.imgNum == 0) {
			setFacing0(this.facing);
			imgNum = 1;
		}
		else if(this.imgNum == 1) {
			setFacing1(this.facing);
			imgNum = 2;
		}
		else if(this.imgNum == 2) {
			setFacing2(this.facing);
			imgNum = 0;
		}
	}
	
	public void setFacing0(Facing f) {
		this.facing = f;
		if(f == Facing.LEFT) this.setImage(left);
		if(f == Facing.RIGHT) this.setImage(right);
		if(f == Facing.UP) this.setImage(up);
		if(f == Facing.DOWN) this.setImage(down);
	}
	
	public void setFacing1(Facing f) {
		this.facing = f;
		if(f == Facing.LEFT) this.setImage(left1);
		if(f == Facing.RIGHT) this.setImage(right1);
		if(f == Facing.UP) this.setImage(up1);
		if(f == Facing.DOWN) this.setImage(down1);
	}
	
	public void setFacing2(Facing f) {
		this.facing = f;
		if(f == Facing.LEFT) this.setImage(left2);
		if(f == Facing.RIGHT) this.setImage(right2);
		if(f == Facing.UP) this.setImage(up2);
		if(f == Facing.DOWN) this.setImage(down2);
	}
	
	/* Getters & setters */
	public void setCactus(Cactus cactus) {
		this.cactus = cactus;
	}
	
	public void setStarsLabel(StarsLabel stars) {
		this.starsLabel = stars;
	}
	
	public void setHealthBar(HealthBar healthBar) {
		this.healthBar = healthBar;
	}
	
	public int getNumStars() {
		return numStars;
	}

	public void addStar() {
		this.numStars++;
		starsLabel.updateNumStars(numStars);
	}
	
	public void removeStar() {
		this.numStars--;
		starsLabel.updateNumStars(numStars);
	}

	public void setKeys(Keys keys) {
		this.keys = keys;
	}
	
	public int getNumKeys() {
		return numKeys;
	}
	
	public void addKey() {
		this.numKeys++;
	}
	
	public void removeKey() {
		this.numKeys--;
	}
	
	public Cactus getCactus() {
		return cactus;
	}

	public Keys getKeys() {
		return keys;
	}

	public HealthBar getHealthBar() {
		return healthBar;
	}
}

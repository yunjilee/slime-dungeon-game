package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

abstract class Entity extends ImageView {
	protected Facing facing;
	protected Image left, right, up, down;
	protected int height, width;
	protected int maxHealth;
	protected int health;
	protected int paneWidth, paneHeight;
	
	/* Template Pattern */
	abstract void resetEntity();
	
	public Entity(Image img, int height, int width, int health, int paneWidth, int paneHeight) {
		super(img);
		this.height = height;
		this.width = width;
		this.health = health;
		this.maxHealth = health;
		this.paneWidth = paneWidth;
		this.paneHeight = paneHeight;
		
		this.setFitWidth(width);
		this.setPreserveRatio(true);
	}
	
	public final void reset() {
		health = maxHealth;
		moveTo(paneWidth / 2, paneHeight / 2);
		
		/* Template Method */
		resetEntity();
	}
	
	public void setFacing(Facing f) {
		this.facing = f;
		if(f == Facing.LEFT) this.setImage(left);
		if(f == Facing.RIGHT) this.setImage(right);
		if(f == Facing.UP) this.setImage(up);
		if(f == Facing.DOWN) this.setImage(down);
	}
	
	public int getCenterY() {
		return (int)(this.getLayoutY() + height / 2);
	}
	
	public int getCenterX() {
		return (int)(this.getLayoutX() + width / 2);
	}
	
	public int getWidth() {
		return (int)this.getBoundsInLocal().getWidth();
	}
	
	public int getHeight() {
		return (int)this.getBoundsInLocal().getHeight();
	}
	
	public void setLeftImage(Image i) {
		this.left = i;
	}
	
	public void setRightImage(Image i) {
		this.right = i;
	}
	
	public void setUpImage(Image i) {
		this.up = i;
	}
	
	public void setDownImage(Image i) {
		this.down = i;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void getDamage() {
		health -= 1;
	}
	
	public void moveBy(int dx, int dy) {
        if(dx == 0 && dy == 0) return;

        double x = this.getLayoutX() + dx;
        double y = this.getLayoutY() + dy;

        moveTo(x, y);
    }
	
	public void moveTo(double x, double y) {
        if (x >= 0 && x <= (paneWidth - this.getWidth()) &&
            y >= 0 && y <= (paneHeight - this.getHeight())) {
        	this.relocate(x, y);
        }
    }
	
	public Facing getFacing() {
		return facing;
	}
}

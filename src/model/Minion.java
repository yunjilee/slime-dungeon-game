package model;

import javafx.scene.image.Image;

public class Minion extends Entity {
	
	public Minion(int paneWidth, int paneHeight, Facing f) {
		super(new Image("images/Slime.png"), 40, 71, 2, paneWidth, paneHeight); // 2 health
		
		setLeftImage(new Image("images/SlimeLeft.png"));
		setRightImage(new Image("images/SlimeRight.png"));
		
		setFacing(f);
	}
	
	@Override
	public void resetEntity() {
		facing = Facing.LEFT;
		this.setImage(left);
	}
	
	public void changeXDirection() {
		if(facing == Facing.LEFT) {
			this.setFacing(Facing.RIGHT);
		}
		else if(facing == Facing.RIGHT) {
			this.setFacing(Facing.LEFT);
		}
	}
	
	public void move() {
		// Minion movement
		int dx = 0, dy = 0;

        if (facing == Facing.RIGHT)  dx += 1;
        if (facing == Facing.LEFT)  dx -= 1;

		this.moveBy(dx, dy);
	}
}

package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cactus extends ImageView {
	int growthLevel; // 1 -> 2 -> 3
	Image level1;
	Image level2;
	Image level3;
	
	public Cactus() {
		super(new Image("images/Cactus1.png"));
		this.setFitWidth(70);
		this.setPreserveRatio(true);
		
		growthLevel = 1;
		
		level1 = new Image("images/Cactus1.png");
		level2 = new Image("images/Cactus2.png");
		level3 = new Image("images/Cactus3.png");
	}
	
	/* Reset cactus to original state */
	public void reset() {
		growthLevel = 1;
		this.setImage(level1);
	}
	
	/* Upgrade growth level */
	public boolean upgradeLevel() {
		boolean harvestJewel = false;
		int prevHeight = (int)this.getBoundsInLocal().getHeight();
		int prevY = (int)this.getLayoutY();
		
		if(growthLevel == 1) {
			this.setImage(level2);
			this.growthLevel = 2;
		}
		else if(growthLevel == 2) {
			this.setImage(level3);
			this.growthLevel = 3;
		}
		else if(growthLevel == 3) {
			resetLevel();
			harvestJewel = true;
		}
		
		int newHeight = (int)this.getBoundsInLocal().getHeight();
		int heightDiff = newHeight - prevHeight;
		this.setLayoutY(prevY - heightDiff);
		
		return harvestJewel;
	}
	
	/* Reset growth level */
	public void resetLevel() {
		this.setImage(level1);
		this.growthLevel = 1;
	}
	
	/* Get height */
	public int getHeight() {
		return (int)this.getBoundsInParent().getWidth();
	}
}

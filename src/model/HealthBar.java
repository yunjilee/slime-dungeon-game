package model;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class HealthBar extends HBox {
	private int maxHealth;
	private int numHearts;
	private ArrayList<ImageView> hearts;
	
	private Image fullHeartImg;
	private Image halfHeartImg;
	private Image emptyHeartImg;
	
	private static final int heartWidth = 32;
	
	public HealthBar(int maxHealth) {
		super();
		
		this.maxHealth = maxHealth;
		this.numHearts = maxHealth / 2;
		this.fullHeartImg = new Image("images/FullHeart.png");
		this.halfHeartImg = new Image("images/HalfHeart.png");
		this.emptyHeartImg = new Image("images/EmptyHeart.png");
		
		hearts = new ArrayList<>();
		
		for(int i = 0; i < numHearts; i++) {
			ImageView h = new ImageView(fullHeartImg);
			h.setFitWidth(heartWidth);
			h.setPreserveRatio(true);
			hearts.add(h);
		}
		
		this.getChildren().addAll(hearts);
		this.setPrefWidth(heartWidth * numHearts);
		this.setSpacing(7);
	}
	
	public void reset() {
		for(int i = 0; i < numHearts; i++) {
			hearts.get(i).setImage(fullHeartImg);
		}
	}
	
	public void update(int playerHealth) {
		if(playerHealth >= maxHealth || playerHealth < 0) {
			throw new RuntimeException("Invalid player health.");
		}
		
		int heartNum = (playerHealth + 1) / 2 - 1;
		if(heartNum < 0) heartNum = 0;
		int halfHeart = playerHealth % 2; // 1: half, 0: empty
		
		if(halfHeart == 1) hearts.get(heartNum).setImage(halfHeartImg);
		else hearts.get(heartNum + 1).setImage(emptyHeartImg);
	}
	
	public int getHeartHeight() {
		return (int)hearts.get(0).getBoundsInLocal().getHeight();
	}
}

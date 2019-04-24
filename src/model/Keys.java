package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Keys extends HBox {
	
	private ImageView rKey;
	private ImageView bKey;
	private ImageView yKey;
	private ImageView gKey;
	
	private Image rKeyPlaceholderImg;
	private Image bKeyPlaceholderImg;
	private Image yKeyPlaceholderImg;
	private Image gKeyPlaceholderImg;
	
	private Image rKeyImg;
	private Image bKeyImg;
	private Image yKeyImg;
	private Image gKeyImg;
	
	private static final int keyWidth = 33;
	
	public Keys() {
		super();
		
		rKeyPlaceholderImg = new Image("images/RKeyPlaceholder.png");
		bKeyPlaceholderImg = new Image("images/BKeyPlaceholder.png");
		yKeyPlaceholderImg = new Image("images/YKeyPlaceholder.png");
		gKeyPlaceholderImg = new Image("images/GKeyPlaceholder.png");
		
		rKeyImg = new Image("images/RKey.png");
		bKeyImg = new Image("images/BKey.png");
		yKeyImg = new Image("images/YKey.png");
		gKeyImg = new Image("images/GKey.png");
		
		rKey = new ImageView(rKeyPlaceholderImg);
		rKey.setFitWidth(keyWidth);
		rKey.setPreserveRatio(true);
		
		bKey = new ImageView(bKeyPlaceholderImg);
		bKey.setFitWidth(keyWidth);
		bKey.setPreserveRatio(true);
		
		yKey = new ImageView(yKeyPlaceholderImg);
		yKey.setFitWidth(keyWidth);
		yKey.setPreserveRatio(true);
		
		gKey = new ImageView(gKeyPlaceholderImg);
		gKey.setFitWidth(keyWidth);
		gKey.setPreserveRatio(true);
		
		this.getChildren().addAll(rKey, bKey, yKey, gKey);
		this.setPrefWidth(keyWidth * 4);
		this.setSpacing(7);
	}
	
	public void reset() {
		rKey.setImage(rKeyPlaceholderImg);
		bKey.setImage(bKeyPlaceholderImg);
		yKey.setImage(yKeyPlaceholderImg);
		gKey.setImage(gKeyPlaceholderImg);
	}
	
	public int getKeyWidth() {
		return keyWidth;
	}
	
	public int getKeyHeight() {
		return (int)rKey.getBoundsInLocal().getHeight();
	}
	
	public void fillKey(int k) {
		if(k == 1) rKey.setImage(rKeyImg);
		else if(k == 2) bKey.setImage(bKeyImg);
		else if(k == 3) yKey.setImage(yKeyImg);
		else if(k == 4) gKey.setImage(gKeyImg);
	}
}
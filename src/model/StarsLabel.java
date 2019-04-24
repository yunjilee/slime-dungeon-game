package model;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class StarsLabel extends Label {
	private ImageView starImg;
	private static int starWidth = 28;
	
	public StarsLabel() {
		super("x0");
		
		starImg = new ImageView(new Image("images/Star.png"));
		starImg.setFitWidth(starWidth);
		starImg.setPreserveRatio(true);
		
	    this.setGraphic(starImg);
	    this.setFont(Font.font("Proxima Nova Extrabold", 28));
	}
	
	/* Reset to original state */
	public void reset() {
		this.setText("x0");
	}
	
	/* Update number of stars gained */
	public void updateNumStars(int numStars) {
		this.setText("x" + numStars);
	}
}

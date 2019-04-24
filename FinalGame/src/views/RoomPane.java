package views;

import controller.MainController;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import model.Cactus;

public class RoomPane extends GamePaneBase {
	
	protected Button menuButton;
	
	private ImageView bed;
	private ImageView chainedExit; // exit from dungeon
	private ImageView backyardExit; // exit to backyard
	private ImageView torchL;
	private ImageView torchR;
	
	private Cactus cactus; // from game controller
	
	private static int doorWidth = 100;
	private static int torchWidth = 25;
	
	public RoomPane(int mainPaneWidth, int mainPaneHeight, MainController controller) {
		super(mainPaneWidth, mainPaneHeight, controller);
		setupRoomPieces();
	}

	// set up pieces inside room
	private void setupRoomPieces() {
		
		menuButton = new Button("MENU");
		mc.styleButton(menuButton);
		menuButton.setPrefWidth(100);
		menuButton.setFont(Font.font(mc.getTitleFont(), 18));
		menuButton.setLayoutX(15);
		menuButton.setLayoutY(15);
		menuButton.setOnMouseClicked(e -> {
			mc.showMenuScene();
		});
		
		bed = new ImageView(new Image("images/Bed.png"));
		bed.setFitWidth(220);
		bed.setPreserveRatio(true);
		bed.setLayoutX(width / 2 - bed.getFitWidth() / 2);
		bed.setLayoutY(height - 150);
		
		chainedExit = new ImageView(new Image("images/ChainedDoor.png"));
		chainedExit.setFitWidth(doorWidth);
		chainedExit.setPreserveRatio(true);
		chainedExit.setLayoutX(width / 2 - doorWidth - 15);
		chainedExit.setLayoutY(20);
		
		backyardExit = new ImageView(new Image("images/BackyardDoor.png"));
		backyardExit.setFitWidth(doorWidth);
		backyardExit.setPreserveRatio(true);
		backyardExit.setLayoutX(width / 2 + 15);
		backyardExit.setLayoutY(20);
		
		torchL = new ImageView(new Image("images/TorchLeft.png"));
		torchL.setFitWidth(torchWidth);
		torchL.setPreserveRatio(true);
		torchL.setLayoutX(width / 2 - doorWidth - 15 - torchWidth - 15);
		torchL.setLayoutY(70);
		
		torchR = new ImageView(new Image("images/TorchRight.png"));
		torchR.setFitWidth(torchWidth);
		torchR.setPreserveRatio(true);
		torchR.setLayoutX(width / 2 + doorWidth + 15 + 15);
		torchR.setLayoutY(70);
		
		cactus = gc.getCactus();
		cactus.setLayoutX(width - 100);
		cactus.setLayoutY(height / 2 - cactus.getHeight() / 2);
		
		this.getChildren().addAll(menuButton, bed, chainedExit, backyardExit, torchL, torchR, cactus);
	}
	
	/* Interactable object collisions */
	public void interactCollisions() {
		// to simulate walking in the door
		int backyardExitCenterY = (int)(backyardExit.getY() + backyardExit.getBoundsInLocal().getHeight() / 2);
		
		// backyard exit
		if(gc.playerCollidesWith(backyardExit) && player.getLayoutY() <= backyardExitCenterY) {
			if(gc.interact) {
				mc.showBackyardScene();
				player.moveTo(width / 2 - doorWidth / 2 + 10, height - 130); // set player position to bottom center
			}
		}
		// cactus
		else if(gc.playerCollidesWith(cactus)) {
			if(gc.interact) gc.feedCactus();
		}
	}
	
	/* Non-interactable object collisions */
	public boolean playerCollision() {
		boolean collision = false;
		
		// chained exit
		if(gc.playerCollidesWith(chainedExit)) {
			collision = true;
		}
		// torches
		else if(gc.playerCollidesWith(torchL) || gc.playerCollidesWith(torchR)) {
			collision = true;
		}
		// bed
		else if(gc.playerCollidesWith(bed)) {
			collision = true;
		}
		
		return collision;
	}
}

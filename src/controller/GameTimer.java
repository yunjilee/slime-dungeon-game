package controller;

import javafx.animation.AnimationTimer;
import views.BackyardPane;
import views.RoomPane;

public class GameTimer extends AnimationTimer {
	private GameController gc;
	private RoomPane room;
	private BackyardPane backyard;
	
	private long lastCollision;
	private double elapsedSeconds;
    private double damageSpeed = 1; // # seconds between damages
    private boolean doDamage;
    
    // for player frame animation
    private long lastFrameUpdate;
	
	public GameTimer(GameController gc, RoomPane room, BackyardPane backyard) {
		this.gc = gc;
		
		this.room = room;
		this.backyard = backyard;
	}

    @Override
    public void start() {
        lastCollision = System.nanoTime();
        lastFrameUpdate = System.nanoTime();
        doDamage = true;
        gc.resetGC();
        super.start();
    }
		
	@Override
	public void handle(long now) {
		elapsedSeconds = (now - lastCollision) / 1_000_000_000.0;
		if(elapsedSeconds >= damageSpeed) doDamage = true;
		else doDamage = false;

		// Player movement
		int dx = 0, dy = 0;

        if (gc.goNorth) dy -= 1;
        if (gc.goSouth) dy += 1;
        if (gc.goEast)  dx += 1;
        if (gc.goWest)  dx -= 1;
        if (gc.running) { dx *= 3; dy *= 3; }
        
        gc.getPlayer().moveBy(dx, dy);
        
        if(gc.getViewNum() == 1) { // 1: room
        	room.interactCollisions();
        	if(room.playerCollision()) gc.getPlayer().moveBy(-dx, -dy);
        }
        else if(gc.getViewNum() == 2) { // 2: backyard
        	boolean collision = backyard.moveMinions(doDamage);
        	if(collision && doDamage) lastCollision = now;
        	
        	backyard.interactCollisions();
        	if(backyard.playerCollision()) gc.getPlayer().moveBy(-dx, -dy);
        }
        
        double elapsedFrameUpdateSeconds = (now - lastFrameUpdate) / 1_000_000_000.0;
        if(gc.getKeyPressed() && (elapsedFrameUpdateSeconds >= 0.2)) {
        	gc.getPlayer().updateWalking(); // Update walking frame animatio
        	lastFrameUpdate = now;
        } 
        else {
        	gc.getPlayer().setFacing(gc.getPlayer().getFacing());
        	lastFrameUpdate = 0;
        }
        
        // if player's health drops below 0, lose game
        if(gc.getPlayer().getHealth() <= 0) gc.endGame(false);
        
        // unactivate interaction
        gc.interact = false;
	}
}
